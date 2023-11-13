package player.stats.metrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long>, JpaSpecificationExecutor<Metric> {

    List<Metric> findAllByNameAndSystem(String name, String system);

    Metric findMetricById(Long id);

    List<Metric> findAllBySystem(String system);

    List<Metric> findAllByDateGreaterThanEqualAndSystem(Integer from, String system);

    List<Metric> findAllByDateLessThanEqualAndSystem(Integer to, String system);

    List<Metric> findAllByDateBetweenAndSystem(Integer from, Integer to, String system);

    List<Metric> findAllByNameAndSystemAndDateGreaterThanEqual(String name, String system, Integer from);

    List<Metric> findAllByNameAndSystemAndDateLessThanEqual(String name, String system, Integer to);

    List<Metric> findAllByNameAndSystemAndDateBetween(String name, String system, Integer from, Integer to);


}
