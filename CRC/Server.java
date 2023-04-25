package com.CRC;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        System.out.println("Waiting for client...");
        try {
            ServerSocket serverSocket = new ServerSocket(5566);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected..!\n");
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String[] str = bufferedReader.readLine().split(" ");

                System.out.println("Input data is: " + str[0]);

                ArrayList<Integer> sendData = new ArrayList<>();
                ArrayList<Integer> receiveData = new ArrayList<>();

                for (int i = 0; i < str[0].length(); i++) {
                    sendData.add(Character.getNumericValue(str[0].charAt(i)));
                }

                for (int i = 0; i < str[1].length(); i++) {
                    receiveData.add(Character.getNumericValue(str[1].charAt(i)));
                }

                int[] data = new int[sendData.size()];
                int[] rcData = new int[receiveData.size()];

                for (int i = 0; i < data.length; i++) {
                    data[i] = sendData.get(i);
                }

                for (int i = 0; i < rcData.length; i++) {
                    rcData[i] = receiveData.get(i);
                }

                CRC c = new CRC();
                c.mainOp(data, rcData);

                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("\nCRC value: " + c.getCrcValue() + "\n" + c.getSendData() + "\n" + c.getOutput());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CRC {
    private String crcValue;
    private String output;
    private String sendData;

    public String getCrcValue() {
        return crcValue;
    }

    public void setCrcValue(String crcValue) {
        this.crcValue = crcValue;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    void mainOp(int[] data, int[] sendData) {

        int divisor[] = {1, 0, 0, 0, 1, 1};

        int rem[] = divideDataWithDivisor(data, divisor);

        String crc = new String();
        for (int i = 0; i < rem.length - 1; i++) {
            crc += rem[i];
        }
        System.out.println("CRC value: " + crc);
        this.setCrcValue(crc);

        System.out.print("Sending data is: ");
        String snd = new String();

        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
            snd += data[i];
        }
        this.setSendData("Sending data is: " + snd + crc);

        for (int i = 0; i < rem.length - 1; i++) {
            System.out.print(rem[i]);
        }

        System.out.println();

        int sentData[] = new int[data.length + rem.length - 1];

        for (int i = 0; i < sentData.length; i++) {
            sentData[i] = sendData[i];
        }
        receiveData(sentData, divisor);
    }

    static int[] divideDataWithDivisor(int oldData[], int divisor[]) {
        int rem[] = new int[divisor.length];
        int i;
        int data[] = new int[oldData.length + divisor.length];
        System.arraycopy(oldData, 0, data, 0, oldData.length);
        System.arraycopy(data, 0, rem, 0, divisor.length);
        for (i = 0; i < oldData.length; i++) {
            if (rem[0] == 1) {
                for (int j = 1; j < divisor.length; j++) {
                    rem[j - 1] = exorOperation(rem[j], divisor[j]);
                }
            } else {
                for (int j = 1; j < divisor.length; j++) {
                    rem[j - 1] = exorOperation(rem[j], 0);
                }
            }
            rem[divisor.length - 1] = data[i + divisor.length];
        }
        return rem;
    }

    static int exorOperation(int x, int y) {
        if (x == y) {
            return 0;
        }
        return 1;
    }

    void receiveData(int data[], int divisor[]) {

        int rem[] = divideDataWithDivisor(data, divisor);
        for (int i = 0; i < rem.length; i++) {
            if (rem[i] != 0) {
                System.out.print("Corrupted data received...\nThat is: ");
                String cd = new String();
                for (int j = 0; j < data.length; j++) {
                    System.out.print(data[j]);
                    cd += data[j];
                }
                this.setOutput("Corrupted data received...\nThat is: " + cd);
                return;
            }
        }
        System.out.print("Data received without any error.\nThat is: ");
        String notError = new String();
        for (int j = 0; j < data.length; j++) {
            System.out.print(data[j]);
            notError += data[j];
        }
        this.setOutput("Data received without any error...\nThat is: " + notError);
    }
}