package LabsProject.Commands;

import LabsProject.GeneratorPassword;
import LabsProject.Mail.SendMail;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.sql.SQLException;

public class Registration extends Authorization {

    public Registration() {

    }

    public Registration(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        DataBaseReciver dbReciver = (DataBaseReciver) reciver;
        String passwd = GeneratorPassword.get();
        String salt = GeneratorPassword.get();
        String hashpswd = GeneratorPassword.hash(passwd, salt);
        String mail = "Hello!\nYour login: " + this.getNickname() + "\nYour password: " + passwd;
        SendMail sm = new SendMail();
        sm.send(mail, this.getArguments());
        int count = dbReciver.addUser(this.getNickname(), hashpswd, salt);
        if (count == 1) {
            return new Result("check your email");
        }
        else {
            return new Result("Can't add new user");
        }
    }
}
