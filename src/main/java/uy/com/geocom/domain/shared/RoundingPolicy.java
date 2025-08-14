package uy.com.geocom.domain.shared;

import java.math.RoundingMode;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class RoundingPolicy {
  private int scale = 2;
  private RoundingMode mode = RoundingMode.HALF_UP;
}
