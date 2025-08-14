package uy.com.geocom.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

import uy.com.geocom.domain.rule.TaxRule;

public interface TaxRuleRepository extends MongoRepository<TaxRule, String> {
  Optional<TaxRule> findByKeyAndActiveTrue(String key);
}
