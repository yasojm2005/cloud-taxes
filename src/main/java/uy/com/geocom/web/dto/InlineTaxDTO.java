package uy.com.geocom.web.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineTaxDTO {
    @NotNull
    private InlineTaxType type;
    @NotBlank
    private String name;
    private BigDecimal rate;
    private BigDecimal amountPerUnit;
    private BigDecimal threshold;
    private List<RegionalRateDTO> regionalRates;
}
