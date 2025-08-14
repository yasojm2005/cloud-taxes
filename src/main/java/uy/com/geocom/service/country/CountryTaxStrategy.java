package uy.com.geocom.service.country;



import java.util.List;
import java.util.Map;

import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.rule.TaxComposition;
import uy.com.geocom.domain.shared.Money;
import uy.com.geocom.domain.shared.RoundingPolicy;
import uy.com.geocom.service.composition.CompositionStrategy;

public interface CountryTaxStrategy {
    CompositionStrategy.Result composeForInline(
            Money unitPrice, int qty, List<TaxComponent> components,
            RoundingPolicy rounding, Map<String, Object> ctx, TaxComposition compositionOverride
    );
}
