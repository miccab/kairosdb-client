package miccab.kairosdbclient.mina;

import miccab.kairosdbclient.Client;
import miccab.kairosdbclient.MetricValue;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by michal on 12.03.16.
 */
public class MinaClient implements Client {
    private final IoSession session;
    private final IoConnector connector;

    public MinaClient(IoSession session, IoConnector connector) {
        this.session = session;
        this.connector = connector;
    }

    @Override
    public void send(MetricValue metricValue) {
        final StringBuilder message = new StringBuilder();
        message.append("put");
        message.append(' ');
        message.append(metricValue.getMetricName());
        message.append(' ');
        message.append(metricValue.getTimestamp());
        message.append(' ');
        message.append(metricValue.getValue());
        final List<String> tags = metricValue.getTags();
        if (tags != null) {
            for (String tag : tags) {
                message.append(' ');
                message.append(tag);
            }
        }
        session.write(message.toString());
    }

    @Override
    public void close() {
        final CloseFuture future = session.closeOnFlush();
        future.awaitUninterruptibly(4, TimeUnit.SECONDS);
        connector.dispose();
    }
}
