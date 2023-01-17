package model.dao.impl;

import db.DB;
import model.dao.UserDao;
import model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJDBC implements UserDao {

    private Connection conn;

    public UserDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(User obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO users "+
                    "(twitter_user_id, twitter_screen_name) "+
                    "VALUES "+"(?, ?)");

            st.setString(1,obj.getTwitterUserId());
            st.setString(2,obj.getScreenName());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public User findById(String twitterUserId) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            st = conn.prepareStatement("SELECT twitter_user_id, twitter_screen_name "+
                    "FROM users "+"WHERE users.twitter_user_id = ?");

            st.setString(1,twitterUserId);
            rs = st.executeQuery();

            if (rs.next()){
                User obj = new User();
                obj.setTwitterUserId(rs.getString("twitter_user_id"));
                obj.setScreenName(rs.getString("twitter_screen_name"));
                return obj;

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;


    }
}
