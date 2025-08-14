package uy.com.geocom.service.country;

import java.math.BigDecimal;
import java.util.*;

import uy.com.geocom.domain.component.FixedTax;
import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.rule.TaxComposition;
import uy.com.geocom.domain.shared.Money;
import uy.com.geocom.domain.shared.RoundingPolicy;
import uy.com.geocom.service.composition.CompositionStrategy;

public class ColombiaTaxStrategy implements CountryTaxStrategy {
  @Override
  public CompositionStrategy.Result composeForInline(
      Money unitPrice,
      int qty,
      List<TaxComponent> components,
      RoundingPolicy rounding,
      java.util.Map<String, Object> ctx,
      TaxComposition ignored) {

    int scale = rounding.getScale();
    var mode = rounding.getMode();
    String currency = unitPrice.getCurrency();
    List<TaxComponent> fixed = new java.util.ArrayList<>(), others = new java.util.ArrayList<>();

    for (TaxComponent c : components) {
      if (!c.isActive()) continue;
      if (c instanceof FixedTax) fixed.add(c);
      else others.add(c);
    }

    BigDecimal fixedPerUnit = BigDecimal.ZERO;
    for (TaxComponent c : fixed) fixedPerUnit = fixedPerUnit.add(((FixedTax) c).getAmountPerUnit());

    BigDecimal adjustedUnit = unitPrice.getAmount().subtract(fixedPerUnit);

    if (adjustedUnit.signum() < 0) adjustedUnit = java.math.BigDecimal.ZERO;

    Money adjusted = new Money(adjustedUnit, currency);
    List<CompositionStrategy.Line> lines = new java.util.ArrayList<>();
    BigDecimal total = java.math.BigDecimal.ZERO.setScale(scale, mode);

    for (TaxComponent c : others) {
      Money amt = c.calculate(adjusted, qty, scale, mode);
      lines.add(new CompositionStrategy.Line(c.getKey(), c.getDescription(), amt));
      total = total.add(amt.getAmount());
    }

    for (TaxComponent c : fixed) {
      Money amt = c.calculate(unitPrice, qty, scale, mode);
      lines.add(new CompositionStrategy.Line(c.getKey(), c.getDescription(), amt));
      total = total.add(amt.getAmount());
    }

    return new CompositionStrategy.Result(new Money(total, currency), lines);
  }
}
