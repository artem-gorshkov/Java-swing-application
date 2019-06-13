package LabsProject.Recivers;

import LabsProject.Nature.Homosapiens.Human;

import java.sql.SQLException;
import java.util.List;

public class DBReciverWithImport implements Reciver {
    private DataBaseReciver DBreciver;
    private List<Human> humans;
    public DBReciverWithImport(DataBaseReciver db, List<Human> humans) {
        this.DBreciver  = db;
        this.humans = humans;
    }
    public void ImportHumans(String login) throws SQLException {
        for(Human human : humans) {
            DBreciver.addHuman(human, login);
        }
    }
    public List<Human> getAllHuman() throws SQLException{
        return DBreciver.getAllHuman();
    }

}
