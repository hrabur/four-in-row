package pu.fmi.connect4.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



public interface GameRepository 
	extends JpaRepository<Game, UUID> {
	
	List<Game> findFirst10ByOrderByStartTimeDesc();
}
