/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.Socket;
import javax.imageio.ImageIO;

public class ClientCapt {

    static BufferedImage image;

    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 6000);
            Robot bot;
            bot = new Robot();
            image = bot.createScreenCapture(new Rectangle(0, 0, 500, 300));
            ImageIO.write(image, "JPG", client.getOutputStream());
            client.close();
        } catch (Exception e) {
        }
    }

}
