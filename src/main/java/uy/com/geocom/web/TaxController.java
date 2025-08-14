package uy.com.geocom.web;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.rule.TaxComposition;
import uy.com.geocom.domain.shared.Money;
import uy.com.geocom.service.TaxEngine;
import uy.com.geocom.service.composition.CompositionStrategy;
import uy.com.geocom.web.dto.CalculateRequest;
import uy.com.geocom.web.dto.CalculateResponse;
import uy.com.geocom.web.dto.TaxBreakdownDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
public class TaxController {
    private final TaxEngine engine;

    @PostMapping("/calculate")
    public ResponseEntity<CalculateResponse> calculate(@Valid @RequestBody CalculateRequest req) {
        var order = req.getOrder();
        List<CalculateResponse.ItemTax> items = new ArrayList<>();
        for (var it : order.getItems()) {
            Map<String, Object> ctx = new HashMap<>();
            ctx.put("documentType", order.getDocumentType());
            ctx.put("customerType", order.getCustomerType());
            ctx.put("regionCode", order.getCountryCode());
            ctx.put("itemId", it.getItemId());

            List<TaxComponent> inline = null;
            if (it.getTaxes() != null && !it.getTaxes().isEmpty()) {
                inline = InlineTaxMapper.toComponents(it.getTaxes());
            }

            TaxComposition compOverride = null;
            if (it.getComposition() != null && !it.getComposition().isBlank()) {
                compOverride = TaxComposition.valueOf(it.getComposition());
            }

            var res = engine.calculateForItem(it.getTaxRuleKey(), inline, new Money(it.getUnitPrice(), it.getCurrency()), it.getQuantity(), ctx, compOverride);

            List<TaxBreakdownDTO> lines = new ArrayList<>();

            for (CompositionStrategy.Line l : res.lines()) {
                lines.add(new TaxBreakdownDTO(l.componentKey(), l.description(), l.amount().getAmount()));
            }

            items.add(CalculateResponse.ItemTax.builder().itemId(res.itemId()).totalTax(res.totalTax()).breakdown(lines).build());
        }

        return ResponseEntity.ok(CalculateResponse.builder().orderId(order.getOrderId()).items(items).build());
    }
}
