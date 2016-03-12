package miccab.kairosdbclient;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by michal on 12.03.16.
 */
public class MetricValue {
    private final String metricName;
    private final long timestamp;
    private final double value;
    private List<String> tags;

    public MetricValue(String metricName, long timestamp, double value) {
        this.metricName = metricName;
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getMetricName() {
        return metricName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    public void addTag(String tagWithValue) {
        if (tags == null) {
            tags = new LinkedList<>();
        }
        tags.add(tagWithValue);
    }

    public List<String> getTags() {
        return tags;
    }
}
