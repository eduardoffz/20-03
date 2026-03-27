package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3337/caixa_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void inicializar() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "usuario VARCHAR(100) NOT NULL UNIQUE," +
                "senha VARCHAR(100) NOT NULL)"
            );

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS movimentacoes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "descricao VARCHAR(255) NOT NULL," +
                "valor DOUBLE NOT NULL," +
                "tipo VARCHAR(10) NOT NULL," +
                "data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
            );

            stmt.execute(
                "INSERT IGNORE INTO usuarios (usuario, senha) VALUES ('admin', '123')"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar banco: " + e.getMessage(), e);
        }
    }
}
