package sn.brasilburger.config.factory.database;

import java.util.HashMap;
import java.util.Map;

public final class EntityManager {

    public EntityManager() {
    }

    public static Map<String, String> persistanceUnit(SGBDName sgbdName) {
        switch (sgbdName) {
            case POSTGRESQL:
                return persistanceUnitPostgre();
            case MYSQL:
                return persistanceUnitMysql();
            default:
                return null;
        }
    }

    private static Map<String, String> persistanceUnitMysql() {
        Map<String, String> config = new HashMap<>();
        config.put("driver", "com.mysql.cj.jdbc.Driver");
        config.put("url", "jdbc:mysql://localhost:3306/brasilburger");
        config.put("user", "root");
        config.put("password", "root");
        return config;
    }

    private static Map<String, String> persistanceUnitPostgre() {
        Map<String, String> config = new HashMap<>();
        config.put("driver", "org.postgresql.Driver");
        config.put("url", "jdbc:postgresql://localhost:5432/brasilburger");
        config.put("user", "postgres");
        config.put("password", "passer");
        return config;
    }
}
