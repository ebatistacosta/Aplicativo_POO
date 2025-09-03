package app.produtos.view;

import app.produtos.dao.UsuariosDAO;
import app.produtos.model.Usuario;
import app.produtos.view.ProdutoCadastroGUI;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProdutosUsuariosGUI {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnCriarConta;
    private JButton btnLogin;
    private JPanel panelMain;

    private JFrame parentFrame; // armazenar o frame recebido

    public ProdutosUsuariosGUI(JFrame frame) {
        this.parentFrame = frame;
        UsuariosDAO dao = new UsuariosDAO();

        btnCriarConta.addActionListener(e -> {
            String nome = txtUsuario.getText();
            String senha = String.valueOf(txtSenha.getPassword());

            if (nome.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                return;
            }

            try {
                dao.criarConta(new Usuario(nome, senha));
                JOptionPane.showMessageDialog(null, "Conta criada com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao criar conta: " + ex.getMessage());
            }
        });

        btnLogin.addActionListener(e -> {
            String nome = txtUsuario.getText();
            String senha = String.valueOf(txtSenha.getPassword());

            if (nome.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                return;
            }

            try {
                Usuario usuario = dao.autenticarUsuario(nome, senha);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");

                    // Fecha a janela de login
                    parentFrame.dispose();

                    // Se o id for 1, abre a tela de registro de refeição
                    if (usuario.getId() == 1) {
                        ProdutoCadastroGUI cadastro = new ProdutoCadastroGUI();
                        cadastro.setVisible(true);
                    } else {
                        PodutoVisualizacao produtoVisualizacao = new PodutoVisualizacao();
                        produtoVisualizacao.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fazer login: " + ex.getMessage());
            }
        });

    }

    public JPanel getPanel() {
        return panelMain;
    }
}
