/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO2;

import java.io.*;
import java.awt.image.BufferedImage;
import java.net.Socket;
import javax.imageio.ImageIO;

public class ClientImage {

    static BufferedImage bimg;

    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 6066);
            bimg = ImageIO.read(new File("./src/EXO2/univ.JPG"));
            ImageIO.write(bimg, "JPG", client.getOutputStream());
            client.close();
        } catch (IOException e) {
        }
    }

}
