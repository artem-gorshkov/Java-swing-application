package LabsProject.NetworkInteraction;

import LabsProject.Commands.Command;
import LabsProject.Commands.Import;
import LabsProject.Nature.Homosapiens.Human;
import LabsProject.Recivers.DBReciverWithImport;
import LabsProject.Recivers.DataBaseReciver;
import LabsProject.Recivers.Reciver;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.SQLException;
import java.util.List;

public class ConnectionServer implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket recievePacket;
    private byte[] commandByte;
    private DatagramPacket answerPacket;
    private Reciver reciver;

    ConnectionServer(DatagramSocket socket, DatagramPacket packet, Reciver reciver) {
        this.socket = socket;
        this.recievePacket = packet;
        this.reciver = reciver;
    }

    public void run() {
        try {
            commandByte = recievePacket.getData();
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(commandByte);
            ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);
            Command command = (Command) objectInputStream.readObject();
            Result result = null;
            if (command instanceof Import) {
                result = command.execute(new DBReciverWithImport((DataBaseReciver) reciver, ((List<Human>) objectInputStream.readObject())));
            }
            else {
                result = command.execute(reciver);
            }
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(result);
            answerPacket = new DatagramPacket(arrayOutputStream.toByteArray(), arrayOutputStream.toByteArray().length, recievePacket.getAddress(), recievePacket.getPort());
            socket.send(answerPacket);
        } catch(SQLException e) {
            for(Throwable t : e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
