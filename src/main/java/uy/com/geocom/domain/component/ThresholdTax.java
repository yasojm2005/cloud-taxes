package uy.com.geocom.domain.component;


import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.*;
import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.shared.Money;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor
public class ThresholdTax extends TaxComponent {
  private BigDecimal threshold; // aplica si baseUnitPrice >= threshold
  private BigDecimal rate;      // porcentaje

  @Override
  public Money calculate(Money baseUnitPrice, int quantity, int scale, RoundingMode mode) {
    if (baseUnitPrice.getAmount().compareTo(threshold) < 0) return new Money(BigDecimal.ZERO.setScale(scale, mode), baseUnitPrice.getCurrency());
    Money base = new Money(baseUnitPrice.getAmount().multiply(BigDecimal.valueOf(quantity)), baseUnitPrice.getCurrency());
    return base.times(rate, scale, mode);
  }
}
