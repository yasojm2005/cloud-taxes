package uy.com.geocom.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ConditionalOnBean(MongoTemplate.class)
@RequiredArgsConstructor
public class SeedDataLoader implements CommandLineRunner {
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Environment env;
    @Value("classpath:seed/tax_rules_embedded.json")
    private Resource taxRulesRes;
    @Value("classpath:seed/tax_adjustment_rules.json")
    private Resource taxAdjustmentsRes;

    @Override
    public void run(String... args) {
        boolean enabled = env.getProperty("app.seed.enabled", Boolean.class, true);
        if (!enabled) {
            return;
        }
        seedIfEmpty("tax_rules", taxRulesRes);
        seedIfEmpty("tax_adjustment_rules", taxAdjustmentsRes);
    }

    private void seedIfEmpty(String collection, Resource resource) {
        try {
            long count = mongoTemplate.getCollection(collection).countDocuments();
            if (count > 0) {
                return;
            }
            if (!resource.exists()) {
                return;
            }
            try (InputStream is = resource.getInputStream()) {
                ArrayNode arr = (ArrayNode) objectMapper.readTree(is);
                List<Document> docs = new ArrayList<>();
                for (JsonNode node : arr) {
                    docs.add(Document.parse(objectMapper.writeValueAsString(node)));
                }
                if (!docs.isEmpty()) {
                    mongoTemplate.getCollection(collection).insertMany(docs);
                }
            }
        } catch (Exception e) {
        }
    }
}
