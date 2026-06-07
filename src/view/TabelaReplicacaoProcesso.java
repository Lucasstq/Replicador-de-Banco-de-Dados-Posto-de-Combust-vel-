package view;

import javax.swing.*;

public class TabelaReplicacaoProcesso extends JFrame {
    /*
        * (JFrame) tem 3 campos de texto e um checkbox,
        * para o usuário preencher os dados de um processo de replicação.
    */
    private JTextField txtId;
    private JTextField txtProcesso;
    private JTextField txtDescricao;
    private JCheckBox ckHabilitado;

    /*
        * Os botoes da tela
     */
    private JButton bntSalvar;
    private JButton bntBuscar;
    private JButton bntDeletar;
    private JButton bntAdicionar;

    public TabelaReplicacaoProcesso(){
        setTitle("Tabela de Replicação de Processo");
        setSize(620, 300);
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

        // Nomes dos campos
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 120, 25);
        getContentPane().add(lblId);

        // Campos para preencher os dados
        txtId = new JTextField();
        txtId.setBounds(120, 70, 200, 25);
        getContentPane().add(txtId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 120, 25);
        getContentPane().add(lblProcesso);

        txtProcesso = new JTextField();
        txtProcesso.setBounds(120, 105, 420, 25);
        getContentPane().add(txtProcesso);

        JLabel lblDescricao = new JLabel("DESCRICAO:");
        lblDescricao.setBounds(10, 135, 120, 25);
        getContentPane().add(lblDescricao);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(120, 135, 420, 25);
        getContentPane().add(txtDescricao);

        ckHabilitado = new JCheckBox("HABILITADO");
        ckHabilitado.setBounds(10, 170, 120, 25);
        getContentPane().add(ckHabilitado);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TabelaReplicacaoProcesso tabela = new TabelaReplicacaoProcesso();
            tabela.setVisible(true);
        });
    }

}
