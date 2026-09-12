package com.example.erp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Configuration
public class DatabaseConfig {
    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Value("${DATABASE_DRIVER:org.h2.Driver}")
    private String driver;

    @Value("${DATABASE_USER:sa}")
    private String username;

    @Value("${DATABASE_PASSWORD:}")
    private String password;

    @Bean
    public DataSource dataSource() {
        String url = databaseUrl;
        String user = username;
        String pass = password;

        if (url == null || url.isBlank()) {
            url = "jdbc:h2:file:./data/erp;MODE=PostgreSQL;DB_CLOSE_DELAY=-1";
            driver = "org.h2.Driver";
            user = "sa";
            pass = "";
        } else if (url.startsWith("jdbc:postgresql://") || url.startsWith("postgresql://")) {
            String uriText = url.startsWith("jdbc:") ? url.substring(5) : url;
            URI uri = URI.create(uriText);
            if (uri.getUserInfo() != null) {
                String[] credentials = uri.getUserInfo().split(":", 2);
                user = credentials[0];
                if (credentials.length > 1) {
                    pass = java.net.URLDecoder.decode(credentials[1], StandardCharsets.UTF_8);
                }
            }
            String authority = uri.getHost();
            if (uri.getPort() > 0) authority += ":" + uri.getPort();
            url = "jdbc:postgresql://" + authority + uri.getRawPath()
                    + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery());
            driver = "org.postgresql.Driver";
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setDriverClassName(driver);
        return new HikariDataSource(config);
    }
}
