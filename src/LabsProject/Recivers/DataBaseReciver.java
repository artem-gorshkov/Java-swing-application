package LabsProject.Recivers;

import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.Nature.Homosapiens.Propetyies.MaterialProperty;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class DataBaseReciver implements Reciver {
    private Connection conn;
    public DataBaseReciver(Connection connection) {
        this.conn = connection;
    }

    public List<Human> getAllHuman() throws SQLException {
        Statement st = conn.createStatement();
        final String getall = "Select * from humans, properties";
        ResultSet rs = st.executeQuery(getall);
        List<Human> humans = new LinkedList<>();
        while(rs.next()) {
            humans.add(HumanFromTable(rs));
        }
        st.close();
        return humans;
    }
    public Human HumanFromTable(ResultSet rs) throws SQLException {
        Human human = new Human(rs.getString(1));
        human.setBirthday(ZonedDateTime.of(rs.getObject(2, LocalDateTime.class), ZoneId.systemDefault()));
        human.setCondition(Condition.valueOf(rs.getString(3)));
        human.setCordX(rs.getInt(4));
        human.setCordY(rs.getInt(5));
        human.addProperty(new MaterialProperty(rs.getString(7)));
        return human;
    }
    public Human return_max() throws SQLException {
        Statement st = conn.createStatement();
        final String findMax = "select * from humans, properties where birthday = (select max(birthday) from humans)";
        st.close();
        return HumanFromTable(st.executeQuery(findMax));

    }
    public int addHuman(Human human, String login) throws SQLException{
        Statement st = conn.createStatement();
        final String add = "Insert into Humans Values (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(add);
        pst.setString(1, human.getName());
        pst.setObject(2, human.getBirthday().toLocalDateTime());
        pst.setString(3, human.getCondition().toString());
        pst.setDouble(4, human.getCordX());
        pst.setDouble(5, human.getCordY());
        pst.setString(6, login);
        st.close();
        return pst.executeUpdate();
    }
    public int addUser(String nick, String hashpswd, String salt) throws SQLException {
        final String addUser = "Insert into Users values (?,?,?)";
        PreparedStatement pst = conn.prepareStatement(addUser);
        pst.setString(1, nick);
        pst.setString(2, hashpswd);
        pst.setString(3, salt);
        pst.close();
        return pst.executeUpdate();
    }
    public String[] getPswd(String nick) throws SQLException {
        final String getpswd = "Select password, salt from users where nickname = ?";
        PreparedStatement pst = conn.prepareStatement(getpswd);
        pst.setString(1, nick);
        ResultSet rs = pst.executeQuery();
        pst.close();
        return new String[] {rs.getString(1), rs.getString(2)};
    }
    public long numberOfElement() throws SQLException {
        final String getNum = "select count(name) from Humans";
        Statement st = conn.createStatement();
        ResultSet rs  = st.executeQuery(getNum);
        st.close();
        return rs.getLong(1);
    }
    public long numberOfUser() throws SQLException {
        final String getNum = "select count(nickname) from Users";
        Statement st = conn.createStatement();
        ResultSet rs  = st.executeQuery(getNum);
        st.close();
        return rs.getLong(1);
    }
    public int removeHuman(String name,String nick) throws SQLException {
        final String removeHum = "DELETE FROM Humans WHERE name = ?, nickname = ?";
        PreparedStatement pst = conn.prepareStatement(removeHum);
        pst.setString(1, name);
        pst.setString(2, nick);
        pst.close();
        return pst.executeUpdate();
    }
    public List<String> getGreaterName(String name) throws SQLException {
        final String getGreater = "Select name from Humans where name > ?";
        PreparedStatement pst = conn.prepareStatement(getGreater);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        List<String> names = new LinkedList<>();
        while(rs.next()) {
            names.add(rs.getString(1));
        }
        return names;
    }
}
