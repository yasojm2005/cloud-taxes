package uy.com.geocom.domain.component;


import lombok.*;
import uy.com.geocom.domain.shared.Money;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TaxComponent {
    private String key;                // IVA, ICO, etc.
    private String description;
    private boolean active = true;

    public abstract Money calculate(Money baseUnitPrice, int quantity, int scale, java.math.RoundingMode mode);
}
