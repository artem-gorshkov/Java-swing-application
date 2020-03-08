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
            if (this.getArguments().equals("helios"))
                return helios(dbReciver);
            String passwd = GeneratorPassword.get();
            String salt = GeneratorPassword.getSalt();
            String hashpswd = GeneratorPassword.hash(passwd, salt);
            String mail = "Hello!\nYour login: " + this.getNickname() + "\nYour password: " + passwd;
            SendMail sm = new SendMail();
            dbReciver.checkUser(this.getNickname());
            if (dbReciver.checkUser(this.getNickname())) {
                sm.send(mail, this.getArguments());
                int count = dbReciver.addUser(this.getNickname(), hashpswd, salt);
                if (count == 1) {
                    return new Result("Check");
                } else {
                    return new Result("incorrect");
                }
            } else {
                return new Result("User already exist");
            }
        } catch (MessagingException e) {
            return new Result("incorrect");
        }
    }
    private Result helios(DataBaseReciver reciver) throws  SQLException{
        String passwd = GeneratorPassword.get();
        String salt = GeneratorPassword.getSalt();
        String hashpswd = GeneratorPassword.hash(passwd, salt);
        if (reciver.checkUser(this.getNickname())) {
            int count = reciver.addUser(this.getNickname(), hashpswd, salt);
            if (count == 1) {
                return new Result("Your password: " + passwd);
            } else {
                return new Result("Can't add new user");
            }
        } else {
            return new Result("User already exist");
        }
    }
}
