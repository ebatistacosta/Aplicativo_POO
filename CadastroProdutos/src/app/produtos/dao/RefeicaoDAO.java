package app.produtos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class RefeicaoDAO {

    public List<String> listarFuncionariosPorRefeicao(int idRefeicao) throws SQLException {
        String sql = "SELECT u.nome, ru.quer_comida, ru.quer_bebida, ru.quer_sobremesa " +
                "FROM usuarios_refeicoes ru " +
                "JOIN usuarios u ON ru.id_usuario = u.id " +
                "WHERE ru.id_refeicao = ?";

        List<String> funcionarios = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRefeicao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("nome");
                    boolean comida = rs.getBoolean("comida");
                    boolean bebida = rs.getBoolean("bebida");
                    boolean sobremesa = rs.getBoolean("sobremesa");

                    StringBuilder sb = new StringBuilder();
                    sb.append("Funcionário: ").append(nome).append(" - Vai consumir: ");

                    if (comida) sb.append("[Comida] ");
                    if (bebida) sb.append("[Bebida] ");
                    if (sobremesa) sb.append("[Sobremesa] ");

                    funcionarios.add(sb.toString());
                }
            }
        }

        return funcionarios;
    }

    private static final String URL = "jdbc:postgresql://localhost:5432/db_refeicoes";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }



}
