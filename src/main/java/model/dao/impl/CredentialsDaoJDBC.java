package model.dao.impl;

import db.DB;
import model.dao.CredentialsDao;
import model.entities.Credentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialsDaoJDBC implements CredentialsDao {

    private static Logger logger = LogManager.getLogger(Credentials.class);

    private Connection conn;

    public CredentialsDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void update(Credentials credentials) {

        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE credentials "
                    +"SET access_token = ?, refresh_token = ?, created_at = ? "+ "WHERE id = ? ");
            st.setString(1,credentials.getAccessToken());
            st.setString(2,credentials.getRefreshToken());
            st.setLong(3,credentials.getCreatedAt());
            st.setInt(4, 1);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Credentials instantiateCredentials() {

        Credentials credentials = new Credentials();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM credentials");
            rs = st.executeQuery();
            if (rs.next()){
                credentials.setAccessToken(rs.getString("access_token"));
                credentials.setRefreshToken(rs.getString("refresh_token"));
                credentials.setCreatedAt(rs.getLong("created_at"));
                return credentials;
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);

        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return null;


    }


}
