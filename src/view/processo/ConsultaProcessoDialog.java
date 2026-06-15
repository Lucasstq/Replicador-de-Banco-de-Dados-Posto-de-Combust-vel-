package view.processo;

import database.dao.ReplicacaoProcessoDAO;
import database.model.controle.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ConsultaProcessoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnDeletar;

    private TB_REPLICACAO_PROCESSO processoSelecionado;

    public ConsultaProcessoDialog(JFrame parent, ReplicacaoProcessoDAO dao) throws SQLException {
        super(parent, "Consulta de Processos.");
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO");
        model.addColumn("DESCRICAO");
        model.addColumn("HABILITADO");

        List<TB_REPLICACAO_PROCESSO> processos = dao.selectAll();

        for (TB_REPLICACAO_PROCESSO processo : processos) {
            model.addRow(new Object[]{
                    processo.getId(),
                    processo.getProcesso(),
                    processo.getDescricao(),
                    processo.isHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 680, 300);
        add(scrollPane);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10, 320, 140, 30);
        add(btnSelecionar);

        btnDeletar = new JButton("DELETAR");
        btnDeletar.setBounds(170, 320, 140, 30);
        add(btnDeletar);

        btnDeletar.addActionListener(e ->{
            processoSelecionado = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um processo para continuar.");
                return;
            }

            TB_REPLICACAO_PROCESSO p = new TB_REPLICACAO_PROCESSO();
            p.setId(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
            p.setProcesso(table.getValueAt(selectedRow, 1).toString());
            p.setDescricao(table.getValueAt(selectedRow, 2).toString());
            p.setHabilitado(Boolean.parseBoolean(table.getValueAt(selectedRow, 3).toString()));
            processoSelecionado = p;
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

    public TB_REPLICACAO_PROCESSO getProcessoSelecionado() {
        return processoSelecionado;
    }
}
