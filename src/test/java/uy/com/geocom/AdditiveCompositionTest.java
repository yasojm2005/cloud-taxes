/*
package uy.com.geocom;

import com.example.taxservice.domain.component.*;
import com.example.taxservice.domain.shared.Money;
import com.example.taxservice.service.composition.*;
import org.junit.jupiter.api.*;
import java.math.*;
import java.util.*;

class AdditiveCompositionTest {
  @Test void additiveSumsComponents() {
    Money unit = new Money(new BigDecimal("100.00"), "UYU");
    List<TaxComponent> comps = List.of(
      new PercentTax("IVA","IVA 22%",true,new BigDecimal("0.22")),
      new FixedTax("ICO","Impuesto fijo",true,new BigDecimal("5.00"))
    );
    CompositionStrategy s = new AdditiveComposition();
    var res = s.compose(unit, 1, comps, 2, RoundingMode.HALF_UP);
    Assertions.assertEquals(new BigDecimal("27.00"), res.total().getAmount());
  }
}
*/
