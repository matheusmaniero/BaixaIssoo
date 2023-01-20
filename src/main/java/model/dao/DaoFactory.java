package model.dao;

import db.DB;
import model.dao.impl.CredentialsDaoJDBC;
import model.dao.impl.DBInsertionControlJDBC;
import model.dao.impl.LastRequestDaoJDBC;
import model.dao.impl.UserDaoJDBC;

public class DaoFactory {

    public static CredentialsDao createCredentialsDao(){
        return new CredentialsDaoJDBC(DB.getConnection());

    }

    public static UserDao createUserDao(){
        return new UserDaoJDBC(DB.getConnection());
    }

    public static DBInsertionControlDao createDbControlDao(){
        return new DBInsertionControlJDBC(DB.getConnection());
    }

    public static LastRequestDao createLastRequestDao(){
        return new LastRequestDaoJDBC(DB.getConnection());
    }


}
