package adeo.leroymerlin.cdp;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends Repository<Event, Long> {

    @Transactional(readOnly = true)
    List<Event> findAllBy();

    void deleteById(Long eventId);

    // I choosed to update this way because (for now) the front only update comment and nbStars
    @Transactional
    @Modifying
    @Query("update Event e set e.nbStars = :nbStars, e.comment = :comment where e.id = :id")
    void updateEvent(Integer nbStars, String comment, Long id);

    // We can also use the more basic "save"
    // It would be more generic for futur evolutions of the app
    // It should look like ->

    // Event save(Event event);
}
