package view.direcao;

import database.dao.ReplicacaoDirecaoDAO;
import database.model.controle.TB_REPLICACAO_DIRECAO;
import database.model.controle.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TabelaReplicacaoDirecaoView extends JFrame {
    /*
     * (JFrame) tem os campos para o usuário preencher
     * os dados de uma direção de replicação.
     */
    private JTextField txtId;
    private JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;
    private JTextField txtDirecaoOrigem;
    private JTextField txtDirecaoDestino;
    private JTextField txtUsuarioOrigem;
    private JTextField txtUsuarioDestino;
    private JTextField txtSenhaOrigem;
    private JTextField txtSenhaDestino;
    private JCheckBox ckHabilitado;

    private enum ModoTela {NENHUM, INSERT, UPDATE}

    ModoTela modoTela = ModoTela.NENHUM;

    private Connection conn;
    private ReplicacaoDirecaoDAO dao;

    /*
     * Os botoes da tela
     */
    private JButton bntSalvar;
    private JButton bntBuscar;
    private JButton bntDeletar;
    private JButton bntAdicionar;

    public TabelaReplicacaoDirecaoView(Connection connection) throws SQLException {
        this.conn = connection;
        if (this.conn != null) {
            this.dao = new ReplicacaoDirecaoDAO(conn);
        }

        setTitle("Tabela de Replicação de Direção");
        setSize(720, 430);
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
        txtId.setBounds(150, 70, 200, 25);
        getContentPane().add(txtId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 120, 25);
        getContentPane().add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(150, 105, 520, 25);
        getContentPane().add(cbProcesso);

        JLabel lblDirecaoOrigem = new JLabel("DIRECAO ORIGEM:");
        lblDirecaoOrigem.setBounds(10, 140, 130, 25);
        getContentPane().add(lblDirecaoOrigem);

        txtDirecaoOrigem = new JTextField();
        txtDirecaoOrigem.setBounds(150, 140, 520, 25);
        getContentPane().add(txtDirecaoOrigem);

        JLabel lblDirecaoDestino = new JLabel("DIRECAO DESTINO:");
        lblDirecaoDestino.setBounds(10, 175, 130, 25);
        getContentPane().add(lblDirecaoDestino);

        txtDirecaoDestino = new JTextField();
        txtDirecaoDestino.setBounds(150, 175, 520, 25);
        getContentPane().add(txtDirecaoDestino);

        JLabel lblUsuarioOrigem = new JLabel("USUARIO ORIGEM:");
        lblUsuarioOrigem.setBounds(10, 210, 130, 25);
        getContentPane().add(lblUsuarioOrigem);

        txtUsuarioOrigem = new JTextField();
        txtUsuarioOrigem.setBounds(150, 210, 220, 25);
        getContentPane().add(txtUsuarioOrigem);

        JLabel lblUsuarioDestino = new JLabel("USUARIO DESTINO:");
        lblUsuarioDestino.setBounds(380, 210, 130, 25);
        getContentPane().add(lblUsuarioDestino);

        txtUsuarioDestino = new JTextField();
        txtUsuarioDestino.setBounds(510, 210, 160, 25);
        getContentPane().add(txtUsuarioDestino);

        JLabel lblSenhaOrigem = new JLabel("SENHA ORIGEM:");
        lblSenhaOrigem.setBounds(10, 245, 130, 25);
        getContentPane().add(lblSenhaOrigem);

        txtSenhaOrigem = new JPasswordField();
        txtSenhaOrigem.setBounds(150, 245, 220, 25);
        getContentPane().add(txtSenhaOrigem);

        JLabel lblSenhaDestino = new JLabel("SENHA DESTINO:");
        lblSenhaDestino.setBounds(380, 245, 130, 25);
        getContentPane().add(lblSenhaDestino);

        txtSenhaDestino = new JPasswordField();
        txtSenhaDestino.setBounds(510, 245, 160, 25);
        getContentPane().add(txtSenhaDestino);

        ckHabilitado = new JCheckBox("HABILITADO");
        ckHabilitado.setBounds(10, 280, 120, 25);
        getContentPane().add(ckHabilitado);

        txtId.setEnabled(false);
        cbProcesso.setEnabled(false);
        txtDirecaoOrigem.setEnabled(false);
        txtDirecaoDestino.setEnabled(false);
        txtUsuarioOrigem.setEnabled(false);
        txtUsuarioDestino.setEnabled(false);
        txtSenhaOrigem.setEnabled(false);
        txtSenhaDestino.setEnabled(false);
        ckHabilitado.setEnabled(false);
        bntSalvar.setEnabled(false);

        bntAdicionar.addActionListener(e -> {
            modoTela = ModoTela.INSERT;

            txtId.setText("");

            if (cbProcesso.getItemCount() > 0) {
                cbProcesso.setSelectedIndex(0);
            }

            txtDirecaoOrigem.setText("");
            txtDirecaoDestino.setText("");
            txtUsuarioOrigem.setText("");
            txtUsuarioDestino.setText("");
            txtSenhaOrigem.setText("");
            txtSenhaDestino.setText("");

            ckHabilitado.setSelected(true);
            cbProcesso.setEnabled(true);
            txtDirecaoOrigem.setEnabled(true);
            txtDirecaoDestino.setEnabled(true);

            txtUsuarioOrigem.setEnabled(true);
            txtUsuarioDestino.setEnabled(true);

            txtSenhaOrigem.setEnabled(true);
            txtSenhaDestino.setEnabled(true);

            bntSalvar.setEnabled(true);
            bntDeletar.setEnabled(true);
        });

        bntSalvar.addActionListener(e -> {
            try {
                if (cbProcesso.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um processo.");
                    return;
                }

                if (txtDirecaoOrigem.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo DIRECAO ORIGEM é obrigatório.");
                    return;
                }

                if (txtDirecaoDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo DIRECAO DESTINO é obrigatório.");
                    return;
                }

                if (txtSenhaOrigem.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo SENHA ORIGEM é obrigatório.");
                    return;
                }

                if (txtSenhaDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo SENHA DESTINO é obrigatório.");
                    return;
                }

                if (txtUsuarioOrigem.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo USUARIO ORIGEM é obrigatório.");
                    return;
                }

                if (txtUsuarioDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo USUARIO DESTINO é obrigatório.");
                    return;
                }

                TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();
                TB_REPLICACAO_DIRECAO direcao = new TB_REPLICACAO_DIRECAO();
                direcao.setProcesso_id(pSel.getId());
                direcao.setDirecao_origem(txtDirecaoOrigem.getText().trim());
                direcao.setDirecao_destino(txtDirecaoDestino.getText().trim());
                direcao.setUsuario_origem(txtUsuarioOrigem.getText().trim());
                direcao.setUsuario_destino(txtUsuarioDestino.getText().trim());
                direcao.setSenha_origem(txtSenhaOrigem.getText().trim());
                direcao.setSenha_destino(txtSenhaDestino.getText().trim());
                direcao.setHabilitado(ckHabilitado.isSelected());

                if (modoTela == ModoTela.INSERT) {
                    dao.insert(direcao);
                    JOptionPane.showMessageDialog(this, "Direcao de replicacao cadastrada!");
                } else if (modoTela == ModoTela.UPDATE) {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "O campo ID é obrigatório para atualizar a direcao de replicacao.");
                        return;
                    }
                    direcao.setId(Long.parseLong(txtId.getText().trim()));
                    dao.update(direcao);
                    JOptionPane.showMessageDialog(this, "Direcao de replicacao atualizada!");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique no botao de ADICIONAR ou BUSCAR antes de SALVAR.");
                    return;
                }

                modoTela = ModoTela.NENHUM;
                txtId.setEnabled(false);
                cbProcesso.setEnabled(false);
                txtDirecaoOrigem.setEnabled(false);
                txtDirecaoDestino.setEnabled(false);
                txtUsuarioOrigem.setEnabled(false);
                txtUsuarioDestino.setEnabled(false);
                txtSenhaOrigem.setEnabled(false);
                txtSenhaDestino.setEnabled(false);
                ckHabilitado.setEnabled(false);
                bntSalvar.setEnabled(false);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        bntDeletar.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo ID é obrigatório para deletar a direcao de replicacao.");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o processo?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (op != JOptionPane.YES_OPTION) return;

                long id = Long.parseLong(txtId.getText());
                dao.delete(id);
                JOptionPane.showMessageDialog(this, "Processo excluído!");

                txtId.setText("");

                if (cbProcesso.getItemCount() > 0) {
                    cbProcesso.setSelectedIndex(0);
                }

                txtDirecaoOrigem.setText("");
                txtDirecaoDestino.setText("");
                txtUsuarioOrigem.setText("");
                txtUsuarioDestino.setText("");
                txtSenhaOrigem.setText("");
                txtSenhaDestino.setText("");

                ckHabilitado.setSelected(false);
                cbProcesso.setEnabled(false);
                txtDirecaoOrigem.setEnabled(false);
                txtDirecaoDestino.setEnabled(false);

                txtUsuarioOrigem.setEnabled(false);
                txtUsuarioDestino.setEnabled(false);

                txtSenhaOrigem.setEnabled(false);
                txtSenhaDestino.setEnabled(false);

                bntSalvar.setEnabled(false);
                bntDeletar.setEnabled(false);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        bntBuscar.addActionListener(e -> {
            try {
                if (dao == null) {
                    JOptionPane.showMessageDialog(this, "Conexao indisponivel para realizar a busca.");
                    return;
                }

                ConsultaReplicacaoDirecaoDialog dialog = new ConsultaReplicacaoDirecaoDialog(this, dao);
                dialog.setVisible(true);

                TB_REPLICACAO_DIRECAO direcao = dialog.getDirecaoSelecionada();
                if (direcao != null) {
                    modoTela = ModoTela.UPDATE;

                    txtId.setText(String.valueOf(direcao.getId()));
                    txtDirecaoOrigem.setText(direcao.getDirecao_origem());
                    txtDirecaoDestino.setText(direcao.getDirecao_destino());
                    txtUsuarioOrigem.setText(direcao.getUsuario_origem());
                    txtUsuarioDestino.setText(direcao.getUsuario_destino());
                    ckHabilitado.setSelected(direcao.isHabilitado());

                    long id = direcao.getProcesso_id();
                    for (int i = 0; i < cbProcesso.getItemCount(); i++) {
                        TB_REPLICACAO_PROCESSO item = cbProcesso.getItemAt(1);
                        if (item.getId() == id) {
                            cbProcesso.setSelectedIndex(i);
                            break;
                        }
                    }

                    cbProcesso.setEnabled(true);
                    ckHabilitado.setEnabled(true);
                    txtDirecaoOrigem.setEnabled(true);
                    txtDirecaoDestino.setEnabled(true);
                    txtUsuarioOrigem.setEnabled(true);
                    txtUsuarioDestino.setEnabled(true);
                    txtSenhaOrigem.setEnabled(true);
                    txtSenhaDestino.setEnabled(true);
                    bntSalvar.setEnabled(true);
                    bntDeletar.setEnabled(true);

                } else return;
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("Erro ao buscar direcao de replicacao: " + ex.getMessage());
            }
        });
    }
}

