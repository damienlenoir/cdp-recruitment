package adeo.leroymerlin.cdp;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends Repository<Event, Long> {

    void deleteById(Long eventId);

    @Transactional(readOnly = true)
    List<Event> findAllBy();

    Event save(Event event);

    // The below method also work because the front only update comment and nbStars
    // But the more basic "save" is better for futur evolutions of the app
    /*@Transactional
    @Modifying
    @Query("update Event e set e.nbStars = :nbStars, e.comment = :comment where e.id = :id")
    void updateEvent(Integer nbStars, String comment, Long id);*/

}
