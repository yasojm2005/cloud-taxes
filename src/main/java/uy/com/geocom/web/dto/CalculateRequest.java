package uy.com.geocom.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculateRequest {
    @NotNull
    private OrderDTO order;
}
