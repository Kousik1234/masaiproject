package com.masaiproject.Repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masaiproject.Entity.Score;

@Repository
public interface ScoreRepo extends JpaRepository<Score, Integer>{


}