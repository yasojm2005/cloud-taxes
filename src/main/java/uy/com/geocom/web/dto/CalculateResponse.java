package uy.com.geocom.web.dto;

import java.math.BigDecimal;
import java.util.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculateResponse {
    private String orderId;
    private List<ItemTax> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ItemTax {
        private String itemId;
        private BigDecimal totalTax;
        private List<TaxBreakdownDTO> breakdown;
    }
}
