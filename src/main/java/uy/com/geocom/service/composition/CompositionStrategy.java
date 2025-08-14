package uy.com.geocom.service.composition;


import java.util.List;

import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.shared.Money;

public interface CompositionStrategy {
  Result compose(Money unitPrice, int qty, List<TaxComponent> components, int scale, java.math.RoundingMode mode);
  record Line(String componentKey, String description, Money amount) {}
  record Result(Money total, java.util.List<Line> lines) {}
}
