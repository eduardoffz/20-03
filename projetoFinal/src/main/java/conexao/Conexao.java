/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/gerenciador";
    private static final String user = "root";
    private static final String senha = "1234";
    
    public static Connection conectar () {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public void testarConexao() {
        Connection conn = conectar();
        if(conn == null) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados");
        } else {
            JOptionPane.showMessageDialog(null, "Conectado com sucesso");
        }
    }
    
}
