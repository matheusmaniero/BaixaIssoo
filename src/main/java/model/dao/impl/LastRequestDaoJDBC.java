package model.dao.impl;

import db.DB;
import model.dao.LastRequestDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LastRequestDaoJDBC implements LastRequestDao {

    private Connection conn;

    public LastRequestDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getLastRequest() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM last_request_table");
            rs = st.executeQuery();

            if (rs.next()){
                return rs.getString("last_request");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return null;

    }

    @Override
    public void update(String lastRequest) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE last_request_table SET last_request=?");
            st.setString(1,lastRequest);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }
}
