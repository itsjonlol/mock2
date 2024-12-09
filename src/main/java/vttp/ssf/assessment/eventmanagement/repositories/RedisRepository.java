package vttp.ssf.assessment.eventmanagement.repositories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.constant.ConstantVar;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired
    @Qualifier(ConstantVar.template01)
    RedisTemplate<String, String> template;
    

	// TODO: Task 2
	public void saveRecord(Event event) {
		template.opsForHash().put(ConstantVar.redisKey,String.valueOf(event.getEventId()),event.toString()); // put string cause of serializer
		// template.opsForHash().put(ConstantVar.redisKey,event.getEventId(),event.toString());
	}
	
	public void saveRecord2(Event event) {
		// private Integer eventId;
		// private String eventName;
		// private Integer eventSize;
		// private Long eventDate;
		// private Integer participants;

		JsonObject eventJsonObject = Json.createObjectBuilder()
										 .add("eventId",event.getEventId())
										 .add("eventName",event.getEventName())
										 .add("eventSize",event.getEventSize())
										 .add("eventDate",event.getEventDate())
										 .add("participants",event.getParticipants())
										 .build();
		template.opsForHash().put(ConstantVar.redisKey,String.valueOf(event.getEventId()),eventJsonObject.toString());
	}

	// TODO: Task 3
	public Integer getNumberOfEvents() {
		// return (int) (long) template.opsForHash().size(ConstantVar.redisKey);
		return template.opsForHash().size(ConstantVar.redisKey).intValue();
	}
	

	


	// TODO: Task 4
	public Event getEvent(Integer index) {
		String rawData = (String) template.opsForHash().get(ConstantVar.redisKey,String.valueOf(index));
		String[] data = rawData.split(",");
		Event event = new Event();
		Integer eventId = Integer.parseInt(data[0]);
		String eventName = data[1];
		Integer eventSize = Integer.parseInt(data[2]);
		Long eventDate = Long.parseLong(data[3]);
		Integer participants = Integer.parseInt(data[4]);
		event.setEventId(eventId);
		event.setEventName(eventName);
		event.setEventSize(eventSize);
		event.setEventDate(eventDate);
		event.setParticipants(participants);

		return event;
	}

	public Event getEvent2(Integer index) {
		String rawDataJsonString = (String) template.opsForHash().get(ConstantVar.redisKey,String.valueOf(index));
		InputStream is = new ByteArrayInputStream(rawDataJsonString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject dataJson = reader.readObject();
		
		Event event = new Event();
		Integer eventId = dataJson.getInt("eventId");
		String eventName = dataJson.getString("eventName");
		Integer eventSize = dataJson.getInt("eventSize");
		Long eventDate = dataJson.getJsonNumber("eventDate").longValue();
		Integer participants = dataJson.getInt("participants");
		event.setEventId(eventId);
		event.setEventName(eventName);
		event.setEventSize(eventSize);
		event.setEventDate(eventDate);
		event.setParticipants(participants);

		return event;


	}

	public void updateValue(String redisKey,String mapKey,String value) {
        template.opsForHash().put(redisKey, mapKey,value); //hset c01 email fred@gmail.com
    }
	public Boolean hashExists(String redisKey) {
        return template.hasKey(redisKey);
    }
}
