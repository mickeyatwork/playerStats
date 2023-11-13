package player.stats.metrics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long>, JpaSpecificationExecutor<Metric> {

    List<Metric> findAllByNameAndSystem(String name, String system);

    Metric findMetricById(Long id);

    List<Metric> findAllBySystem(String system);

    List<Metric> findAllByTimestampGreaterThanEqualAndSystem(Integer from, String system);

    List<Metric> findAllByTimestampLessThanEqualAndSystem(Integer to, String system);

    List<Metric> findAllByTimestampBetweenAndSystem(Integer from, Integer to, String system);

    List<Metric> findAllByNameAndSystemAndTimestampGreaterThanEqual(String name, String system, Integer from);

    List<Metric> findAllByNameAndSystemAndTimestampLessThanEqual(String name, String system, Integer to);

    List<Metric> findAllByNameAndSystemAndTimestampBetween(String name, String system, Integer from, Integer to);


}
