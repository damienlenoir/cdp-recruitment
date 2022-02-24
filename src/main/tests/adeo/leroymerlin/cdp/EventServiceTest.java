package adeo.leroymerlin.cdp;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@FixMethodOrder(MethodSorters.JVM)
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Test
    public void findEvents() {
        List<Event> events = eventService.getEvents();
        Assert.assertNotNull(events);
    }

    @Test
    public void updateEvent() {
        List<Event> events = eventService.getEvents();
        events.forEach(event -> {
            event.setComment("test");
        });

        events.forEach(event -> eventService.updateEvent(event));
        events = eventService.getEvents();

        Assert.assertTrue(events.stream().allMatch(o -> o.getComment().equals("test")));
    }

    @Test
    public void findEventsWithQuery() {
        List<Event> events = eventService.getFilteredEvents("Wa");
        Assert.assertEquals(1, events.size());
    }

    @Test
    public void findEventsWithQueryPlusCount() {
        List<Event> events = eventService.getFilteredEventsWithCount("Wa");
        Assert.assertEquals(1, events.size());

        String titleWithCount = events.get(0).getTitle();

        String patternString = "\\[\\d]$";
        boolean match = Pattern.compile(patternString).matcher(titleWithCount).find();

        Assert.assertTrue(match);
    }

    @Test
    public void deleteEvent() {
        List<Event> events = eventService.getEvents();
        int count = events.size();
        eventService.delete(events.get(0).getId());

        Assert.assertEquals(eventService.getEvents().size(), count - 1);
    }
}