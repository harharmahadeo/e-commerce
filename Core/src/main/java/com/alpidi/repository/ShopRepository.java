package com.alpidi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.alpidi.model.Shops;

public interface ShopRepository extends MongoRepository<Shops, String> {
	Optional<Shops> findByetsyuserid(String etsyuserid);
	
	Boolean existsByetsyuserid(String userid);
}
