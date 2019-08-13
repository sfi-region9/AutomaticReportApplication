package uss.versailles.ara;

import android.util.Log;
import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.colin.arssdk.ARSdk;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketRegisterAndLoginCommunication {

    private String link;
    private int port;
    private Socket client;

    public SocketRegisterAndLoginCommunication(String link, int port) throws IOException {
        this.link = link;
        this.port = port;
        this.client = new Socket(link, port);
    }

    public String[] registerUser(String name, String username, String password, String vaisseau, String email, String scc) throws IOException {

        password = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(10, password.toCharArray());

        String[] str = new String[]{"REG", name, username, password, vaisseau, email, scc};
        String message  = StringUtils.join(str, "}_}");
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintStream writer = new PrintStream(client.getOutputStream());
        writer.println(message);
        String lig = reader.readLine();
        Log.e("Register process : ", lig);
        if(lig.contains("Error while register, ")){
            return new String[]{"false", lig};
        }
        return new String[]{"true", lig};
    }

    public String[] loginUser(String username, String password) throws IOException {
        String[] str = new String[]{"LOG", username, password};
        String messsage = StringUtils.join(str, "}_}");
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintStream writer = new PrintStream(client.getOutputStream());
        writer.println(messsage);
        String lig = reader.readLine();
        Log.e("Registration process : ", lig);
        if(lig.contains("Error while login, ")){
            return new String[]{"false", lig};
        }
        return new String[]{"true", lig};
    }

    public int getPort() {
        return port;
    }

    public Socket getClient() {
        return client;
    }

    public String getLink() {
        return link;
    }
}
