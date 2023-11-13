package player.stats.metrics;

import org.springframework.web.bind.annotation.*;
import player.stats.error.handling.MetricNotFoundException;
import player.stats.error.handling.SystemNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class MetricController {

    private final MetricRepository repository;

    MetricController(MetricRepository repository) { this.repository = repository; }

    @GetMapping("/metrics")
    @ResponseBody
    public List<Metric> all(@RequestParam(value = "system", required = true) String system,
                            @RequestParam(value = "name", required = false, defaultValue = "") String name,
                            @RequestParam(value = "from", required = false, defaultValue= "0") Integer from,
                            @RequestParam(value = "to", required = false, defaultValue= "0") Integer to) {

        // If there's no valid system match then none of the further conditions should be checked
        if (system.matches("player_stats")) {

            // If only the 'from' timestamp parameter has been provided
            if (!from.equals(0) && to.equals(0)) {

                // Now check if the name value matches any of the metric names
                if (name.matches("goals|assists")) {
                    return repository.findAllByNameAndSystemAndTimestampGreaterThanEqual(name, system, from);
                } else if (!name.matches("goals|assists") && !name.equals("")) {

                    // If they don't match any value then it's not a valid name
                    throw new MetricNotFoundException(name);
                }
                return repository.findAllByTimestampGreaterThanEqualAndSystem(from, system);
            }
            // If only the 'to' timestamp parameter has been provided
            if (!to.equals(0) && from.equals(0)) {

                // Now check if the name value matches any of the metric names
                if (name.matches("goals|assists")) {
                    return repository.findAllByNameAndSystemAndTimestampLessThanEqual(name, system, to);
            } else if (!name.matches("goals|assists") && !name.equals("")) {
                    throw new MetricNotFoundException(name);
                }
                return repository.findAllByTimestampLessThanEqualAndSystem(to, system);
            }
            if (!from.equals(0) && !to.equals(0)){

                // Now check if the name value matches any of the metric names
                if (name.matches("goals|assists")) {
                    return repository.findAllByNameAndSystemAndTimestampBetween(name, system, from, to);
                } else if (!name.matches("goals|assists") && !name.equals("")) {
                    throw new MetricNotFoundException(name);
                }
                return repository.findAllByTimestampBetweenAndSystem(from, to, system);
            }
            // If just the 'name' parameter has been provided
            if (name.matches("goals|assists")) {

            // If the name matches one of the metric names return them
            return repository.findAllByNameAndSystem(name, system);
            } else if (!name.matches("goals|assists") && !name.equals("")) {
                throw new MetricNotFoundException(name);
            }

            //Returns if only the system parameter was provided
            return repository.findAllBySystem(system);
        } else {
            throw new SystemNotFoundException(system);
        }
    }

    @GetMapping("/metrics/{id}")
    Metric one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new MetricNotFoundException(id));
    }

    @GetMapping("/metricsummary")
    @ResponseBody
    public List<Metric> summary(@RequestParam(value = "system", required = true) String system,
                                @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                @RequestParam(value = "from", required = false, defaultValue= "0") Integer from,
                                @RequestParam(value = "to", required = false, defaultValue= "0") Integer to) {

        List<Metric> results = repository.findAllBySystem(system);

        // First check the system value is valid
        if (system.matches("player_stats")) {

            // Now start checking the optional parameters
            if (name.matches("goals|assists")) {

                int combinedTotals = 0;
                results = repository.findAllByNameAndSystem(name, system);

                for (Metric metric : results) {
                    combinedTotals = combinedTotals + metric.getValue();
                }
                List<Metric> nameMatchResult = new ArrayList<Metric>();
                nameMatchResult.add(new Metric("Summary for " + system, name, combinedTotals));

                return nameMatchResult;

            } else if (!name.matches("goals|assists") && !name.equals("")) {
                throw new MetricNotFoundException(name);
            } else {

                List<Metric> nameMatchResult = new ArrayList<Metric>();
                List<String> validMetricNames = Arrays.asList("goals", "assists");

                for (String metricName : validMetricNames) {

                    int combinedTotals = 0;
                    for (Metric metric : results) {
                        if (metricName.equals(metric.getName())) {
                            combinedTotals = combinedTotals + metric.getValue();
                        }
                    }
                    nameMatchResult.add(new Metric("Summary for " + system + ": " + metricName, metricName, combinedTotals));
                }
                return nameMatchResult;
            }
        } else {
            throw new SystemNotFoundException(system);
        }
    }

    @PostMapping("/metrics")
    Metric newMetric(@RequestBody Metric newMetric) {

        newMetric.setDate(new Date());
        newMetric.setTimestamp((System.currentTimeMillis() / 1000L));

        String system = newMetric.getSystem();
        String name = newMetric.getName();

        if (newMetric.getName() == null | newMetric.getSystem() == null) {
            throw new MetricNotFoundException();
        } else if(!system.matches("player_stats|test") | !name.matches("goals|assists")) {
            throw new MetricNotFoundException();
       } else {
          if (newMetric.getValue() == null){
              newMetric.setValue(1);
          }
        return repository.save(newMetric);
      }
    }

    @PutMapping("/metrics/{id}")
    Metric replacePlayer(@RequestBody Metric newMetric, @PathVariable Long id) {

        String system = newMetric.getSystem();
        String name = newMetric.getName();

        Metric existingValues = repository.findMetricById(id);

        if (newMetric.getName() == null | newMetric.getSystem() == null | !system.equals(existingValues.getSystem())) {
            throw new SystemNotFoundException();
        } else if(!name.matches("goals|assists")) {
            throw new MetricNotFoundException();
        } else if(newMetric.getValue() == null){
            newMetric.setValue(existingValues.getValue() + 1);
        }
        return repository.findById(id)
                .map(metric -> {
                    metric.setName(newMetric.getName());
                    metric.setValue(newMetric.getValue());
                    return repository.save(metric);
                })
                .orElseGet(() -> {
                    newMetric.setId(id);
                    return repository.save(newMetric);
                });
    }
}
