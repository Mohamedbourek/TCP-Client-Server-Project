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

public class ClientWithName extends JFrame implements ActionListener {

    Socket sock = null;
    DataOutputStream sockOut = null;
    DataInputStream sockIn = null;
    private JButton jbtGetSyms = new JButton("Afficher les symptomes");
    private JTextField jtfNom = new JTextField();
    private JTextArea jtaSym = new JTextArea();

    public ClientWithName() {
        JPanel panneau = new JPanel();
        panneau.setLayout(new GridLayout(2, 2));
        panneau.add(new JLabel("Patient"));
        panneau.add(jtfNom);
        panneau.add(new JLabel("Symptomes"));
        panneau.add(new JScrollPane(jtaSym)); 
        add(panneau, BorderLayout.CENTER);
        add(jbtGetSyms, BorderLayout.SOUTH);
        jbtGetSyms.addActionListener(this);
    }

    public void init() {
        try {
            sock = new Socket("localhost", 7777);
            sockOut = new DataOutputStream(sock.getOutputStream());
            sockIn = new DataInputStream(sock.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Hôte non atteignable : localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connexion impossible avec : localhost");
            System.exit(1);
        }
    }

    @Override
   public void actionPerformed(ActionEvent e) {
        try {
            sockOut.writeUTF(jtfNom.getText().trim());
            sockOut.flush();
            
            String symptomes = sockIn.readUTF();
            jtaSym.setText(symptomes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientWithName client = new ClientWithName();
        client.setTitle("Traitement des symptômes");
        client.setSize(350, 200); 
        client.init();
        client.setLocationRelativeTo(null);
        client.setVisible(true);
    }
}

