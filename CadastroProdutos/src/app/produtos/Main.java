package app.produtos;

import app.produtos.view.ProdutosUsuariosGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            ProdutosUsuariosGUI loginPanel = new ProdutosUsuariosGUI(frame);
            frame.setContentPane(loginPanel.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}