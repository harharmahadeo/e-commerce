package com.alpidi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.ListingResult;

public interface ListingRepository extends MongoRepository<ListingResult, String> {

}
