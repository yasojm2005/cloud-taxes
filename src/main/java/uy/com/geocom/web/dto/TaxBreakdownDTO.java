package uy.com.geocom.web.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxBreakdownDTO {
    private String componentKey;
    private String description;
    private BigDecimal amount;
}
