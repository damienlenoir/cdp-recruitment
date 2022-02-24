package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String query) {
        return eventRepository.findAllBy().stream()
                .filter(event -> event.getBands().stream()
                        .anyMatch(band -> band.getMembers().stream()
                                .anyMatch(member -> member.getName().contains(query))
                        )).collect(Collectors.toList());
    }

    public List<Event> getFilteredEventsWithCount(String query) {
        // to get the filtred Item with the counter
        // I prefere the old fashion way, with stream it would be very hard to read and understand
        // Keep it simple, stupid !
        List<Event> events = new ArrayList<>();
        for (Event event : eventRepository.findAllBy()) {
            List<Band> bands = new ArrayList<>();
            for (Band band : event.getBands()) {
                long count = band.getMembers().stream().filter(o -> o.getName().contains(query)).count();
                if (count > 0) {
                    band.setName(String.format("%s [%s]", band.getName(),  count));
                    bands.add(band);
                }
            }
            if (bands.size() > 0) {
                event.setTitle(String.format("%s [%s]", event.getTitle(),  bands.size()));
                events.add(event);
            }
        }

        return events;
    }

    public void updateEvent(Event event) {
        eventRepository.updateEvent(event.getNbStars(), event.getComment(), event.getId());
    }
}
