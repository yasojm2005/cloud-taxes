package uy.com.geocom.domain.component;


import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.*;
import uy.com.geocom.domain.shared.Money;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FixedTax extends TaxComponent {
    private BigDecimal amountPerUnit; // monto fijo por unidad

    @Override
    public Money calculate(Money baseUnitPrice, int quantity, int scale, RoundingMode mode) {
        BigDecimal total = amountPerUnit.multiply(BigDecimal.valueOf(quantity));
        return new Money(total.setScale(scale, mode), baseUnitPrice.getCurrency());
    }
}
