package app.produtos.dao;

import app.produtos.model.Usuario;
import java.sql.*;


public class UsuariosDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_refeicoes";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void criarConta(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.executeUpdate();
        }
    }

    public boolean autenticar(String nome, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            return stmt.executeQuery().next(); // retorna true se encontrou usuário
        }
    }

    public Usuario autenticarUsuario(String nome, String senha) throws SQLException {
        String sql = "SELECT id, nome, senha FROM usuarios WHERE nome = ? AND senha = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nomeUsuario = rs.getString("nome");
                String senhaUsuario = rs.getString("senha");
                return new Usuario(id, nomeUsuario, senhaUsuario);
            } else {
                return null;
            }
        }
    }


}
