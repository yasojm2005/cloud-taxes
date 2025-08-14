package uy.com.geocom.domain.component;


import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.*;
import uy.com.geocom.domain.shared.Money;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PercentTax extends TaxComponent {
    private BigDecimal rate; // 0.22 para 22%

    @Override
    public Money calculate(Money baseUnitPrice, int quantity, int scale, RoundingMode mode) {
        Money base = new Money(baseUnitPrice.getAmount().multiply(BigDecimal.valueOf(quantity)), baseUnitPrice.getCurrency());
        return base.times(rate, scale, mode);
    }
}
