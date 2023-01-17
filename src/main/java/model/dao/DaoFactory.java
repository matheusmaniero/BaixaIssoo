package model.dao;

import db.DB;
import model.dao.impl.CredentialsDaoJDBC;
import model.dao.impl.UserDaoJDBC;

public class DaoFactory {

    public static CredentialsDao createCredentialsDao(){
        return new CredentialsDaoJDBC(DB.getConnection());

    }

    public static UserDao createUserDao(){
        return new UserDaoJDBC(DB.getConnection());
    }
}
