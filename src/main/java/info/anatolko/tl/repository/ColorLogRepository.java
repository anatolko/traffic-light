package info.anatolko.tl.repository;

import info.anatolko.tl.domain.ColorLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for working with color-log table
 */
public interface ColorLogRepository extends JpaRepository<ColorLog, Integer> {
}
