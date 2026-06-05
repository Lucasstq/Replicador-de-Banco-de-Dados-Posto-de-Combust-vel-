package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrigemDAO {
    private final Connection conn;
    private PreparedStatement pstSelectComandoOrigem;

    public OrigemDAO(Connection conn) {
        this.conn = conn;
    }

    /*
        * Método para selecionar dados da tabela de origem com base em uma condição WHERE.
        * Select do lado da origem, onde o processo irá ler os dados para replicação.
     */
    public ResultSet selectComandoOrigem(String tabela, String where) throws SQLException {
        pstSelectComandoOrigem = conn.prepareStatement("SELECT * FROM " + tabela + " WHERE " + where);
        return pstSelectComandoOrigem.executeQuery();
    }
}
