/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO2;

import java.io.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import javax.imageio.ImageIO;

public class ServerImage {

    public static void main(String[] args) throws IOException {
        try {
            Socket server = new ServerSocket(6066).accept();
            BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
            ImageIO.write(img, "JPG", new File("./src/EXO2/test.JPG"));
            System.out.println("Image reçue");
        } catch (Exception ex) {
        }
    }

}
