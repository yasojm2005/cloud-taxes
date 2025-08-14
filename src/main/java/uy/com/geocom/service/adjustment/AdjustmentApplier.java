package uy.com.geocom.service.adjustment;


import java.util.*;
import java.math.BigDecimal;

import uy.com.geocom.domain.rule.TaxAdjustmentRule;
import uy.com.geocom.domain.shared.Action;
import uy.com.geocom.domain.shared.Condition;

public class AdjustmentApplier {
  public Map<String, BigDecimal> apply(List<TaxAdjustmentRule> rules, Map<String, BigDecimal> taxByComponent, Map<String,Object> context) {
    Map<String, BigDecimal> result = new HashMap<>(taxByComponent);
    for (TaxAdjustmentRule rule : rules) {
      if (!rule.isActive()) continue;
      if (matches(rule.getCondition(), context)) {
        Action action = rule.getAction();
        switch (action.getType()) {
          case SET_ZERO_TAX -> result.replaceAll((k,v) -> BigDecimal.ZERO);
          case PERCENTAGE_ADJUSTMENT -> {
            BigDecimal factor = BigDecimal.valueOf(action.getValue() / 100.0);
            result.replaceAll((k,v) -> v.add(v.multiply(factor)));
          }
          case FIXED_ADJUSTMENT -> {
            BigDecimal delta = BigDecimal.valueOf(action.getValue());
            result.replaceAll((k,v) -> v.add(delta));
          }
          case OVERRIDE_RATE -> {
            String target = action.getTargetComponentKey();
            if (target != null && result.containsKey(target) && action.getValue()!=null) {
              BigDecimal current = result.get(target);
              BigDecimal overridden = current.multiply(BigDecimal.valueOf(action.getValue()));
              result.put(target, overridden);
            }
          }
        }
      }
    }
    return result;
  }

  private boolean matches(Condition c, Map<String,Object> ctx) {
    Object val = ctx.get(c.getField());
    if (val == null) return false;
    return switch (c.getOperator()) {
      case EQUALS -> val.equals(c.getValue());
      case IN -> (c.getValue() instanceof Collection<?> coll) && coll.contains(val);
      case GT -> compare(val, c.getValue()) > 0;
      case GTE -> compare(val, c.getValue()) >= 0;
      case LT -> compare(val, c.getValue()) < 0;
      case LTE -> compare(val, c.getValue()) <= 0;
      case CONTAINS -> (val instanceof String s) && s.contains(String.valueOf(c.getValue()));
    };
  }

  private int compare(Object a, Object b) {
    java.math.BigDecimal x = new java.math.BigDecimal(String.valueOf(a));
    java.math.BigDecimal y = new java.math.BigDecimal(String.valueOf(b));
    return x.compareTo(y);
  }
}
