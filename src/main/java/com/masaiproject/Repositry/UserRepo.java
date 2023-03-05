package com.masaiproject.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.masaiproject.Entity.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

	public Optional<Users> findByEmail(String email);
	
	public Optional<Users> findByPhoneNo(String phoneNo);

	
	
	
}