package uy.com.geocom.domain.shared;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class Action {
  private ActionType type;
  private String targetComponentKey; // para OVERRIDE_RATE opcional
  private Double value;              // porcentaje o monto
}
