package pu.fmi.connect4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
	
	/**
	 * Demo of filed injection (not recomended)
	 */
	@Autowired
	public GameService gameService;

	@GetMapping("/")
	public String hello() {
		return "Hello!. It works.";
	}
}
