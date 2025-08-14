package uy.com.geocom.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import uy.com.geocom.domain.rule.TaxAdjustmentRule;

public interface TaxAdjustmentRuleRepository extends MongoRepository<TaxAdjustmentRule, String> {
  List<TaxAdjustmentRule> findByIsActiveTrue();
}
