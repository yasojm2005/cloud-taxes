package uy.com.geocom.service.rounding;


import uy.com.geocom.domain.shared.Money;
import uy.com.geocom.domain.shared.RoundingPolicy;

public class RoundingService {
  private final RoundingPolicy policy;
  public RoundingService(RoundingPolicy policy) { this.policy = policy; }
  public Money round(Money m) { return m.round(policy.getScale(), policy.getMode()); }
}
