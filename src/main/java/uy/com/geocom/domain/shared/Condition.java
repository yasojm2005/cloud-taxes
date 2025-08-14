package uy.com.geocom.domain.shared;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Condition {
  private String field;
  private ConditionOperator operator;
  private Object value;
}
