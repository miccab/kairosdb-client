package miccab.kairosdbclient;

/**
 * Created by michal on 12.03.16.
 */
public interface Client {
    void send(MetricValue metricValue);

    void close();
}
