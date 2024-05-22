package com.example;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) {
        String host = "master.lab";
        int port = 54321;

        try {
            for (int i = 0; i <= 99999999; i++) { 
                String licenseKey = String.format("%08d", i); 
                if (isLicenseKeyValid(host, port, licenseKey)) {
                    System.out.println("Found key: " + licenseKey);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isLicenseKeyValid(String host, int port, String licenseKey) {
        try (Socket socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send license key for checking
            String serialCommand = "serial=" + licenseKey;
            out.println(serialCommand);

            // get clients response
            String response = in.readLine();

            // check response
            if (!"SERIAL_VALID=0".equalsIgnoreCase(response)) {
                //System.out.println("Found key: " + licenseKey + " - Response: " + response);
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
