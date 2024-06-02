/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO1;
import java.io.*;
import java.net.*;
import java.util.*;
public class ServeurWithName {
     public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("Serveur en attente de connexion...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket);
            
            HashMap<String, List<String>> map = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("./src/EXO1/symptomesnom.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\s+", 2); 
                    String nom = parts[0];
                    String symptome = parts[1];
                    map.computeIfAbsent(nom, k -> new ArrayList<>()).add(symptome);
                }
            }
            
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String nomPatient = input.readUTF().trim();
                System.out.println(nomPatient);
                List<String> symptomes = map.get(nomPatient);
                StringBuilder symptomesString = new StringBuilder();
                for (String symptome : symptomes) {
                    symptomesString.append(symptome).append(", ");
                }
                output.writeUTF(symptomesString.toString());
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     
   
}
