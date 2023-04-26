package com.ChatApp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner sc = new Scanner(System.in);
            while (socket.isConnected()) {
                String sendToMessage = sc.nextLine();

                //self

//                if (sendToMessage.trim().equalsIgnoreCase("bye")){
//                    closeEverything(socket,bufferedReader,bufferedWriter);
//                }else {
//
//                    bufferedWriter.write(userName + ": " + sendToMessage);
//                    bufferedWriter.newLine();
//                    bufferedWriter.flush();
//
//                }


                //end self


                bufferedWriter.write(userName + ": " + sendToMessage);
                bufferedWriter.newLine();
                bufferedWriter.flush();

//                if (sendToMessage.equalsIgnoreCase("Bye")){
//                    closeEverything(socket,bufferedReader,bufferedWriter);
//                }
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);

        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        messageFromGroupChat = bufferedReader.readLine();


                        //self

                        if (messageFromGroupChat.contains("@")) {
                            String specialMessage = messageFromGroupChat.substring(messageFromGroupChat.indexOf("@") + 1);
                            String[] users = specialMessage.split(",");
                            for (String u : users) {
                                if (u.equalsIgnoreCase(userName)) {
                                    System.out.println(messageFromGroupChat.substring(0, messageFromGroupChat.indexOf("@")));
                                }
                            }
                        } else {

                            System.out.println(messageFromGroupChat);
                        }


//end self

                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your name to start chatting: ");
        String userName = sc.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, userName);
        client.listenForMessage();
        client.sendMessage();
    }
}
