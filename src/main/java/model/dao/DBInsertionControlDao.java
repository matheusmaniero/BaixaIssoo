package model.dao;

public interface DBInsertionControlDao {

    public void insert(Long timeStamp);
    public Long getLastInsertionTimeStamp();

    public void UpdateTimeStamp(Long timeStamp);

}
