package uy.com.geocom.domain.rule;


import lombok.*;
import uy.com.geocom.domain.shared.Action;
import uy.com.geocom.domain.shared.Condition;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tax_adjustment_rules")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TaxAdjustmentRule {
  @Id private String id;
  private String key;
  private String description;
  private boolean isActive = true;
  private Condition condition;
  private Action action;
}
