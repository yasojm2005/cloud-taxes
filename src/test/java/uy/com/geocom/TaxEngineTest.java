/*
package uy.com.geocom;

import com.example.taxservice.domain.component.*;
import com.example.taxservice.domain.rule.*;
import com.example.taxservice.domain.shared.*;
import com.example.taxservice.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.math.*;
import java.util.*;

class TaxEngineTest {
  @Test void appliesAdjustmentRuleDocumentExempt() {
    TaxRuleRepository ruleRepo = Mockito.mock(TaxRuleRepository.class);
    TaxComponentRepository compRepo = Mockito.mock(TaxComponentRepository.class);
    TaxAdjustmentRuleRepository adjRepo = Mockito.mock(TaxAdjustmentRuleRepository.class);

    TaxRule rule = TaxRule.builder().key("RULE1").active(true).composition(TaxComposition.ADDITIVE)
      .componentIds(List.of("C1"))
      .build();

    Mockito.when(ruleRepo.findByKeyAndActiveTrue("RULE1")).thenReturn(Optional.of(rule));
    Mockito.when(compRepo.findAllById(List.of("C1"))).thenReturn(List.of(
      new PercentTax("IVA","IVA 22%",true,new BigDecimal("0.22"))
    ));

    TaxAdjustmentRule r = TaxAdjustmentRule.builder()
      .id("doc_exempt_rule")
      .key("DOCUMENT_EXEMPT")
      .isActive(true)
      .condition(new Condition("documentType", ConditionOperator.EQUALS, "EXEMPT_INVOICE"))
      .action(new Action(ActionType.SET_ZERO_TAX,null,null))
      .build();

    Mockito.when(adjRepo.findByIsActiveTrue()).thenReturn(List.of(r));

    TaxEngine engine = new TaxEngine(ruleRepo, compRepo, adjRepo, new RoundingPolicy(2, RoundingMode.HALF_UP));

    Map<String,Object> ctx = new HashMap<>();
    ctx.put("documentType","EXEMPT_INVOICE");
    ctx.put("itemId","ITEM1");

    var res = engine.calculateForItem("RULE1", new Money(new BigDecimal("100.00"),"UYU"), 1, ctx);
    Assertions.assertEquals(new BigDecimal("0.00"), res.totalTax());
  }
}
*/
