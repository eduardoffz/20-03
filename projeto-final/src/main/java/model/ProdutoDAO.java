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
 *
 * @author Usuario
 */
    public class ProdutoDAO {
        
        public List<ProdutoBean> ler () {
            List<ProdutoBean> produtos = new ArrayList();
            try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("SELECT * FROM produto");
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                ProdutoBean produto = new ProdutoBean();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setFornecedor(rs.getString("fornecedor"));
                produto.setUnidadeDeMedida(rs.getString("unidade_medida"));
                produto.setDataValidade(rs.getDate("data_validade").toLocalDate());
                produto.setEstoqueMinimo(rs.getDouble("estoque_minimo"));
                produto.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                
                produtos.add(produto);
            }
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return produtos;
        }
}
