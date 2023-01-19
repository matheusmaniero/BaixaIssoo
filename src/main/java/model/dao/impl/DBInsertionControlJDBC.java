package model.dao.impl;

import db.DB;
import model.dao.DBInsertionControlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBInsertionControlJDBC implements DBInsertionControlDao {

    private Connection conn;

    public DBInsertionControlJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Long timeStamp) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO date_control "+"(id, last_insertion_into_database_timestamp) "+
                    "VALUES "+"(?, ?)");
            st.setInt(1,1);
            st.setLong(2,timeStamp);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Long getLastInsertionTimeStamp() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT last_insertion_into_database_timestamp FROM date_control WHERE id = ?");
            st.setInt(1,1);
            rs = st.executeQuery();

            if (rs.next()){
                return rs.getLong("last_insertion_into_database_timestamp");
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
    public void UpdateTimeStamp(Long timeStamp) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE date_control SET last_insertion_into_database_timestamp = ? WHERE id = ?");
            st.setLong(1,timeStamp);
            st.setInt(2,1);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }


    }
}
