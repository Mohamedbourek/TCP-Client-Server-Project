/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

public class Serveur {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9001);
        while (true) {
            Socket sock = serverSocket.accept();
            Thread clientThread = new Thread(new ClientHandler(sock));
            clientThread.start();
        }
    }

    static class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                String type = in.readUTF();
                System.out.println(type);

                if (type.equals("image")) {
                    BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(clientSocket.getInputStream()));
                    ImageIO.write(img, "jpg", new File("./src/EXO3/recv_img.jpg"));
                    System.out.println("Image reçue et enregistrée");
                } 
                else if (type.equals("txt")) {
                    ABC.mystere(clientSocket.getInputStream(), new FileOutputStream("./src/EXO3/recv_text.txt"));
                    System.out.println("Fichier texte reçu et enregistré");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
