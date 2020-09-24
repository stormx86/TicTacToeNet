package com.tictactoenet;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

class Connector {
    private static final char MARK_X = 'X';
    private static final char MARK_0 = '0';
    private ServerSocket ss = new ServerSocket(0, 1, InetAddress.getLocalHost());
    private String whereToSendIP;
    private int whereToSendPort;
    private Random rand = new Random();
    private char roleXor0;

    public ServerSocket getSs() {
        return ss;
    }

    public void setSs(ServerSocket ss) {
        this.ss = ss;
    }

    public String getWhereToSendIP() {
        return whereToSendIP;
    }

    public int getWhereToSendPort() {
        return whereToSendPort;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public char getRoleXor0() {
        return roleXor0;
    }

    public void setRoleXor0(char roleXor0) {
        this.roleXor0 = roleXor0;
    }

    private void setWhereToSendIP(String whereToSendIP) {
        this.whereToSendIP = whereToSendIP;
    }

    private void setWhereToSendPort(int whereToSendPort) {
        this.whereToSendPort = whereToSendPort;
    }

    Connector() throws IOException {
    }


    public void selectNetRole() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Server - input 1 / Client - input 2");
        while (!in.hasNextInt()) {
            System.out.println("Incorrect input!");
            System.out.println("Server - input 1 / Client - input 2");
            in.next();
        }
        int result = in.nextInt();
        if (result == 1) {
            runServerMode();
        } else if (result == 2) {
            runClientMode();
        } else {
            in.reset();
            System.out.println("Incorrect input!");
            selectNetRole();
        }
    }


    public void runServerMode() {
        System.out.println("\r\nRunning Server: " + "Host=" + getSocketAddress().getHostAddress() + " Port=" + getServerPort());
        String[] inputData = receiver();
        whereToSendIP = inputData[1];
        whereToSendPort = Integer.parseInt(inputData[2]);
        if (inputData[0].equals("1")) {
            System.out.print("\rYou got X");
            roleXor0 = MARK_X;
        } else {
            System.out.println("\rYou got 0");
            roleXor0 = MARK_0;
        }
    }


    public void runClientMode() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Provide server IP");
        setWhereToSendIP(in.next());
        System.out.println("Provide server port");
        setWhereToSendPort(in.nextInt());
        if (rand.nextInt(101) < 50) {
            String[] outputData = {"1", InetAddress.getLocalHost().getHostAddress(), Integer.toString(getServerPort())};
            sender(outputData);
            System.out.println("\r\nYou got 0");
            roleXor0 = MARK_0;
        } else {
            String[] outputData = {"0", InetAddress.getLocalHost().getHostAddress(), Integer.toString(getServerPort())};
            sender(outputData);
            System.out.println("\r\nYou got X");
            roleXor0 = MARK_X;
        }
    }


    public void sender(String[] outputData) {
        try (Socket socket = new Socket(whereToSendIP, whereToSendPort);) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(outputData);
        } catch (Exception ex) {
            System.out.println("Connection failed" + ex);
            System.exit(0);
        }
    }

    public String[] receiver() {
        String[] input = new String[0];
        try (Socket socket = ss.accept()) {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            input = (String[]) objectInputStream.readObject();
        } catch (IOException ex) {
            System.out.println("IOException occurred" + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found exception occured" + ex);
        }
        return input;
    }

    public InetAddress getSocketAddress() {
        return ss.getInetAddress();
    }

    public int getServerPort() {
        return ss.getLocalPort();
    }


}
