package app.produtos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRefeicaoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/db_refeicoes";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Inserir ou atualizar consumo
    public void registrarConsumo(int idUsuario, int idRefeicao, boolean comida, boolean bebida, boolean sobremesa) throws SQLException {
        String sql = "INSERT INTO usuarios_refeicoes (id_usuario, id_refeicao, quer_comida, quer_bebida, quer_sobremesa) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id_usuario, id_refeicao) DO UPDATE SET " +
                "quer_comida = EXCLUDED.quer_comida, " +
                "quer_bebida = EXCLUDED.quer_bebida, " +
                "quer_sobremesa = EXCLUDED.quer_sobremesa";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRefeicao);
            stmt.setBoolean(3, comida);
            stmt.setBoolean(4, bebida);
            stmt.setBoolean(5, sobremesa);

            stmt.executeUpdate();
        }
    }

    // Remover consumo
    public void removerConsumo(int idUsuario, int idRefeicao) throws SQLException {
        String sql = "DELETE FROM usuarios_refeicoes WHERE id_usuario = ? AND id_refeicao = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRefeicao);
            stmt.executeUpdate();
        }
    }

    // Buscar seleções de um usuário
    public List<String> listarConsumoPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT r.id, r.comida, r.bebida, r.sobremesa, ur.quer_comida, ur.quer_bebida, ur.quer_sobremesa " +
                "FROM usuarios_refeicoes ur " +
                "JOIN refeicoes r ON r.id = ur.id_refeicao " +
                "WHERE ur.id_usuario = ?";

        List<String> escolhas = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRef = rs.getInt("id");
                String comida = rs.getBoolean("quer_comida") ? rs.getString("comida") : "-";
                String bebida = rs.getBoolean("quer_bebida") ? rs.getString("bebida") : "-";
                String sobremesa = rs.getBoolean("quer_sobremesa") ? rs.getString("sobremesa") : "-";

                String linha = "Refeição ID: " + idRef + " | Comida: " + comida + " | Bebida: " + bebida + " | Sobremesa: " + sobremesa;
                escolhas.add(linha);
            }
        }

        return escolhas;
    }

    // Verificar se um usuário já selecionou uma refeição
    public boolean jaSelecionou(int idUsuario, int idRefeicao) throws SQLException {
        String sql = "SELECT 1 FROM usuarios_refeicoes WHERE id_usuario = ? AND id_refeicao = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idRefeicao);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }
    }



}
