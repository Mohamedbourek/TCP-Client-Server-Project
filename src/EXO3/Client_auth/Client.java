package Client_auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;

public class Client extends JFrame implements ActionListener {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private JComboBox<String> dataTypeComboBox;
    private JTextField filePathTextField;
    private JButton sendButton;
    private Login loginWindow;

    public Client() {
        loginWindow = new Login(this);
    }

    public void init() {
        try {
            socket = new Socket("localhost", 7777);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
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
        if (socket == null || out == null || in == null) {
            init();
        }

        String dataType = (String) dataTypeComboBox.getSelectedItem();
        String filePath = filePathTextField.getText().trim();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    sendData(dataType, filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };

        worker.execute();
    }

    public boolean authenticate(String username, String password) {
        try {
            if (socket == null || out == null || in == null) {
                init();
            }

            out.writeUTF(username);
            out.writeUTF(password);
            out.flush();

            String response = in.readUTF();
            return response.equals("Login successful");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendData(String dataType, String filePath) throws IOException {
        String text = dataType + "  " + new File(filePath).getName();
        out.writeUTF(text);
        out.flush();

        if (dataType.equals("Image")) {
            sendImage(filePath);
        } else if (dataType.equals("Fichier texte")) {
            sendTextFile(filePath);
        }
    }

    private void sendImage(String filePath) throws IOException {
        BufferedImage bimg = ImageIO.read(new File(filePath));
        ImageIO.write(bimg, "png", socket.getOutputStream());
        System.out.println("Image envoyée");
    }

    private void sendTextFile(String filePath) throws IOException {
        try (InputStream fileStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }
        System.out.println("Fichier texte envoyé");
    }

    private void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClientUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        dataTypeComboBox = new JComboBox<>(new String[]{"Fichier texte", "Image"});
        filePathTextField = new JTextField("./src/EXO3/Client_auth/file1.txt");
        sendButton = new JButton("Envoyer");
        sendButton.addActionListener(this);

        panel.add(new JLabel("Type de données :"));
        panel.add(dataTypeComboBox);
        panel.add(new JLabel("Chemin du fichier :"));
        panel.add(filePathTextField);
        panel.add(sendButton);

        add(panel);

        dataTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dataTypeComboBox.getSelectedItem();
                if (selectedOption.equals("Image")) {
                    filePathTextField.setText("./src/EXO3/Client_auth/univ.png");
                } else if (selectedOption.equals("Fichier texte")) {
                    filePathTextField.setText("./src/EXO3/Client_auth/file1.txt");
                }
            }
        });

        setTitle("Client");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnection();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
