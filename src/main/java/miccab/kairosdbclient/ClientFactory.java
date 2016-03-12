package miccab.kairosdbclient;

import miccab.kairosdbclient.mina.ClientSessionHandler;
import miccab.kairosdbclient.mina.MinaClient;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * Created by michal on 12.03.16.
 */
public class ClientFactory {
    public static Client createClient(Properties properties) {
        NioSocketConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(Long.parseLong(properties.getProperty("connectionTimeoutMillis", "10000")));
        connector.setHandler(new ClientSessionHandler());
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory()));
        connector.getFilterChain().addLast("logging", new LoggingFilter());

        final ConnectFuture futureConnection = connector.connect(new InetSocketAddress(properties.getProperty("connectionHost", "localhost"),
                                                                                       Integer.parseInt(properties.getProperty("connectionPort", "4242"))));
        futureConnection.awaitUninterruptibly();
        return new MinaClient(futureConnection.getSession(), connector);
    }
}
