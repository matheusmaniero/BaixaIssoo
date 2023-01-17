package model.dao;

import db.DB;
import model.dao.impl.CredentialsDaoJDBC;

public class DaoFactory {

    public static CredentialsDao createCredentialsDao(){
        return new CredentialsDaoJDBC(DB.getConnection());

    }
}
