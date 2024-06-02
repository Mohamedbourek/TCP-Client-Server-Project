package EXO3.Serveur_auth;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Serveur {

    private static ArrayList<User> users = new ArrayList<>();

    static {
        users.add(new User("mohammed", "2003"));
        users.add(new User("mourad", "2002"));
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Serveur en attente de connexion...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion établie avec " + clientSocket);

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

                while (true) {
                    String username = in.readUTF();
                    String password = in.readUTF();

                    if (authenticate(username, password)) {
                        out.writeUTF("Login successful");
                        out.flush();
                        break;
                    } else {
                        out.writeUTF("Login failed");
                        out.flush();
                    }
                }

                while (true) {
                    String text = in.readUTF();
                    if (text.equals("Terminer")) {
                        System.out.println("Connexion terminée avec " + clientSocket);
                        break;
                    }
                    String[] parts = text.split("  ");
                    String type = parts[0];
                    String filename = parts[1];
                    System.out.println(type);
                    System.out.println(filename);

                    if (type.equals("Image")) {
                        receiveImage(in, filename);
                    } else if (type.equals("Fichier texte")) {
                        receiveTextFile(in, filename);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean authenticate(String username, String password) {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true;
                }
            }
            return false;
        }

        private void receiveImage(DataInputStream in, String filename) throws IOException {
            BufferedImage bimg = ImageIO.read(ImageIO.createImageInputStream(clientSocket.getInputStream()));
            ImageIO.write(bimg, "png", new File("./src/EXO3/Serveur_auth/" + filename));
            System.out.println("Image reçue et enregistrée");
        }

        private void receiveTextFile(DataInputStream in, String filename) throws IOException {
            ABC.mystere(clientSocket.getInputStream(), new FileOutputStream("./src/EXO3/Serveur_auth/" + filename));
            System.out.println("Fichier texte reçu et enregistré");
        }
    }

    private static class User {

        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
