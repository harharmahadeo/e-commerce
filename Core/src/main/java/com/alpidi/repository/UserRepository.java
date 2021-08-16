package com.alpidi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.alpidi.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	  Optional<User> findByEmail(String email);
	  
	  //List<User> findByroleNot(String role);
	  //List<User> findByrole(String role);
	  
//	  @Query("select u.* from users u where u.role !== 'admin' && u.isdeleted = 0")
//	  List<User> findByroleNot(String role);
	  
	  List<User> findByRoleNotAndIsdeleted(String role,Boolean isdeleted);
	  
	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	}