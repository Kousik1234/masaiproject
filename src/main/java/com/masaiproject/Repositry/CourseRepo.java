package com.masaiproject.Repositry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.masaiproject.Entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

	@Query("select u from Course u where lastDate  >= ?1")
	List<Course> findbyexpierytime(LocalDate now);
	
}