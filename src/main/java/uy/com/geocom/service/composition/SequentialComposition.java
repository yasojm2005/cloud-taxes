package uy.com.geocom.service.composition;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.shared.Money;

public class SequentialComposition implements CompositionStrategy {
  @Override
  public Result compose(Money unitPrice, int qty, List<TaxComponent> components, int scale, RoundingMode mode) {
    List<Line> lines = new ArrayList<>();
    Money total = new Money(BigDecimal.ZERO.setScale(scale, mode), unitPrice.getCurrency());
    Money currentBaseUnit = unitPrice;
    for (TaxComponent c : components) {
      if (!c.isActive()) continue;
      Money amt = c.calculate(currentBaseUnit, qty, scale, mode);
      lines.add(new Line(c.getKey(), c.getDescription(), amt));
      total = total.plus(amt);
      BigDecimal newBaseUnit = currentBaseUnit.getAmount().add(amt.getAmount().divide(BigDecimal.valueOf(qty), scale, mode));
      currentBaseUnit = new Money(newBaseUnit, unitPrice.getCurrency());
    }
    return new Result(total, lines);
  }
}
