package com.CRC;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        System.out.println("Client Started...");
        try {
            Socket socket = new Socket("localhost", 5566);
            while (true) {
                System.out.print("\nEnter send data and received data: ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String str = bufferedReader.readLine();

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(str);

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(br.readLine());
                System.out.println(br.readLine());
                System.out.println(br.readLine());
                System.out.println(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}