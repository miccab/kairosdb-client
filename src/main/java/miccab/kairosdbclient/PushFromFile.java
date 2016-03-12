package miccab.kairosdbclient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by michal on 12.03.16.
 */
public class PushFromFile {
    public static void main(String [] args) throws IOException {
        final Client client = ClientFactory.createClient(getProperties());
        Files.lines(Paths.get(args[0])).forEach(line -> pushMetric(line, client));
        client.close();
    }

    private static void pushMetric(String line, Client client) {
        if (line.trim().length() == 0) {
            return;
        }
        final String [] columns = line.split("\\s");
        final MetricValue metricValue = new MetricValue(columns[0], Long.parseLong(columns[1]), Double.parseDouble(columns[2]));
        if (columns.length > 3) {
            for (int i = 3; i < columns.length; i++) {
                metricValue.addTag(columns[i]);
            }
        }
        client.send(metricValue);
    }

    private static Properties getProperties() throws IOException {
        final Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(new File("./src/main/resources/app.properties"))));
        return properties;
    }

}
