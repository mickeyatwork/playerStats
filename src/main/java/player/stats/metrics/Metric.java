package player.stats.metrics;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "metric_generator")
    @SequenceGenerator(name = "metric_generator", sequenceName = "metric_seq", allocationSize = 1)
    private Long id;
    private String system;
    private String name;
    private Date date;
    private Integer value;
    private Long timestamp;

    public Metric() {
    }

    public Metric(String system, String name, Integer value) {
        this.system = system;
        this.name = name;
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
