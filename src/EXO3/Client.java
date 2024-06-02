/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9001);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Voulez-vous envoyer une image ou un fichier texte ? Tapez 'image' ou 'txt'");
        String type = br.readLine();
        out.writeUTF(type);
        if (type.equals("image")) {
            BufferedImage bimg = ImageIO.read(new File("./src/EXO3/univ.jpg"));
            ImageIO.write(bimg, "JPG", socket.getOutputStream());
            System.out.println("Image envoyée");
        } else if (type.equals("txt")) {
            ABC.mystere(new FileInputStream("./src/EXO3/test1.txt"), socket.getOutputStream());
            System.out.println("Fichier texte envoyé");
        }
        out.close();
        socket.close();
    }

}
