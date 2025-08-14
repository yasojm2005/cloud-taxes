package uy.com.geocom.domain.component;



import lombok.*;
import uy.com.geocom.domain.shared.Money;

import org.springframework.data.annotation.TypeAlias;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@TypeAlias("RegionalTax")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionalTax extends TaxComponent {
    private Map<String, BigDecimal> regionalRates;

    @Override
    public Money calculate(Money baseUnitPrice, int quantity, int scale, RoundingMode mode) {
        String code = (String) ThreadLocalContext.get("regionCode");
        BigDecimal rate = regionalRates == null ? BigDecimal.ZERO : regionalRates.getOrDefault(code, BigDecimal.ZERO);
        Money base = new Money(baseUnitPrice.getAmount().multiply(BigDecimal.valueOf(quantity)), baseUnitPrice.getCurrency());
        return base.times(rate, scale, mode);
    }

    public static class ThreadLocalContext {
        private static final ThreadLocal<java.util.Map<String, Object>> CTX = new ThreadLocal<>();

        public static void set(java.util.Map<String, Object> m) {
            CTX.set(m);
        }

        public static Object get(String k) {
            return CTX.get() == null ? null : CTX.get().get(k);
        }

        public static void clear() {
            CTX.remove();
        }
    }
}
