package tests.utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties p = new Properties();
    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) p.load(in);
        } catch (Exception ignored) {}
    }

    public static String get(String key, String def){
        // 1) JVM parametresi (-Dusername=Test) varsa al
        String sys = System.getProperty(key);
        if (sys != null) return sys;

        // 2) config.properties dosyasından al
        String prop = p.getProperty(key);
        if (prop != null) return prop;

        // 3) yoksa default dön
        return def;
    }
}
