package model.dao;

import model.entities.User;
import model.entities.Video;

public interface UserDao {

    public void insertNewUser(User user);

    public User userExists(Long twitterUserId);

    public void insertVideo(Video video);

    public User findById(Long twitterId);

    public Video getLastVideo();



}
