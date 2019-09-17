package uss.versailles.ara;

import android.util.Log;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class RegisterLoginCommunication {

    private String link;
    private int port;

    public RegisterLoginCommunication(String link, int port) throws IOException {
        this.link = link;
        this.port = port;
    }

    public String[] registerUser(String name, String username, String password, String vaisseau, String email, String scc) throws IOException {

        //  password = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(10, password.toCharArray());

        Register register = new Register(name, username, password, vaisseau, email, scc);
        OkHttpClient o = new OkHttpClient();
        Request r = new Request.Builder().url("https://auth.nwa2coco.fr/register").post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(register))).build();

        String lig = o.newCall(r).execute().body().string();
        if (lig.contains("Error while register, ")) {
            return new String[]{"false", lig};
        }
        return new String[]{"true", lig};
    }

    public String[] loginUser(String username, String password) throws IOException {

        Login login = new Login(username, password);
        OkHttpClient o = new OkHttpClient();
        Request r = new Request.Builder().url("https://auth.nwa2coco.fr/login").post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(login))).build();
        String lig = o.newCall(r).execute().body().string();

        if (lig.contains("Error while login, ")) {
            return new String[]{"false", lig};
        }
        return new String[]{"true", lig};
    }

    public int getPort() {
        return port;
    }


    public String getLink() {
        return link;
    }
}
