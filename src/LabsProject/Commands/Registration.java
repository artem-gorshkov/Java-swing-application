package LabsProject.Commands;

import LabsProject.GeneratorPassword;
import LabsProject.Mail.SendMail;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class Registration extends Authorization {

    public Registration() {

    }

    public Registration(String arguments) {
        super(arguments);
    }

    @Override
    public Result execute(Reciver reciver) throws SQLException {
        try {
            DataBaseReciver dbReciver = (DataBaseReciver) reciver;
            String passwd = GeneratorPassword.get();
            String salt = GeneratorPassword.getSalt();
            String hashpswd = GeneratorPassword.hash(passwd, salt);
            String mail = "Hello!\nYour login: " + this.getNickname() + "\nYour password: " + passwd;
            SendMail sm = new SendMail();
            sm.send(mail, this.getArguments());
            if (dbReciver.checkUser(this.getNickname())) {
                int count = dbReciver.addUser(this.getNickname(), hashpswd, salt);
                if (count == 1) {
                    return new Result("Check your email");
                } else {
                    return new Result("Can't add new user");
                }
            } else {
                return new Result("User already exist");
            }
        } catch (MessagingException e) {
            return new Result("Incorrect email");
        }
    }
}
