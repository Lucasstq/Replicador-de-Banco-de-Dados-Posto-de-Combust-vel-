package database.dao;

import database.model.controle.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessoTabelaDAO {
    private final Connection connection;

    public static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE PROCESSO_ID = ? AND HABILITADO = TRUE";

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA";

    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ? AND ORDER BY ORDEM";

    public static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO_TABELA " +
                    "(PROCESSO_ID, TABELA_ORIGEM, TABELA_DESTINO, DS_WHERE) VALUES (?, ?, ?, ?)";

    public static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO_TABELA SET PROCESSO_ID = ?, TABELA_ORIGEM = ?, TABELA_DESTINO = ?, " +
                    "DS_WHERE = ? WHERE ID = ?";

    public static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ProcessoTabelaDAO(Connection conn) throws SQLException {
        this.connection = conn;
        this.pstSelectByProcessoHabilitado = conn.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = conn.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = conn.prepareStatement(SQL_INSERT);
        this.pstUpdate = conn.prepareStatement(SQL_UPDATE);
        this.pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    public List<TB_REPLICACAO_PROCESSO_TABELA> selectByProcessoHabilidado(long processoId) throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> tabelas = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, processoId);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()) {
            while (rs.next()) {
                tabelas.add(mapper(rs));
            }
        }
        return tabelas;
    }

    public List<TB_REPLICACAO_PROCESSO_TABELA> selectAll() throws SQLException {
        List<TB_REPLICACAO_PROCESSO_TABELA> tabelas = new ArrayList<>();
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            while (rs.next()) {
                tabelas.add(mapper(rs));
            }
        }
        return tabelas;
    }

    public TB_REPLICACAO_PROCESSO_TABELA selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? mapper(rs) : null;
        }
    }

    public void insert(TB_REPLICACAO_PROCESSO_TABELA processoTabela) throws SQLException {
        pstInsert.setLong(1, processoTabela.getProcesso_id());
        pstInsert.setString(2, processoTabela.getTabela_origem());
        pstInsert.setString(3, processoTabela.getTabela_destino());
        pstInsert.setString(4, processoTabela.getDs_where());
        pstInsert.executeUpdate();
    }

    public void update(TB_REPLICACAO_PROCESSO_TABELA processoTabela) throws SQLException {
        pstUpdate.setLong(1, processoTabela.getProcesso_id());
        pstUpdate.setString(2, processoTabela.getTabela_origem());
        pstUpdate.setString(3, processoTabela.getTabela_destino());
        pstUpdate.setString(4, processoTabela.getDs_where());
        pstUpdate.setLong(5, processoTabela.getId());
        pstUpdate.executeUpdate();
    }

    public void delete(long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_PROCESSO_TABELA mapper(ResultSet rs) throws SQLException {
        TB_REPLICACAO_PROCESSO_TABELA tabela = new TB_REPLICACAO_PROCESSO_TABELA();
        tabela.setId(rs.getLong("ID"));
        tabela.setProcesso_id(rs.getLong("PROCESSO_ID"));
        tabela.setTabela_origem(rs.getString("TABELA_ORIGEM"));
        tabela.setTabela_destino(rs.getString("TABELA_DESTINO"));
        tabela.setOrdem(rs.getInt("ORDEM"));
        tabela.setHabilitado(rs.getBoolean("HABILITADO"));
        tabela.setDs_where(rs.getString("DS_WHERE"));
        return tabela;
    }
}
