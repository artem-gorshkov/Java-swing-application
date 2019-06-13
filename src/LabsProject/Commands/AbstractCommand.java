package LabsProject.Commands;

import LabsProject.Recivers.Reciver;
import LabsProject.NetworkInteraction.Result;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class AbstractCommand implements Command, Serializable {
    private String arguments;
    private String nickname;

    public AbstractCommand() {

    }

    public AbstractCommand(String arguments) {
        this.arguments = arguments;
    }

    public void addNick(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public abstract Result execute(Reciver reciver) throws SQLException;

    public String getArguments() {
        return arguments;
    }
    public String getNickname() {
        return nickname;
    }
}