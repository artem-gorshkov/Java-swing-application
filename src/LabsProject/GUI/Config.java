package LabsProject.GUI;

import java.net.InetAddress;

public class Config {
    private String nickname;
    private String passwrd;
    private InetAddress address;
    private int port;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Config(String nickname, String passwrd, InetAddress address, int port, int color) {
        this.nickname = nickname;
        this.passwrd = passwrd;
        this.address = address;
        this.port = port;
        this.color = color;
    }
}
