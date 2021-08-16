package com.alpidi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.VectorFiles;

public interface VectorfilesRepository extends MongoRepository<VectorFiles, String> {
	Boolean existsBylistingid(String listingid);
}
