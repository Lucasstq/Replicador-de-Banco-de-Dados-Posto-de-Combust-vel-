package database.dao;

import database.model.controle.TB_REPLICACAO_PROCESSO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplicacaoProcessoDAO {

    private final Connection connection;

    private static final String SQL_SELECT_ALL = "SELECT * FROM TB_REPLICACAO_PROCESSO";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM TB_REPLICACAO_PROCESSO WHERE ID = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO (PROCESSO, DESCRICAO, HABILITADO)"
                    + " VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO SET PROCESSO = ?, DESCRICAO = ?, HABILITADO = ? WHERE ID = ?";

    private static final String SQL_DELETE = "DELETE FROM TB_REPLICACAO_PROCESSO WHERE ID = ?";

    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ReplicacaoProcessoDAO(Connection connection) throws Exception {
        this.connection = connection;
        this.pstSelectAll = connection.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = connection.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = connection.prepareStatement(SQL_INSERT);
        this.pstUpdate = connection.prepareStatement(SQL_UPDATE);
        this.pstDelete = connection.prepareStatement(SQL_DELETE);
    }

    public List<TB_REPLICACAO_PROCESSO> selectAll() throws SQLException {
        List<TB_REPLICACAO_PROCESSO> processos = new ArrayList<>();
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            while (rs.next()) {
                processos.add(mapper(rs));
            }
        }
        return processos;
    }

    public TB_REPLICACAO_PROCESSO selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? mapper(rs) : null;
        }
    }

    public void insert(TB_REPLICACAO_PROCESSO processo) throws SQLException {
        pstInsert.setString(1, processo.getProcesso());
        pstInsert.setString(2, processo.getDescricao());
        pstInsert.setBoolean(3, processo.isHabilitado());
        pstInsert.executeUpdate();
    }

    public void update(TB_REPLICACAO_PROCESSO processo) throws SQLException {
        pstUpdate.setString(1, processo.getProcesso());
        pstUpdate.setString(2, processo.getDescricao());
        pstUpdate.setBoolean(3, processo.isHabilitado());
        pstUpdate.setLong(4, processo.getId());
        pstUpdate.executeUpdate();

    }

    public void delete(long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_PROCESSO mapper(ResultSet rs) throws SQLException {
        TB_REPLICACAO_PROCESSO processo = new TB_REPLICACAO_PROCESSO();
        processo.setId(rs.getLong("ID"));
        processo.setProcesso(rs.getString("PROCESSO"));
        processo.setDescricao(rs.getString("DESCRICAO"));
        processo.setHabilitado(rs.getBoolean("HABILITADO"));
        return processo;
    }
}
