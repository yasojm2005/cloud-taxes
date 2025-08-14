/*
package uy.com.geocom;

import com.example.taxservice.domain.shared.*;
import com.example.taxservice.service.rounding.RoundingService;
import org.junit.jupiter.api.*;
import java.math.*;

public class RoundingServiceTest {
  @Test void halfUpTo2Decimals() {
    RoundingService s = new RoundingService(new RoundingPolicy(2, RoundingMode.HALF_UP));
    Money m = new Money(new BigDecimal("1.005"), "UYU");
    Assertions.assertEquals(new BigDecimal("1.01"), s.round(m).getAmount());
  }
}
*/
