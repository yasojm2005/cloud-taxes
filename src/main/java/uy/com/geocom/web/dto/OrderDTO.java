package uy.com.geocom.web.dto;

import jakarta.validation.constraints.*;

import java.util.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    @NotBlank
    private String orderId;
    @NotBlank
    private String documentType;
    @NotBlank
    private String customerType;
    @NotBlank
    private String countryCode;
    @NotNull
    @Size(min = 1)
    private List<OrderItemDTO> items;
}
