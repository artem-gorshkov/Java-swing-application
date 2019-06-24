package LabsProject.Recivers;

import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.Nature.Homosapiens.Propetyies.MaterialProperty;
import LabsProject.Nature.Homosapiens.Propetyies.Property;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
        final String getall = "Select * from humans";
        ResultSet rs = st.executeQuery(getall);
        List<Human> humans = new LinkedList<>();
        while (rs.next()) {
            humans.add(HumanFromTable(rs));
        }
        return humans;
    }

    public Human HumanFromTable(ResultSet rs) throws SQLException {
        Human human = new Human(rs.getString(1));
        OffsetDateTime birthday = rs.getObject(2, OffsetDateTime.class);
        ZoneId zone = ZoneId.of(rs.getString(3));
        human.setBirthday(birthday.atZoneSameInstant(zone));
        human.setCondition(Condition.valueOf(rs.getString(4)));
        human.setCordX(rs.getInt(5));
        human.setCordY(rs.getInt(6));
        human.setColor(rs.getInt(8));
        final String getProp = "select properties.name from properties where properties.human = ?";
        PreparedStatement pst = conn.prepareStatement(getProp);
        pst.setString(1, human.getName());
        ResultSet rsprop = pst.executeQuery();
        while (rsprop.next()) {
            human.addProperty(new MaterialProperty(rsprop.getString(1)));
        }
        return human;
    }

    public Human return_max() throws SQLException {
        Statement st = conn.createStatement();
        final String findMax = "select * from humans, properties where birthday = (select max(birthday) from humans)";
        ResultSet rs = st.executeQuery(findMax);
        rs.next();
        return HumanFromTable(rs);

    }

    public int addHuman(Human human, String login) throws SQLException {
        final String add = "Insert into Humans Values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(add);
        pst.setString(1, human.getName());
        pst.setObject(2, human.getBirthday().toOffsetDateTime());
        pst.setString(3, human.getBirthday().getZone().toString());
        pst.setString(4, human.getCondition().toString());
        pst.setDouble(5, human.getCordX());
        pst.setDouble(6, human.getCordY());
        pst.setString(7, login);

        final String color = "Select color from Users where nickname =?";
        PreparedStatement st = conn.prepareStatement(color);
        st.setString(1, login);

        pst.setInt(8, st.executeQuery().getInt(1));
        int l = pst.executeUpdate();
        final String addProp = "Insert into Properties Values (?, ?)";
        PreparedStatement pstProp = conn.prepareStatement(addProp);
        pstProp.setString(2, human.getName());
        for (Property prop : human.getAllProperty()) {
            pstProp.setString(1, prop.getName());
            pstProp.executeUpdate();
        }
        return l;
    }

    private static class GetColor {
        static Color colors[] = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE};
        static int k = -1;

        public static Color getColor() {
            k++;
            return colors[k];
        }
    }

    public int addUser(String nick, String hashpswd, String salt) throws SQLException {
        final String addUser = "Insert into Users values (?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(addUser);
        pst.setString(1, nick);
        pst.setString(2, hashpswd);
        pst.setString(3, salt);
        pst.setInt(4, GetColor.getColor().getRGB());
        return pst.executeUpdate();
    }

    public String[] getPswd(String nick) throws SQLException {
        final String getpswd = "Select password, salt from users where nickname = ?";
        PreparedStatement pst = conn.prepareStatement(getpswd);
        pst.setString(1, nick);
        ResultSet rs = pst.executeQuery();
        String[] answer = new String[2];
        while (rs.next()) {
            answer[0] = rs.getString(1);
            answer[1] = rs.getString(2);
        }
        return answer;
    }

    public int getColor(String nick) throws SQLException {
        final String getcol = "Select color from users where nickname = ?";
        PreparedStatement pst = conn.prepareStatement(getcol);
        pst.setString(1, nick);
        ResultSet rs = pst.executeQuery();
        int col = 0;
        while (rs.next()) {
           col = rs.getInt(1);
        }
        return col;
    }

    public long numberOfElement() throws SQLException {
        final String getNum = "select count(name) from Humans";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(getNum);
        rs.next();
        return rs.getLong(1);
    }

    public long numberOfUser() throws SQLException {
        final String getNum = "select count(nickname) from Users";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(getNum);
        rs.next();
        return rs.getLong(1);
    }

    public int removeHuman(String name, String nick) throws SQLException {
        final String removeHum = "DELETE FROM Humans WHERE name = ? And nickname = ?";
        PreparedStatement pst = conn.prepareStatement(removeHum);
        pst.setString(1, name);
        pst.setString(2, nick);
        final String delProp = "DELETE FROM Properties where human = ? and (select name FROM Humans WHERE name = ? And nickname = ?) = ?";
        PreparedStatement pstProp = conn.prepareStatement(delProp);
        pstProp.setString(1, name);
        pstProp.setString(2, name);
        pstProp.setString(3, nick);
        pstProp.setString(4, name);
        pstProp.executeUpdate();
        return pst.executeUpdate();
    }

    public List<String> getGreaterName(String name) throws SQLException {
        final String getGreater = "Select name from Humans where name > ?";
        PreparedStatement pst = conn.prepareStatement(getGreater);
        pst.setString(1, name);
        ResultSet rs = pst.executeQuery();
        List<String> names = new LinkedList<>();
        while (rs.next()) {
            names.add(rs.getString(1));
        }
        return names;
    }

    public boolean checkKey(String key) throws SQLException {
        final String checkKey = "Select name from Humans where name = ?";
        return helpcheck(checkKey, key);
    }

    public boolean checkUser(String nick) throws SQLException {
        final String checkKey = "Select nickname from Users where nickname = ?";
        return helpcheck(checkKey, nick);
    }

    private boolean helpcheck(String query, String key) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, key);
        ResultSet rs = ps.executeQuery();
        String name = null;
        while (rs.next()) {
            name = rs.getString(1);
        }
        if (name != null) return false;
        else return true;
    }

    public void createTables() throws SQLException {
        final String user = "create table Users (nickname varchar(80) primary key, password varchar(160), salt varchar(6))";
        final String humans = "create table Humans(name varchar(80) primary key, birthday timestamp with time zone, timezone varchar(80), condition varchar(80), cordX real, cordY real, nickname varchar(80) references Users)";
        final String properties = "create table properties (name varchar(80), human varchar(80) references Humans (name))";
        Statement st = conn.createStatement();
        st.executeUpdate(user);
        st.executeUpdate(humans);
        st.executeUpdate(properties);
    }

    public void dropTables() throws SQLException {
        final String user = "DELETE FROM Users";
        final String humans = "DELETE FROM Humans";
        final String properties = "DELETE FROM properties";
        Statement st = conn.createStatement();
        st.executeUpdate(properties);
        st.executeUpdate(humans);
        st.executeUpdate(user);
    }

}
