package info.anatolko.tl.repository;

import info.anatolko.tl.domain.ColorLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for working with color-log table
 */
public interface ColorLogRepository extends JpaRepository<ColorLog, Integer> {

    /**
     * JPA request to find all records about lights before some date.
     *
     * @param inputDate date for searching
     * @param pageable page setting
     * @return list of dates with lights
     */
    List<ColorLog> findByDateBeforeOrderByDateDesc(LocalDateTime inputDate, Pageable pageable);
}
