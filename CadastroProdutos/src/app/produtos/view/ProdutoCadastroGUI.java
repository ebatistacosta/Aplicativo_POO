package app.produtos.view;

import app.produtos.dao.ProdutoDAO;
import app.produtos.dao.RefeicaoDAO;
import app.produtos.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ProdutoCadastroGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField txtComida;
    private JTextField txtBebida;
    private JButton btnSalvar;
    private JButton btnAtualizarLista;
    private JTextArea txtAreaRefeicoes;
    private JLabel comida;
    private JLabel bebida;
    private JLabel lblStatusMessage;
    private JTextField txtSobremesa;
    private JLabel sobremesa;
    private JTable table1;
    private JButton btnFuncionario;
    private JButton btnVisualizarFuncionarios;

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    private DefaultTableModel tableModel;

    private int selectedRefeicaoId = -1;


    public ProdutoCadastroGUI() {
        setTitle("Cadastro de produtos");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //Centraliza a janela

        add(mainPanel);

        //JTable
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Comida");
        tableModel.addColumn("Bebida");
        tableModel.addColumn("Sobremesa");

        table1.setModel(tableModel);

        produtoDAO = new ProdutoDAO();

        btnSalvar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) { salvarProduto(); }
        });
        btnAtualizarLista.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) { carregarProdutoNaLista(); }
        });

        btnFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaVisualizacao();
            }
        });

        btnVisualizarFuncionarios.addActionListener(e -> {
            if (selectedRefeicaoId == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma refeição na tabela primeiro.");
                return;
            }

            try {
                RefeicaoDAO dao = new RefeicaoDAO();
                List<String> funcionarios = dao.listarFuncionariosPorRefeicao(selectedRefeicaoId);

                if (funcionarios.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum funcionário selecionou esta refeição.");
                } else {
                    JTextArea textArea = new JTextArea();
                    for (String f : funcionarios) textArea.append(f + "\n");
                    textArea.setEditable(false);

                    JOptionPane.showMessageDialog(
                            this,
                            new JScrollPane(textArea),
                            "Funcionários da Refeição ID: " + selectedRefeicaoId,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar funcionários: " + ex.getMessage());
                ex.printStackTrace();
            }
        });


        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = table1.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = table1.convertRowIndexToModel(viewRow);
                    // Atribua o valor ao atributo da classe, sem redeclarar
                    selectedRefeicaoId = (int) tableModel.getValueAt(modelRow, 0);
                }
            }
        });




    }

    private void salvarProduto() {
        String comida = txtComida.getText().trim();
        String bebida = txtBebida.getText().trim();
        String sobremesa = txtSobremesa.getText().trim();

        if (comida.isEmpty() || bebida.isEmpty() || sobremesa.isEmpty()) {
            lblStatusMessage.setText("Erro ao cadastrar produto: preencha todos os campos!");
            lblStatusMessage.setForeground(Color.RED);
            return; // Impede que continue com o cadastro
        }

        try {
            Produto produto = new Produto(comida, bebida, sobremesa);
            produtoDAO.inserirProduto(produto);
            lblStatusMessage.setText("Produto cadastrado com sucesso!");
            lblStatusMessage.setForeground(Color.GREEN);
            limparCampos();
            carregarProdutoNaLista();
        } catch (SQLException ex) {
            lblStatusMessage.setText("Erro no Banco de Dados: " + ex.getMessage());
            lblStatusMessage.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }


    private void carregarProdutoNaLista() {
        try {
            List<Produto> produtos = produtoDAO.listarProdutos();
            txtAreaRefeicoes.setText("");
            tableModel.setRowCount(0);

            if (produtos.isEmpty()){
                txtAreaRefeicoes.setText("Nenhum produto encontrado");
            }else {
                for (Produto p : produtos){


                    tableModel.addRow(new Object[]{
                            p.getId(),
                            p.getComida(),
                            p.getBebida(),
                            p.getSobremesa()
                    });
                }
            }
            lblStatusMessage.setText("Lista de refeições Atualizada. ");
            lblStatusMessage.setForeground(Color.black);
        } catch (SQLException ex ){
            lblStatusMessage.setText("Erro ao carregar refeições" + ex.getMessage());
            lblStatusMessage.setForeground(Color.red);
            ex.printStackTrace();
        }
    }

    private void limparCampos() {
        txtComida.setText("");
        txtBebida.setText("");
        txtSobremesa.setText("");
    }

    private void createUIComponents() {

    }

    private void abrirTelaVisualizacao() {
        PodutoVisualizacao telaVisualizacao = new PodutoVisualizacao();

        try {
            List<Produto> produtos = produtoDAO.listarProdutos();
            telaVisualizacao.preencherTabela(produtos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        telaVisualizacao.setVisible(true);
    }





}