package uy.com.geocom.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import uy.com.geocom.domain.component.TaxComponent;

public interface TaxComponentRepository extends MongoRepository<TaxComponent, String> {}
