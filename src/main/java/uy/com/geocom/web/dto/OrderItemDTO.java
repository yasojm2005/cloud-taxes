package uy.com.geocom.web.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    @NotBlank
    private String itemId;
    private String taxRuleKey;
    private List<InlineTaxDTO> taxes;
    private String composition;
    @NotNull
    @Positive
    private Integer quantity;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal unitPrice;
    private String currency = "UYU";
}
