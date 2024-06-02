package EXO3.Serveur_auth;

import java.io.*;

public class ABC {

    public static void mystere(InputStream in, OutputStream out) throws IOException {
        byte buf[] = new byte[1024];
        int n;
        while ((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
        }
        in.close();
        out.close();
    }
}
