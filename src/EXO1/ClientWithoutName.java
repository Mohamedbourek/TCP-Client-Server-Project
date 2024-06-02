/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientWithoutName extends JFrame implements ActionListener {

    Socket sock = null;
    DataOutputStream sockOut = null;
    DataInputStream sockIn = null;
    private JButton jbtGetNbFois = new JButton("Afficher le nombre de fois");
    private JTextField jtfSym = new JTextField();
    private JTextField jtfFois = new JTextField();

    public ClientWithoutName() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new GridLayout(2, 2));
        panneau.add(new JLabel("Symptome"));
        panneau.add(jtfSym);
        panneau.add(new JLabel("Nombre de fois"));
        panneau.add(jtfFois);
        add(panneau, BorderLayout.CENTER);
        add(jbtGetNbFois, BorderLayout.SOUTH);
        jbtGetNbFois.addActionListener(this);
    }

    public void init() {
        try {
            sock = new Socket("localhost", 7777);
            sockOut = new DataOutputStream(sock.getOutputStream());
            sockIn = new DataInputStream(sock.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("host non atteignable : localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("connection impossible avec : localhost");
            System.exit(1);
        }
    }

    @Override
   public void actionPerformed(ActionEvent e) {
        try {
            sockOut.writeUTF(jtfSym.getText().trim());
            sockOut.flush();
            
            int nombreDeFois = sockIn.readInt();
            jtfFois.setText(String.valueOf(nombreDeFois));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientWithoutName a = new ClientWithoutName();
        a.setTitle("Traitement des symptomes");
        a.setSize(350, 150);
        a.init();
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }

}
