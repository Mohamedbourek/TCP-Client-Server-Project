/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO1;
import java.io.*;
import java.net.*;
import java.util.*;
public class ServeurWithoutName {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("Serveur en attente de connexion...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket);
            
            HashMap<String, Integer> map = new HashMap<>();
            try (Scanner scanner = new Scanner(new File("./src/EXO1/symptomes.txt"))) {
                while (scanner.hasNextLine()) {
                    ajouter(map, scanner.nextLine());
                }
            }
            
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                String symptome = input.readUTF().trim();
                int nombreFois = map.getOrDefault(symptome, 0);
                output.writeInt(nombreFois);
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

    static void ajouter(Map<String, Integer> map, String symptome) {
        map.compute(symptome, (key, value) -> (value == null) ? 1 : value + 1);
    } 
}
