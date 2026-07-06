package view.direcao;

import database.dao.ReplicacaoDirecaoDAO;
import database.model.controle.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ConsultaReplicacaoDirecaoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnDeletar;

    private TB_REPLICACAO_DIRECAO direcaoSelecionada;

    public ConsultaReplicacaoDirecaoDialog(JFrame parent, ReplicacaoDirecaoDAO dao) throws SQLException {
        super(parent, "Consulta de Replicacao de Direcao.");
        setSize(900, 420);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO ID");
        model.addColumn("DIRECAO ORIGEM");
        model.addColumn("DIRECAO DESTINO");
        model.addColumn("HABILITADO");

        List<TB_REPLICACAO_DIRECAO> direcoes = dao.selectAll();

        for (TB_REPLICACAO_DIRECAO direcao : direcoes) {
            model.addRow(new Object[]{
                    direcao.getId(),
                    direcao.getProcesso_id(),
                    direcao.getDirecao_origem(),
                    direcao.getDirecao_destino(),
                    direcao.isHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 875, 300);
        add(scrollPane);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10, 320, 140, 30);
        add(btnSelecionar);

        btnDeletar = new JButton("DELETAR");
        btnDeletar.setBounds(170, 320, 140, 30);
        add(btnDeletar);

        btnDeletar.addActionListener(e -> {
            direcaoSelecionada = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma linha.");
                return;
            }

            long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
            TB_REPLICACAO_DIRECAO direcao = null;
            try {
                direcao = dao.selectById(id);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar a direção: " + ex.getMessage());
                return;
            }
            direcaoSelecionada = direcao;
            dispose();
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnSelecionar.doClick();
                }
            }
        });
    }

    public TB_REPLICACAO_DIRECAO getDirecaoSelecionada() {
        return direcaoSelecionada;
    }
}

