package uy.com.geocom.service;



import lombok.RequiredArgsConstructor;
import uy.com.geocom.domain.component.RegionalTax;
import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.rule.TaxAdjustmentRule;
import uy.com.geocom.domain.rule.TaxComposition;
import uy.com.geocom.domain.rule.TaxRule;
import uy.com.geocom.domain.shared.Money;
import uy.com.geocom.domain.shared.RoundingPolicy;
import uy.com.geocom.repository.TaxAdjustmentRuleRepository;
import uy.com.geocom.repository.TaxComponentRepository;
import uy.com.geocom.repository.TaxRuleRepository;
import uy.com.geocom.service.adjustment.AdjustmentApplier;
import uy.com.geocom.service.composition.AdditiveComposition;
import uy.com.geocom.service.composition.CompositionStrategy;
import uy.com.geocom.service.composition.SequentialComposition;
import uy.com.geocom.service.country.CountryTaxStrategy;
import uy.com.geocom.service.country.CountryTaxStrategyFactory;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaxEngine {
    private final TaxRuleRepository ruleRepo;
    private final TaxComponentRepository componentRepo;
    private final TaxAdjustmentRuleRepository adjRepo;
    private final RoundingPolicy roundingPolicy;

    public record ItemResult(String itemId, List<CompositionStrategy.Line> lines, BigDecimal totalTax) {
    }

    public CompositionStrategy selectStrategy(TaxComposition composition) {
        return switch (composition) {
            case ADDITIVE -> new AdditiveComposition();
            case SEQUENTIAL -> new SequentialComposition();
        };
    }

    public ItemResult calculateForItem(String taxRuleKey, List<TaxComponent> inlineComponents, Money unitPrice, int quantity, Map<String, Object> context, TaxComposition compositionOverride) {


        if (inlineComponents != null && !inlineComponents.isEmpty()) {
            try {
                RegionalTax.ThreadLocalContext.set(context);
                String region = (String) context.get("regionCode");
                CountryTaxStrategy countryStrategy = CountryTaxStrategyFactory.forRegion(region);
                var result = countryStrategy.composeForInline(unitPrice, quantity, inlineComponents, roundingPolicy, context, compositionOverride);
                Map<String, BigDecimal> byComp = new LinkedHashMap<>();
                for (var line : result.lines())
                    byComp.put(line.componentKey(), line.amount().getAmount());
                List<TaxAdjustmentRule> adjs = adjRepo.findByIsActiveTrue();
                Map<String, BigDecimal> adjusted = new AdjustmentApplier().apply(adjs, byComp, context);
                BigDecimal total = adjusted.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(roundingPolicy.getScale(), roundingPolicy.getMode());
                List<CompositionStrategy.Line> adjLines = new ArrayList<>();
                for (var line : result.lines()) {
                    BigDecimal amt = adjusted.getOrDefault(line.componentKey(), line.amount().getAmount());
                    adjLines.add(new CompositionStrategy.Line(line.componentKey(), line.description(), new Money(amt, unitPrice.getCurrency())));
                }
                return new ItemResult(context.getOrDefault("itemId", java.util.UUID.randomUUID().toString()).toString(), adjLines, total);
            } finally {
                RegionalTax.ThreadLocalContext.clear();
            }
        }


        // Predefined rule

        TaxRule rule = ruleRepo.findByKeyAndActiveTrue(taxRuleKey)
                .orElseThrow(() -> new IllegalArgumentException("TaxRule not found or inactive: " + taxRuleKey));

        List<TaxComponent> comps = componentRepo.findAllById(rule.getComponentIds());

        CompositionStrategy strategy = selectStrategy(rule.getComposition());
        var result = strategy.compose(unitPrice, quantity, comps, roundingPolicy.getScale(), roundingPolicy.getMode());

        Map<String, BigDecimal> byComp = new LinkedHashMap<>();
        for (var line : result.lines()) byComp.put(line.componentKey(), line.amount().getAmount());

        List<TaxAdjustmentRule> adjs = adjRepo.findByIsActiveTrue();
        Map<String, BigDecimal> adjusted = new AdjustmentApplier().apply(adjs, byComp, context);

       BigDecimal total = adjusted.values().stream().reduce(BigDecimal.ZERO, java.math.BigDecimal::add)
                .setScale(roundingPolicy.getScale(), roundingPolicy.getMode());

        List<CompositionStrategy.Line> adjLines = new java.util.ArrayList<>();
        for (var line : result.lines()) {
           BigDecimal amt = adjusted.getOrDefault(line.componentKey(), line.amount().getAmount());
            adjLines.add(new CompositionStrategy.Line(line.componentKey(), line.description(), new Money(amt, unitPrice.getCurrency())));
        }

        return new ItemResult(context.getOrDefault("itemId", java.util.UUID.randomUUID().toString()).toString(), adjLines, total);
    }
}
