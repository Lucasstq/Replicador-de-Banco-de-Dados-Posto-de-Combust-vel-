package view.processo;

import database.model.controle.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;

public class ReplicacaoProcessoTabelaView extends JFrame {

    private JTextField txtId;
    private JComboBox<TB_REPLICACAO_PROCESSO_TABELA> cbProcesso;
    private JTextField txtTabelOrigem;
    private JTextField txtTabelDestino;
    private JTextField txtOrdem;
    private JCheckBox ckHabilitado;
    private JTextArea txtWhere;

    private JButton bntSalvar;
    private JButton bntBuscar;
    private JButton bntDeletar;
    private JButton bntAdicionar;

    public ReplicacaoProcessoTabelaView() {
        setTitle("Cadastro de Tabelas");
        setSize(720, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // Botoes
        bntSalvar = new JButton("Salvar");
        bntBuscar = new JButton("Buscar");
        bntDeletar = new JButton("Deletar");
        bntAdicionar = new JButton("Adicionar");

        // Posicao dos botoes
        bntBuscar.setBounds(10, 10, 130, 30);
        bntAdicionar.setBounds(150, 10, 130, 30);
        bntSalvar.setBounds(290, 10, 130, 30);
        bntDeletar.setBounds(430, 10, 130, 30);

        getContentPane().add(bntBuscar);
        getContentPane().add(bntAdicionar);
        getContentPane().add(bntSalvar);
        getContentPane().add(bntDeletar);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 120, 25);
        getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 70, 220, 25);
        getContentPane().add(txtId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 140, 25);
        getContentPane().add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(120, 105, 550, 25);
        getContentPane().add(cbProcesso);

        JLabel lblOrigem = new JLabel("TABELA ORIGEM");
        lblOrigem.setBounds(10, 140, 120, 25);
        getContentPane().add(lblOrigem);

        txtTabelOrigem = new JTextField();
        txtTabelOrigem.setBounds(120, 140, 550, 25);
        getContentPane().add(txtTabelOrigem);

        JLabel lblDestino = new JLabel("TABELA DESTINO");
        lblDestino.setBounds(10, 175, 120, 25);
        getContentPane().add(lblDestino);

        txtTabelDestino = new JTextField();
        txtTabelDestino.setBounds(120, 175, 550, 25);
        getContentPane().add(txtTabelDestino);

        JLabel lblOrdem = new JLabel("ORDEM");
        lblOrdem.setBounds(10, 210, 120, 25);
        getContentPane().add(lblOrdem);

        txtOrdem = new JTextField();
        txtOrdem.setBounds(120, 210, 220, 25);
        getContentPane().add(txtOrdem);

        ckHabilitado = new JCheckBox("HABILITADO");
        ckHabilitado.setBounds(10, 245, 120, 25);
        getContentPane().add(ckHabilitado);

        JLabel lblWhere = new JLabel("WHERE");
        lblWhere.setBounds(10, 280, 120, 25);
        getContentPane().add(lblWhere);

        txtWhere = new JTextArea();
        txtWhere.setBounds(120, 280, 550, 80);
        getContentPane().add(txtWhere);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReplicacaoProcessoTabelaView tabela = new ReplicacaoProcessoTabelaView();
            tabela.setVisible(true);
        });
    }
}
