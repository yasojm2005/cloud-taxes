package uy.com.geocom.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TaxCalculationSteps extends CucumberSpringConfiguration {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Map<String, Object> order;
    private MvcResult response;

    @Given("an order payload for country {string} with {int} item and unit price {double}")
    public void orderPayload(String country, Integer items, Double unitPrice) {
        order = new HashMap<>();
        order.put("orderId", "BDD-" + country);
        order.put("documentType", "INVOICE");
        order.put("customerType", "RETAIL");
        order.put("countryCode", country);

        List<Object> list = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("itemId", country.equals("UY") ? "P-UY" : "P-BR");
        item.put("quantity", 1);
        item.put("unitPrice", new BigDecimal(String.valueOf(unitPrice)));
        item.put("currency", country.equals("BR") ? "BRL" : "UYU");
        list.add(item);
        order.put("items", list);
    }

    @And("the item has inline taxes:")
    public void inlineTaxes(io.cucumber.datatable.DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        List<Object> taxes = new ArrayList<>();
        for (var r : rows) {
            Map<String, Object> t = new HashMap<>();
            t.put("type", r.get("type"));
            t.put("name", r.get("name"));
            if (r.get("rate") != null && !r.get("rate").isBlank())
                t.put("rate", new BigDecimal(r.get("rate")));
            if (r.get("amountPerUnit") != null && !r.get("amountPerUnit").isBlank())
                t.put("amountPerUnit", new BigDecimal(r.get("amountPerUnit")));
            taxes.add(t);
        }
        Map<String, Object> item = ((List<Map<String, Object>>) order.get("items")).get(0);
        item.put("taxes", taxes);
    }

    @When("I POST it to {string}")
    public void i_post_it(String path) throws Exception {
        Map<String, Object> payload = Map.of("order", order);
        response = mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Then("the response is {int}")
    public void resp_code(Integer code) throws Exception {
        assertThat(response.getResponse().getStatus()).isEqualTo(code);
    }

    @Then("item {string} totalTax equals {double}")
    public void item_total_tax_equals(String itemId, Double expected) throws Exception {
        Map<String, Object> body = mapper.readValue(response.getResponse().getContentAsString(), Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");
        Map<String, Object> item = items.stream().filter(i -> i.get("itemId").equals(itemId)).findFirst().orElseThrow();
        BigDecimal totalTax = new BigDecimal(String.valueOf(item.get("totalTax")));
        assertThat(totalTax).isEqualByComparingTo(new BigDecimal(String.valueOf(expected)));
    }

    @Then("item {string} has a tax line {string}")
    public void item_has_tax_line(String itemId, String componentKey) throws Exception {
        Map<String, Object> body = mapper.readValue(response.getResponse().getContentAsString(), Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");
        Map<String, Object> item = items.stream().filter(i -> i.get("itemId").equals(itemId)).findFirst().orElseThrow();
        List<Map<String, Object>> breakdown = (List<Map<String, Object>>) item.get("breakdown");
        boolean any = breakdown.stream().anyMatch(l -> componentKey.equals(l.get("componentKey")));
        assertThat(any).isTrue();
    }
}
