package uy.com.geocom.domain.rule;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document("tax_rules")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TaxRule {
  @Id private String id;
  private String key;
  private String description;
  private boolean active = true;
  private TaxComposition composition = TaxComposition.ADDITIVE;
  private List<String> componentIds = new ArrayList<>();
}
