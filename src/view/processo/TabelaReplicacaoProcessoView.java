package view.processo;

import database.dao.ReplicacaoProcessoDAO;
import database.model.controle.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TabelaReplicacaoProcessoView extends JFrame {
    /*
     * (JFrame) tem 3 campos de texto e um checkbox,
     * para o usuário preencher os dados de um processo de replicação.
     */
    private JTextField txtId;
    private JTextField txtProcesso;
    private JTextField txtDescricao;
    private JCheckBox ckHabilitado;

    private enum ModoTela {NENHUM, INSERT, UPDATE}

    ModoTela modoTela = ModoTela.NENHUM;

    private Connection conn;
    private ReplicacaoProcessoDAO dao;

    /*
     * Os botoes da tela
     */
    private JButton bntSalvar;
    private JButton bntBuscar;
    private JButton bntDeletar;
    private JButton bntAdicionar;

    public TabelaReplicacaoProcessoView(Connection connection) throws SQLException {

        this.conn = connection;
        this.dao = new ReplicacaoProcessoDAO(conn);

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

        // Os campos comecam indisponiveis para escrita dos dados
        txtId.setEnabled(false);
        txtProcesso.setEnabled(false);
        txtDescricao.setEnabled(false);
        ckHabilitado.setEnabled(false);
        bntSalvar.setEnabled(false);
        bntDeletar.setEnabled(false);

        bntAdicionar.addActionListener(e -> {
            modoTela = ModoTela.INSERT;

            // Os campos comecam vazios para a escrita dos dados
            txtId.setText("");
            txtProcesso.setText("");
            txtDescricao.setText("");
            // Habilita o campo para escrita dos dados
            txtProcesso.setEnabled(true);
            txtDescricao.setEnabled(true);
            ckHabilitado.setEnabled(true);
        });

        bntSalvar.addActionListener(e -> {
            try {
                if (txtProcesso.getText().trim() == null) {
                    JOptionPane.showMessageDialog(this, "O campo PROCESSO é obrigatório.");
                    return;
                }

                TB_REPLICACAO_PROCESSO processo = new TB_REPLICACAO_PROCESSO();
                processo.setProcesso(txtProcesso.getText().trim());
                processo.setDescricao(txtDescricao.getText().trim());
                processo.setHabilitado(ckHabilitado.isSelected());

                if (modoTela == ModoTela.INSERT) {
                    dao.insert(processo);
                    JOptionPane.showMessageDialog(this, "Processo cadastrado!");
                } else if (modoTela == ModoTela.UPDATE) {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "O campo ID é obrigatório para atualização.");
                        return;
                    }
                    processo.setId(Long.parseLong(txtId.getText()));
                    dao.update(processo);
                    JOptionPane.showMessageDialog(this, "Processo atualizado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique no botao de ADICIONAR ou BUSCAR antes de SALVAR.");
                    return;
                }
                modoTela = ModoTela.NENHUM;
                txtId.setText("");
                txtProcesso.setText("");
                txtDescricao.setText("");
                ckHabilitado.setSelected(false);
                bntSalvar.setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("Erro ao cadastrar processo de replicação: " + ex.getMessage());
            }
        });

        bntBuscar.addActionListener(e ->{
            try {
                ConsultaProcessoDialog dialog = new ConsultaProcessoDialog(this, dao);
                dialog.setVisible(true);

                TB_REPLICACAO_PROCESSO processoSelecionado = dialog.getProcessoSelecionado();
                if (processoSelecionado != null) {
                    modoTela = ModoTela.UPDATE;
                    txtId.setText(String.valueOf(processoSelecionado.getId()));
                    txtProcesso.setText(processoSelecionado.getProcesso());
                    txtDescricao.setText(processoSelecionado.getDescricao());
                    ckHabilitado.setSelected(processoSelecionado.isHabilitado());

                    // Habilita os campos para edição
                    txtProcesso.setEnabled(true);
                    txtDescricao.setEnabled(true);
                    ckHabilitado.setEnabled(true);
                    bntSalvar.setEnabled(true);
                    bntDeletar.setEnabled(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("Erro ao buscar: " + ex.getMessage());
            }
        });

        bntDeletar.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo ID é obrigatório para exclusão.");
                    return;
                }
                long id = Long.parseLong(txtId.getText());
                dao.delete(id);
                JOptionPane.showMessageDialog(this, "Processo excluído!");
                modoTela = ModoTela.NENHUM;
                txtId.setText("");
                txtProcesso.setText("");
                txtDescricao.setText("");
                ckHabilitado.setSelected(false);
                bntSalvar.setEnabled(false);
                bntDeletar.setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("Erro ao excluir: " + ex.getMessage());
            }
        });
    }
}
