/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EXO3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class ClientWithIHM  extends JFrame implements ActionListener {
      private Socket socket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private JButton sendButton = new JButton("Envoyer");
    private JComboBox<String> dataTypeComboBox = new JComboBox<>(new String[]{"image", "texte"});
    private JTextField filePathField = new JTextField();
    private JTextArea responseArea = new JTextArea();

    public ClientWithIHM() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Type de donnée"));
        panel.add(dataTypeComboBox);
        panel.add(new JLabel("Chemin du fichier"));
        panel.add(filePathField);
      
        add(panel, BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);
        sendButton.addActionListener(this);

    }

    public void initialize() {
        try {
            socket = new Socket("localhost", 9001);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu : localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connexion impossible avec : localhost");
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String dataType = (String) dataTypeComboBox.getSelectedItem();
            String filePath = filePathField.getText().trim();
            out.writeUTF(dataType);
            out.writeUTF(filePath);
            out.flush();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
            ClientWithIHM client = new ClientWithIHM();
            client.setTitle("Client IHM");
            client.setSize(400, 250);
            client.initialize();
            client.setLocationRelativeTo(null);
            client.setVisible(true);
        
    }
}
