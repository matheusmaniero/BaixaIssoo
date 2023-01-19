package model.dao.impl;

import db.DB;
import model.dao.UserDao;
import model.entities.User;
import model.entities.Video;

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
    public void insertNewUser(User obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO users "+
                    "(twitter_user_id, twitter_screen_name) "+
                    "VALUES "+"(?, ?)");

            st.setLong(1,obj.getTwitterUserId());
            st.setString(2,obj.getScreenName());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public User userExists(Long twitterUserId) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            st = conn.prepareStatement("SELECT twitter_user_id, twitter_screen_name "+
                    "FROM users "+"WHERE users.twitter_user_id = ?");

            st.setLong(1,twitterUserId);
            rs = st.executeQuery();

            if (rs.next()){
                User obj = new User();
                obj.setTwitterUserId(rs.getLong("twitter_user_id"));
                obj.setScreenName(rs.getString("twitter_screen_name"));
                return obj;

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;


    }

    @Override
    public void insertVideo(Video video) {

            PreparedStatement st = null;

            try {
                st = conn.prepareStatement("INSERT INTO videos "+"(video_link, twitter_user_id, created_at) "+
                        "VALUES "+"(?, ?, ?)");
                st.setString(1,video.getVideoUrl());
                st.setLong(2,video.getOwnerId());
                st.setLong(3,video.getCreatedAt());

                st.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DB.closeStatement(st);
            }

    }


    @Override
    public User findById(Long twitterId) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT videos.video_link, videos.created_at, users.twitter_user_id, users.twitter_screen_name FROM videos INNER JOIN users ON videos.twitter_user_id=users.twitter_user_id WHERE users.twitter_user_id = ?");
            st.setLong(1, twitterId);
            rs = st.executeQuery();
            User obj = new User();

           if (rs.next()){
               obj.setScreenName(rs.getString("twitter_screen_name"));
               obj.setTwitterUserId(rs.getLong("twitter_user_id"));
               obj.getVideos().add(new Video(rs.getString("video_link"),rs.getLong("created_at"),rs.getLong("twitter_user_id")));
               while (rs.next()){
                   obj.getVideos().add(new Video(rs.getString("video_link"),rs.getLong("created_at"),rs.getLong("twitter_user_id")));
               }
               return obj;

           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return null;
    }

    @Override
    public Video getLastVideo() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT twitter_user_id, video_link, created_at FROM videos ORDER BY created_at DESC LIMIT 1");
            rs = st.executeQuery();
            if (rs.next()){
                Video vid = new Video(rs.getString("video_link"),rs.getLong("created_at"),rs.getLong("twitter_user_id"));
                return vid;
            }
            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }


    }
}
