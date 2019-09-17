package uss.versailles.ara.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import fr.colin.arssdk.ARSdk;
import uss.versailles.ara.MainActivity;
import uss.versailles.ara.R;
import uss.versailles.ara.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    public static LoginFragment newInstance() {
        return (new LoginFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (MainActivity.getStore().isLoggedIn()) {
            Snackbar.make(view, "You are already log in, please log out if you want to change account", 3000).show();
            MainActivity.navigationView.getMenu().getItem(2).setChecked(false);
            MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
            MainActivity.showMainFrag(getFragmentManager());
            return;
        }

        TextView usnam = view.findViewById(R.id.username);
        TextView password = view.findViewById(R.id.password);
        Button send = view.findViewById(R.id.loginbutton);


        send.setOnClickListener(v -> {
            try {
                String[] reports = new LoginTask().execute(usnam.getText().toString(), password.getText().toString()).get();
                boolean b = Boolean.parseBoolean(reports[0]);
                String message = reports[1];

                if (b) {
                    //the message contains the user
                    String[] m = message.split(Pattern.quote("}_}"));
                    if (m.length < 5) {
                        message = "Error";
                        Snackbar.make(view, message, 5000).show();
                        return;
                    }
                    String username = m[0];
                    String scc = m[1];
                    String vessel = m[2];
                    String name = m[3];
                    String messengerid = m[4];
                    String uuid = m[5];
                    User user = new User(name, username, " ", vessel, messengerid, scc, uuid);
                    MainActivity.getStore().setUserLoggedIn(true);
                    MainActivity.getStore().storeUserData(user);
                    Snackbar.make(view, "You are successfully login " + name + " ! ", 3000).show();
                    MainActivity.navigationView.getMenu().getItem(2).setChecked(false);
                    MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
                    MainActivity.showMainFrag(getFragmentManager());

                    //REGISTER USER
                } else {
                    Snackbar.make(view, message, 5000).show();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

      /*  if(MainActivity.isRegistred()){
            TextView vs = view.findViewById(R.id.viewweb);
            User u = MainActivity.database.userDao().getUser();
            vs.setText("Hello : " + u.getName() + ". How are you ?\nYour SCC is : " + u.getScc() + "\nYou can modify your vessel in the register page. " + u.getVesselid());
        }*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static class LoginTask extends AsyncTask<String, String, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length < 2) {
                return new String[]{"false", "Require two arguments"};
            }
            try {

                return ARSdk.DEFAULT_INSTANCE.loginUser(strings[0], strings[1]);
            } catch (IOException e) {
                e.printStackTrace();
                return new String[]{"false", "Error, please retry"};

            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
