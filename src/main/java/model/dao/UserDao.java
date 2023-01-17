package model.dao;

import model.entities.User;

public interface UserDao {

    public void insert(User user);

    public User findById(String twitterUserId);



}
