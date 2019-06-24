package LabsProject.NetworkInteraction;


import LabsProject.Commands.*;
import LabsProject.Nature.Homosapiens.Human;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;


public class ConnectionClient {

    private DatagramChannel channel;

    public ConnectionClient(InetAddress serverAddress, Integer port) {
        try {
            SocketAddress address = new InetSocketAddress(serverAddress, port);
            this.channel = DatagramChannel.open();
            while (!channel.isConnected())
                channel.connect(address);
        } catch (IOException e) {
            System.err.println("Can't create channel");
        }
    }

    public void send(Command command) {
        try {
            ByteArrayOutputStream arrayOutStream = new ByteArrayOutputStream();
            ObjectOutputStream objOutStream = new ObjectOutputStream(arrayOutStream);
            objOutStream.writeObject(command);
            if (command instanceof Import)
                objOutStream.writeObject(sendCollection(((Import) command).getArguments()));
            ByteBuffer outBuffer = ByteBuffer.wrap(arrayOutStream.toByteArray());
            channel.write(outBuffer);
            objOutStream.flush();
            objOutStream.close();
        } catch (Exception e) {
            System.err.println("can't send package");
        }
    }

    private void Request() {
        try {
            ByteArrayOutputStream a = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(a);
            o.writeObject(new Request());
            o.flush();
            ByteBuffer outBuffer = ByteBuffer.wrap(a.toByteArray());
            ByteBuffer inBuffer = ByteBuffer.allocate(2);
            boolean k = true;
            long time = System.currentTimeMillis();
            while (k) {
                try {
                    if (System.currentTimeMillis() - time > 1000) {
                        time = System.currentTimeMillis();
                        channel.write(outBuffer);
                        outBuffer.flip();
                        channel.read(inBuffer);
                        k = false;
                    }
                } catch (PortUnreachableException ee) {
                }
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    final String yes = "login_successful";
    final String no = "incorrect";
    final String parol = "Check";
    final String email = "incorrect";
    final String newuser = "incorrect";

    public boolean handlePacket(Result result, Authorization command) {
        System.out.println(result.getAnswer());
        if (result.getAnswer().equals(yes) || result.getAnswer().equals(parol)) {
            return true;
        } else {
            return false;
        }
    }

    public void handlePacket(Result result, Command command) {
        if (result.getAnswer() != null)
            System.out.println(result.getAnswer());
        if (result.getHumans() != null) {
            ManagerCollection.outCollection(result.getHumans());
        }
    }

    public Result getPacket(Command command) throws ServerNotRespondException {
        try {
            ByteBuffer inBuffer = ByteBuffer.allocate(Integer.MAX_VALUE / 32);
            channel.read(inBuffer);
            inBuffer.flip();
            ByteArrayInputStream arrayInStream = new ByteArrayInputStream(inBuffer.array());
            ObjectInputStream objInStream = new ObjectInputStream(arrayInStream);
            return (Result) objInStream.readObject();
        } catch (PortUnreachableException ee) {
            System.err.println("Server is not responding");
            Request();
            throw new ServerNotRespondException();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Can't take packet");
            throw new ServerNotRespondException();
        }
    }

    public Result sendAndGetAnswer(Command command) {
        Result result;
        while (true)
            try {
                send(command);
                result = getPacket(command);
                return result;
            } catch (ServerNotRespondException e) {
            }
    }

    public List<Human> sendCollection(String arg) throws Exception {
        try {
            return ManagerCollection.exportfromfile(arg);
        } catch (Exception e) {
            throw e;
        }
    }
}