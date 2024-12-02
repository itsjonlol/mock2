package vttp.ssf.assessment.eventmanagement.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.constant.ConstantVar;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.User;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {
    @Autowired
    RedisRepository redisRepository;
    
    // TODO: Task 1
    // 
    // // "eventId": 1,
	// 	"eventName": "Christmas Eve Party",
	// 	"eventSize": 20,
	// 	"eventDate": 1703415600000,
	// 	"participants": 0
    public List<Event> readFile(String fileName) throws FileNotFoundException  {
        InputStream is = new FileInputStream(fileName);
        JsonReader reader = Json.createReader(is);
        JsonArray eventsJsonArray = reader.readArray();
        
        List<Event> events = new ArrayList<>();

        for (int i = 0; i<eventsJsonArray.size(); i++) {
            JsonObject individualEventJson = eventsJsonArray.getJsonObject(i);
            Event event = new Event();
            Integer eventId = individualEventJson.getInt("eventId");
            String eventName = individualEventJson.getString("eventName");
            Integer eventSize = individualEventJson.getInt("eventSize");
            JsonNumber eventDateJsonNumber = individualEventJson.getJsonNumber("eventDate");
            Long eventDate = eventDateJsonNumber.longValue();
            Integer participants = individualEventJson.getInt("participants");
            event.setEventId(eventId);
            event.setEventName(eventName);
            event.setEventSize(eventSize);
            event.setEventDate(eventDate);
            event.setParticipants(participants);
            events.add(event);
            
        }
        // FileReader reader = new FileReader(fileName);
        // BufferedReader br = new BufferedReader(reader);
        // String line ="";
        // Event event = new Event();
        // while ((line=br.readLine())!=null) {
            
        //     if (line.contains("eventId")) {
        //         String[] tokens = line.split(":");
        //         if (tokens[1].trim().endsWith(",")) {
        //             tokens[1] = tokens[1].trim().substring(0,tokens[1].length()-1); //remove the comma
        //         }
        //         String eventIdString = tokens[1].trim();
        //         event.setEventId(Integer.parseInt(eventIdString));
        //     } else if (line.contains("eventName")) {
        //         String[] tokens = line.split(":");
        //         if (tokens[1].trim().endsWith(",")) {
        //             tokens[1] = tokens[1].trim().substring(0,tokens[1].length()-1); //remove the comma
        //         }
        //         String eventNameString = tokens[1].trim();
        //         event.setEventName(eventNameString);

        //     } else if (line.contains("eventSize")) {
        //         String[] tokens = line.split(":");
        //         if (tokens[1].trim().endsWith(",")) {
        //             tokens[1] = tokens[1].trim().substring(0,tokens[1].length()-1); //remove the comma
        //         }
        //         String eventSizeString = tokens[1].trim();
        //         event.setEventSize(Integer.parseInt(eventSizeString));

        // }

        return events;

    }


    public List<Event> getEvents() {
        Integer numberOfEvents = redisRepository.getNumberOfEvents();
        System.out.println(numberOfEvents);
        List<Event> events = new ArrayList<>();
        for (int i = 0; i<numberOfEvents;i++) {
            Event event = redisRepository.getEvent(i+1);
            events.add(event);

        }
        return events;
    }

    public Event getEventById(Integer eventId) {
        return redisRepository.getEvent(eventId);
    }
    public void updateEventParticipation(Event event) {//update the event itself, not via id else no changes will be made
        
        redisRepository.updateValue(ConstantVar.redisKey, String.valueOf(event.getEventId()), event.toString()); 
    }
    public Boolean checkIfUserOver21(User user) {
        Date dobDate = user.getDOB(); // Assuming getDOB() returns a Date
        
        // Convert Date to LocalDate
        LocalDate dob = dobDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now(); // Get the current date

        // Calculate the period between DOB and today
        int age = Period.between(dob, today).getYears();
        return age >= 21; // Return true if age is 21 or more, false otherwise

        
    }
    public Boolean checkIfEventFull(Event event,Integer ticketsNumber) {
       
        Integer expectedTotal = event.getParticipants() + ticketsNumber;
        Integer maxCapacity = event.getEventSize();

        if (expectedTotal>maxCapacity) {
            return true;
        }

        return false;
    }
}
