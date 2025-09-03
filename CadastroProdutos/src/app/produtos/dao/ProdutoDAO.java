package app.produtos.dao;

import app.produtos.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/db_refeicoes";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    //Metodo para a conexao
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //Criar Produto
    public void inserirProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO refeicoes (comida, bebida, sobremesa) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getComida());
            stmt.setString(2, produto.getBebida());
            stmt.setString(3, produto.getSobremesa());

            stmt.executeUpdate();
        }
    }

        //listar produtos
         public List<Produto> listarProdutos() throws SQLException {
            List<Produto> refeicoes = new ArrayList<>();
            String sql = "SELECT id, comida, bebida, sobremesa FROM refeicoes ORDER BY id";

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String comida = rs.getString("comida");
                    String bebida = rs.getString("bebida");
                    String sobremesa = rs.getString("sobremesa");
                    refeicoes.add(new Produto(id, comida, bebida, sobremesa));
                }
            }
         return refeicoes;
    }
    public void atualizarProduto(Produto refeicao) throws SQLException {
        String sql = "UPDATE refeicoes SET comida = ?, bebida = ?, sobremesa = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, refeicao.getComida());
            stmt.setString(2, refeicao.getBebida());
            stmt.setString(3, refeicao.getSobremesa());
            stmt.setInt(4, refeicao.getId());

            stmt.executeUpdate();
        }
    }
}