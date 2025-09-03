package app.produtos.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import app.produtos.dao.ProdutoDAO;
import app.produtos.model.Produto;

public class PodutoVisualizacao extends JFrame {
    private JTable tblTabela;
    private JPanel panel1;
    private JButton btnConfirmar;

    private JRadioButton rbComida;
    private JRadioButton rbBebida;
    private JRadioButton rbSobremesa;
    private JButton btnAtualizarLista;
    private JLabel lblStatusMessage;

    private ProdutoDAO produtoDAO = new ProdutoDAO(); // instância do DAO

    public PodutoVisualizacao() {
        setTitle("Visualização de Refeições");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);

        // Modelo da tabela
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Comida");
        model.addColumn("Bebida");
        model.addColumn("Sobremesa");

        tblTabela.setModel(model);

        // Listener da seleção da tabela
        tblTabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarTextoDosRadios();
            }
        });

        // Botão "Atualizar Lista"
        btnAtualizarLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarProdutos();
            }
        });
    }

    private void carregarProdutos() {
        try {
            List<Produto> produtos = produtoDAO.listarProdutos(); // método que busca os produtos do banco
            preencherTabela(produtos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void preencherTabela(List<Produto> produtos) {
        DefaultTableModel model = (DefaultTableModel) tblTabela.getModel();
        model.setRowCount(0); // limpa tabela

        for (Produto p : produtos) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getComida(),
                    p.getBebida(),
                    p.getSobremesa()
            });
        }
    }

    private void atualizarTextoDosRadios() {
        int linhaSelecionada = tblTabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            DefaultTableModel model = (DefaultTableModel) tblTabela.getModel();

            String comida = (String) model.getValueAt(linhaSelecionada, 1);
            String bebida = (String) model.getValueAt(linhaSelecionada, 2);
            String sobremesa = (String) model.getValueAt(linhaSelecionada, 3);

            // Atualiza os textos dos radio buttons
            rbComida.setText(comida);
            rbBebida.setText(bebida);
            rbSobremesa.setText(sobremesa);
        }
    }
}
