package database.dao;

import database.model.controle.TB_REPLICACAO_DIRECAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReplicacaoDirecao {
    private final Connection conn;

    public static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE PROCESSO_ID = ? AND HABILITADO = TRUE";

    public static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_DIRECAO";

    public static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE ID = ?";

    public static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_DIRECAO " +
                    "(DIRECAO_ORIGEM, DIRECAO_DESTINO, USUARIO_ORIGEM, USUARIO_DESTINO, SENHA_ORIGEM, " +
                    "SENHA_DESTINO, HABILITADO, PROCESSO_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_UPDATE = "UPDATE TB_REPLICACAO_DIRECAO SET " +
            "DIRECAO_ORIGEM = ?, DIRECAO_DESTINO = ?, USUARIO_ORIGEM = ?, USUARIO_DESTINO = ?, " +
            "SENHA_ORIGEM = ?, SENHA_DESTINO = ?, HABILITADO = ?, PROCESSO_ID = ? WHERE ID = ?";

    public static final String SQL_DELETE = "DELETE FROM TB_REPLICACAO_DIRECAO WHERE ID = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ReplicacaoDirecao(Connection conn) throws SQLException {
        this.conn = conn;
        this.pstSelectByProcessoHabilitado = conn.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = conn.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = conn.prepareStatement(SQL_INSERT);
        this.pstUpdate = conn.prepareStatement(SQL_UPDATE);
        this.pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    public List<TB_REPLICACAO_DIRECAO> selectByProcessoHabilitado(long processoId) throws SQLException {
        pstSelectByProcessoHabilitado.setLong(1, processoId);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()) {
            List<TB_REPLICACAO_DIRECAO> direcoes = new ArrayList<>();
            while (rs.next()) {
                direcoes.add(mapper(rs));
            }
            return direcoes;
        }
    }

    public TB_REPLICACAO_DIRECAO selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? mapper(rs) : null;
        }
    }

    public List<TB_REPLICACAO_DIRECAO> selectAll() throws SQLException {
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            List<TB_REPLICACAO_DIRECAO> direcoes = new ArrayList<>();
            while (rs.next()) {
                direcoes.add(mapper(rs));
            }
            return direcoes;
        }
    }

    public void insert(TB_REPLICACAO_DIRECAO direcao) throws SQLException{
        pstInsert.setString(1, direcao.getDirecao_origem());
        pstInsert.setString(2, direcao.getDirecao_destino());
        pstInsert.setString(3, direcao.getUsuario_origem());
        pstInsert.setString(4, direcao.getUsuario_destino());
        pstInsert.setString(5, direcao.getSenha_origem());
        pstInsert.setString(6, direcao.getSenha_destino());
        pstInsert.setBoolean(7, direcao.isHabilitado());
        pstInsert.setLong(8, direcao.getProcesso_id());
        pstInsert.executeUpdate();
    }

    public void update(TB_REPLICACAO_DIRECAO direcao) throws SQLException {
        pstUpdate.setString(1, direcao.getDirecao_origem());
        pstUpdate.setString(2, direcao.getDirecao_destino());
        pstUpdate.setString(3, direcao.getUsuario_origem());
        pstUpdate.setString(4, direcao.getUsuario_destino());
        pstUpdate.setString(5, direcao.getSenha_origem());
        pstUpdate.setString(6, direcao.getSenha_destino());
        pstUpdate.setBoolean(7, direcao.isHabilitado());
        pstUpdate.setLong(8, direcao.getProcesso_id());
        pstUpdate.setLong(9, direcao.getId());
        pstUpdate.executeUpdate();
    }

    public void delete(long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_DIRECAO mapper(ResultSet rs) throws SQLException {
        TB_REPLICACAO_DIRECAO direcao = new TB_REPLICACAO_DIRECAO();
        direcao.setDirecao_origem(rs.getString("DIRECAO_ORIGEM"));
        direcao.setDirecao_destino(rs.getString("DIRECAO_DESTINO"));
        direcao.setUsuario_origem(rs.getString("USUARIO_ORIGEM"));
        direcao.setUsuario_destino(rs.getString("USUARIO_DESTINO"));
        direcao.setSenha_origem(rs.getString("SENHA_ORIGEM"));
        direcao.setSenha_destino(rs.getString("SENHA_DESTINO"));
        direcao.setHabilitado(rs.getBoolean("HABILITADO"));
        direcao.setProcesso_id(rs.getLong("PROCESSO_ID"));
        return direcao;
    }
}
