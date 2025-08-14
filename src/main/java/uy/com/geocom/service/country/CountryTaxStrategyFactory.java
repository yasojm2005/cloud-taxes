package uy.com.geocom.service.country;

import java.util.Locale;

public class CountryTaxStrategyFactory {
    public static CountryTaxStrategy forRegion(String regionCode) {
        if (regionCode == null) return new DefaultTaxStrategy();
        String code = regionCode.trim().toUpperCase(Locale.ROOT);
        return switch (code) {
            case "UY", "URU", "URUGUAY" -> new ColombiaTaxStrategy();
            default -> new DefaultTaxStrategy();
        };
    }
}
