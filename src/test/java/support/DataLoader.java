package support;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.OrderData;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;

public final class DataLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<OrderData> jsonOrders(String resourcePath) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (is == null) throw new IllegalArgumentException("No se encontró: " + resourcePath);
            return Arrays.asList(MAPPER.readValue(is, OrderData[].class));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private DataLoader() {}
}
