/*
package uy.com.geocom;

import com.example.taxservice.domain.component.*;
import com.example.taxservice.domain.shared.Money;
import com.example.taxservice.service.composition.*;
import org.junit.jupiter.api.*;
import java.math.*;
import java.util.*;

class SequentialCompositionTest {
  @Test void sequentialTaxOnTax() {
    Money unit = new Money(new BigDecimal("100.00"), "UYU");
    List<TaxComponent> comps = List.of(
      new PercentTax("IVA","IVA 22%",true,new BigDecimal("0.22")),
      new PercentTax("ADIC","Adicional 10%",true,new BigDecimal("0.10"))
    );
    CompositionStrategy s = new SequentialComposition();
    var res = s.compose(unit, 1, comps, 2, RoundingMode.HALF_UP);
    Assertions.assertEquals(new BigDecimal("34.20"), res.total().getAmount());
  }
}
*/
