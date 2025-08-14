package uy.com.geocom.web.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionalRateDTO {
    @NotBlank
    private String regionCode;
    @NotNull
    private BigDecimal rate;
}
