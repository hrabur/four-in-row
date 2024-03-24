package pu.fmi.connect4.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



public interface GameRepository 
	extends JpaRepository<Game, UUID> {
	
	// TODO: Add query method to find first 10 ordered by start time desc
	// Look here for examples: https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.limit-query-result
}
