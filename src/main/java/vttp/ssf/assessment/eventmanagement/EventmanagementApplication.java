package vttp.ssf.assessment.eventmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	@Autowired
	DatabaseService dataBaseService;
	

	@Autowired
	RedisRepository redisRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("hello wrold");
		if (redisRepository.getNumberOfEvents()==0) {
			List<Event> events = dataBaseService.readFile("events.json");
			for (Event event : events) {
				System.out.println(event.toString());
				
				redisRepository.saveRecord(event);

			}
		
	
	}


	// TODO: Task 1

	// @Override
	// public void run(String... args) throws Exception {
	// 	// TODO Auto-generated method stub
	// 	List<Event> events = dataBaseService.readFile("events.json");
	// 	for (Event event : events) {
	// 		System.out.println(event.toString());
	// 		redisRepository.saveRecord(event);

	// 	}

	// }
	

}
}
