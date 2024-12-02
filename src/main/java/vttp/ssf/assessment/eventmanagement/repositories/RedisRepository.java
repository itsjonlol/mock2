package vttp.ssf.assessment.eventmanagement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

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
	

	// TODO: Task 3
	public Integer getNumberOfEvents() {
		return (int) (long) template.opsForHash().size(ConstantVar.redisKey);
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

	public void updateValue(String redisKey,String mapKey,String value) {
        template.opsForHash().put(redisKey, mapKey,value); //hset c01 email fred@gmail.com
    }
}
