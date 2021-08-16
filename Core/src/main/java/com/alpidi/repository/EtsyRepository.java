package com.alpidi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.alpidi.model.Etsy;
import com.alpidi.model.User;

public interface EtsyRepository extends MongoRepository<Etsy, String> {
	Optional<Etsy> findByuserid(String user_id);
	
	Boolean existsByetsyuserid(String etsyuserid);
}
