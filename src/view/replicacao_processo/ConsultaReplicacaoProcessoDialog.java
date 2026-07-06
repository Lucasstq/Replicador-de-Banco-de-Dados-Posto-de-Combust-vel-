package view.replicacao_processo;

import database.dao.ProcessoTabelaDAO;
import database.model.controle.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ConsultaReplicacaoProcessoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnDeletar;

    private TB_REPLICACAO_PROCESSO_TABELA tabelaSelecionada;

    public ConsultaReplicacaoProcessoDialog(JFrame parent, ProcessoTabelaDAO dao) throws SQLException {
        super(parent, "Consulta de Replicação de Processo.");
        setSize(1000, 420);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO ID");
        model.addColumn("TABELA ORIGEM");
        model.addColumn("TABELA DESTINO");
        model.addColumn("ORDEM");
        model.addColumn("HABILITADO");

        List<TB_REPLICACAO_PROCESSO_TABELA> tabelas = dao.selectAll();

        for (TB_REPLICACAO_PROCESSO_TABELA tabela : tabelas) {
            model.addRow(new Object[]{
                    tabela.getId(),
                    tabela.getProcesso_id(),
                    tabela.getTabela_origem(),
                    tabela.getTabela_destino(),
                    tabela.getOrdem(),
                    tabela.isHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 680, 300);
        add(scrollPane);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10, 320, 140, 30);
        add(btnSelecionar);

        btnDeletar = new JButton("DELETAR");
        btnDeletar.setBounds(170, 320, 140, 30);
        add(btnDeletar);

        btnDeletar.addActionListener(e -> {
            tabelaSelecionada = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para continuar.");
                return;
            }

            long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
            TB_REPLICACAO_PROCESSO_TABELA t = null;
            try {
                t = dao.selectById(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            tabelaSelecionada = t;
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

    public TB_REPLICACAO_PROCESSO_TABELA getTabelaSelecionada() {
        return tabelaSelecionada;
    }
}
