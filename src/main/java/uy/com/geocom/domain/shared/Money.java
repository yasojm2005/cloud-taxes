package uy.com.geocom.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.*;

@Getter @ToString @EqualsAndHashCode
public class Money {
  private final BigDecimal amount;
  private final String currency; // "UYU" por defecto

  public Money(BigDecimal amount, String currency) {
    this.amount = amount;
    this.currency = currency == null ? "UYU" : currency;
  }

  public Money round(int scale, RoundingMode mode) {
    return new Money(amount.setScale(scale, mode), currency);
  }

  public Money plus(Money other) {
    assertSameCurrency(other);
    return new Money(amount.add(other.amount), currency);
  }

  public Money minus(Money other) {
    assertSameCurrency(other);
    return new Money(amount.subtract(other.amount), currency);
  }

  public Money times(BigDecimal factor, int scale, RoundingMode mode) {
    return new Money(amount.multiply(factor).setScale(scale, mode), currency);
  }

  private void assertSameCurrency(Money other) {
    if (!this.currency.equals(other.currency))
      throw new IllegalArgumentException("Currency mismatch");
  }
}
