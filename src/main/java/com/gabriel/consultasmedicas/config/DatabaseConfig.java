package com.gabriel.consultasmedicas.config;

import org.springframework.context.annotation.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class DatabaseConfig {

    static {
        String url = "jdbc:postgresql://localhost:5432/";
        String dbName = "sistema-consultas-medicas";
        String user = "postgres";
        String password = "1234"; 

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            
            var resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
            
            if (!resultSet.next()) {
                statement.executeUpdate("CREATE DATABASE \"" + dbName + "\"");
                System.out.println("✅ Banco de dados '" + dbName + "' criado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Nota: Não foi possível criar o banco automaticamente (pode ser que já exista).");
        }
    }
}