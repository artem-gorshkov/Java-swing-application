package LabsProject.NetworkInteraction;


import LabsProject.Nature.Homosapiens.Human;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentSkipListMap;


public class ConnectionClient {

    private DatagramChannel channel;
    private ManagerCollection collection = new ManagerCollection();
    private Command interruptedCommand;


    ConnectionClient(InetAddress serverAddress, Integer port) {
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
            if (command.getCommands() == Commands.IMPORT)
                objOutStream.writeObject(sendCollection(command.getArg()));
            ByteBuffer outBuffer = ByteBuffer.wrap(arrayOutStream.toByteArray());
            channel.write(outBuffer);
            objOutStream.flush();
            objOutStream.close();
            getPacket(command);
        } catch (IOException e) {
            System.err.println("can't send package");
        }
    }

    private void Request() {
        try {
            ByteArrayOutputStream a = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(a);
            o.writeObject(new Command(Commands.REQUEST));
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

    private void getPacket(Command command) {
        try {
            ByteBuffer inBuffer = ByteBuffer.allocate(Integer.MAX_VALUE / 32);
            channel.read(inBuffer);
            inBuffer.flip();
            ByteArrayInputStream arrayInStream = new ByteArrayInputStream(inBuffer.array());
            ObjectInputStream objInStream = new ObjectInputStream(arrayInStream);
            Object object = objInStream.readObject();
            if (object instanceof String) {
                String str = (String) object;
                System.out.println(str);
                if (command.getCommands() == Commands.EXIT) {
                    channel.close();
                    System.exit(0);
                }
                if (command.getCommands() != Commands.INFO)
                    try {
                        Object object1 = objInStream.readObject();
                        getCollection(object1);
                    } catch (StreamCorruptedException e) {
                    }
            } else getCollection(object);
        } catch (PortUnreachableException ee) {
            interruptedCommand = command;
            System.err.println("Server is not responding");
            Request();
        } catch (Exception e) {
            System.err.println("Can't take packet");
        }
    }

    public void getCollection(Object object) {
        try {
            collection.getCollection().clear();
            collection.setCollection((ConcurrentSkipListMap<String, Human>) object);
            collection.outCollection();
        } catch (Exception e) {
            System.err.println("Can't read collection");
        }
    }

    public ConcurrentSkipListMap<String, Human> sendCollection(String arg) throws IOException {
        try {
            collection.getCollection().clear();
            collection.exportfromfile(arg);
            return collection.getCollection();
        } catch (IOException e) {
            throw e;
        }
    }

    public void setInterruptedCommand(Command interruptedCommand) {
        this.interruptedCommand = interruptedCommand;
    }

    public Command getInterruptedCommand() {
        return interruptedCommand;
    }
}