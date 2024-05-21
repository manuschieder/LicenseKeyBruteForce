package com.example;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) {
        String host = "master.lab";
        int port = 54321;

        try {
            for (int i = 99999999; i >= 0; i--) { // Absteigende Schleife von 99999999 bis 0
                String licenseKey = String.format("%08d", i); // Generiert einen 8-stelligen Schlüssel mit führenden Nullen
                if (isLicenseKeyValid(host, port, licenseKey)) {
                    System.out.println("Found valid license key: " + licenseKey);
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

            // Sendet den Lizenzschlüssel zur Überprüfung
            String serialCommand = "serial=" + licenseKey;
            out.println(serialCommand);

            // Liest die Antwort vom Server
            String response = in.readLine();

            // Überprüft die Antwort des Servers
            boolean isValid = "SERIAL VALID=1".equalsIgnoreCase(response);
            if (isValid) {
                System.out.println("Tried key: " + licenseKey + " - Response: " + response);
            }
            return isValid;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

