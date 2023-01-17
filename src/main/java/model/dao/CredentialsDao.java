package model.dao;

import model.entities.Credentials;

public interface CredentialsDao {

    public void update(Credentials credentials);
    public Credentials instantiateCredentials();

}
