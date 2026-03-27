/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para Tarefa
 * Esta classe é responsável por todas as operações de banco de dados relacionadas a tarefas
 * Trabalha com a tabela: tarefas (id_tarefa, titulo, descricao, responsavel, status)
 * Implementa as operações CRUD: Create (criar), Read (obter), Update (atualizar) e Delete (deletar)
 * @author Usuario
 */
public class TarefaDAO {
    
    /**
     * Método criar - Insere uma nova tarefa no banco de dados
     * @param tarefa Um objeto TarefaBean contendo os dados da tarefa a ser criada
     */
    public void criar(TarefaBean tarefa) {
        try {
            // Estabelece conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            // Prepara a instrução SQL para inserir uma nova tarefa
            // Os ? são placeholders para os valores que serão inseridos
            stmt = conn.prepareStatement("INSERT INTO tarefas (titulo, descricao, responsavel, status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, tarefa.getTitulo());           // Define o título da tarefa
            stmt.setString(2, tarefa.getDescricao());       // Define a descrição da tarefa
            stmt.setString(3, tarefa.getResponsavel());     // Define o responsável pela tarefa
            stmt.setString(4, tarefa.getStatus() != null ? tarefa.getStatus() : "Pendente"); // Define status, ou "Pendente" se nulo
            
            // Executa a instrução de inserção no banco
            stmt.executeUpdate();
            stmt.close();   // Fecha a instrução preparada
            conn.close();   // Fecha a conexão com o banco
            
        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            e.printStackTrace();
        }
    }
    
    /**
     * Método listar - Retorna todas as tarefas do banco de dados
     * @return Uma lista contendo todos os objetos TarefaBean cadastrados
     */
    public List<TarefaBean> listar() {
        // Cria uma lista para armazenar as tarefas obtidas do banco
        List<TarefaBean> tarefas = new ArrayList();
        try {
            // Estabelece conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            // Prepara a instrução SQL para buscar todas as tarefas, ordenadas por ID (decrescente)
            stmt = conn.prepareStatement("SELECT * FROM tarefas ORDER BY id_tarefa DESC");
            
            // Executa a consulta e obtém os resultados
            rs = stmt.executeQuery();
            // Itera por cada linha de resultado retornada
            while (rs.next()) {
                // Cria um novo objeto TarefaBean para armazenar os dados da linha
                TarefaBean tarefa = new TarefaBean();
                tarefa.setId(rs.getInt("id_tarefa"));           // Obtém o ID da tarefa
                tarefa.setTitulo(rs.getString("titulo"));       // Obtém o título
                tarefa.setDescricao(rs.getString("descricao")); // Obtém a descrição
                tarefa.setResponsavel(rs.getString("responsavel")); // Obtém o responsável
                tarefa.setStatus(rs.getString("status"));       // Obtém o status
                
                // Adiciona o objeto TarefaBean à lista
                tarefas.add(tarefa);
            }
            
            // Fecha os recursos utilizados
            rs.close();     // Fecha o conjunto de resultados
            stmt.close();   // Fecha a instrução preparada
            conn.close();   // Fecha a conexão com o banco
            
        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            e.printStackTrace();
        }
        // Retorna a lista de tarefas (vazia se houve erro)
        return tarefas;
    }
    
    /**
     * Método atualizar - Modifica os dados de uma tarefa existente no banco de dados
     * @param tarefa Um objeto TarefaBean com os dados atualizados (o ID deve estar preenchido)
     */
    public void atualizar(TarefaBean tarefa) {
        try {
            // Estabelece conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            // Prepara a instrução SQL para atualizar uma tarefa existente
            // WHERE id_tarefa = ? garante que apenas a tarefa com esse ID seja atualizada
            stmt = conn.prepareStatement("UPDATE tarefas SET titulo = ?, descricao = ?, responsavel = ?, status = ? WHERE id_tarefa = ?");
            stmt.setString(1, tarefa.getTitulo());       // Atualiza o título
            stmt.setString(2, tarefa.getDescricao());   // Atualiza a descrição
            stmt.setString(3, tarefa.getResponsavel()); // Atualiza o responsável
            stmt.setString(4, tarefa.getStatus());      // Atualiza o status
            stmt.setInt(5, tarefa.getId());             // Define qual tarefa será atualizada (por ID)
            
            // Executa a instrução de atualização no banco
            stmt.executeUpdate();
            stmt.close();   // Fecha a instrução preparada
            conn.close();   // Fecha a conexão com o banco
            
        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            e.printStackTrace();
        }
    }
    
    /**
     * Método deletar - Remove uma tarefa do banco de dados
     * @param id O ID da tarefa a ser deletada
     */
    public void deletar(int id) {
        try {
            // Estabelece conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            
            // Prepara a instrução SQL para deletar uma tarefa pelo ID
            stmt = conn.prepareStatement("DELETE FROM tarefas WHERE id_tarefa = ?");
            stmt.setInt(1, id); // Define qual tarefa será deletada (por ID)
            
            // Executa a instrução de deleção no banco
            stmt.executeUpdate();
            stmt.close();   // Fecha a instrução preparada
            conn.close();   // Fecha a conexão com o banco
            
        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            e.printStackTrace();
        }
    }
    
    /**
     * Método obterPorId - Busca uma tarefa específica pelo seu ID
     * @param id O ID da tarefa a ser buscada
     * @return Um objeto TarefaBean com os dados da tarefa encontrada, ou null se não existir
     */
    public TarefaBean obterPorId(int id) {
        // Inicializa a variável para armazenar a tarefa encontrada
        TarefaBean tarefa = null;
        try {
            // Estabelece conexão com o banco de dados
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            // Prepara a instrução SQL para buscar uma tarefa específica pelo ID
            stmt = conn.prepareStatement("SELECT * FROM tarefas WHERE id_tarefa = ?");
            stmt.setInt(1, id); // Define qual tarefa será buscada (por ID)
            
            // Executa a consulta
            rs = stmt.executeQuery();
            // Verifica se encontrou algum resultado
            if (rs.next()) {
                // Cria um novo objeto TarefaBean para armazenar os dados encontrados
                tarefa = new TarefaBean();
                tarefa.setId(rs.getInt("id_tarefa"));           // Obtém o ID
                tarefa.setTitulo(rs.getString("titulo"));       // Obtém o título
                tarefa.setDescricao(rs.getString("descricao")); // Obtém a descrição
                tarefa.setResponsavel(rs.getString("responsavel")); // Obtém o responsável
                tarefa.setStatus(rs.getString("status"));       // Obtém o status
            }
            
            // Fecha os recursos utilizados
            rs.close();     // Fecha o conjunto de resultados
            stmt.close();   // Fecha a instrução preparada
            conn.close();   // Fecha a conexão com o banco
            
        } catch (SQLException e) {
            // Captura e exibe erros de SQL
            e.printStackTrace();
        }
        // Retorna a tarefa encontrada (ou null se não foi encontrada)
        return tarefa;
    }
    /* FIM DA CLASSE TarefaDAO */
}
