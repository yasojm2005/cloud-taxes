package uy.com.geocom.web;



import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import uy.com.geocom.domain.component.FixedTax;
import uy.com.geocom.domain.component.PercentTax;
import uy.com.geocom.domain.component.TaxComponent;
import uy.com.geocom.domain.component.ThresholdTax;
import uy.com.geocom.web.dto.InlineTaxDTO;
import uy.com.geocom.web.dto.RegionalRateDTO;

public class InlineTaxMapper {
    public static java.util.List<TaxComponent> toComponents(java.util.List<InlineTaxDTO> taxes) {
        if (taxes == null || taxes.isEmpty()) return java.util.List.of();
        java.util.List<TaxComponent> out = new java.util.ArrayList<>();
        for (InlineTaxDTO t : taxes) {
            switch (t.getType()) {
                case PERCENT -> {
                    var c = new PercentTax();
                    c.setKey(t.getName());
                    c.setDescription(t.getName());
                    c.setActive(true);
                    c.setRate(ns(t.getRate()));
                    out.add(c);
                }
                case FIXED -> {
                    var c = new FixedTax();
                    c.setKey(t.getName());
                    c.setDescription(t.getName());
                    c.setActive(true);
                    c.setAmountPerUnit(ns(t.getAmountPerUnit()));
                    out.add(c);
                }
                case THRESHOLD -> {
                    var c = new ThresholdTax();
                    c.setKey(t.getName());
                    c.setDescription(t.getName());
                    c.setActive(true);
                    c.setThreshold(ns(t.getThreshold()));
                    c.setRate(ns(t.getRate()));
                    out.add(c);
                }

            }
        }
        return out;
    }

    private static BigDecimal ns(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
