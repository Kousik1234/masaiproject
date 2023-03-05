package com.masaiproject.Repositry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.masaiproject.Entity.OtpEntity;

@Repository
public interface OtpRepo extends JpaRepository<OtpEntity, Integer> {

	// List<OtpEntity> findByEndTimeBefore(LocalDateTime now);

	@Query("select u from OtpEntity u where otpExpiry  <= ?1")
	List<OtpEntity> findbyexpierytime(LocalDateTime now);

	Optional<OtpEntity> findByUsername(String username);

	@Modifying
	@Query("update OtpEntity o set o.otp = :otp where o.username = :username")
	int updateOtpByUsername(@Param("username") String username, @Param("otp") Integer otp);

}
