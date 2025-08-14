package uy.com.geocom.service.composition;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.shared.Money;

public class AdditiveComposition implements CompositionStrategy {
  @Override
  public Result compose(Money unitPrice, int qty, List<TaxComponent> components, int scale, RoundingMode mode) {
    List<Line> lines = new ArrayList<>();
    Money total = new Money(BigDecimal.ZERO.setScale(scale, mode), unitPrice.getCurrency());
    for (TaxComponent c : components) {
      if (!c.isActive()) continue;
      Money amt = c.calculate(unitPrice, qty, scale, mode);
      lines.add(new Line(c.getKey(), c.getDescription(), amt));
      total = total.plus(amt);
    }
    return new Result(total, lines);
  }
}
