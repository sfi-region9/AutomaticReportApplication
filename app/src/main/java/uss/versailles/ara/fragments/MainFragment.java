package uss.versailles.ara.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import uss.versailles.ara.MainActivity;
import uss.versailles.ara.R;
import uss.versailles.ara.User;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return (new MainFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView views = view.findViewById(R.id.viewmain);
        views.setMovementMethod(LinkMovementMethod.getInstance());
        TextView vs = view.findViewById(R.id.viewweb);
        Button button = view.findViewById(R.id.logout);

        if (MainActivity.getStore().isLoggedIn()) {
            User u = MainActivity.getStore().getLoggedUser();
            vs.setText("Hello : " + u.getName() + " How are you today ?\nYour SCC is : " + u.getScc() + "\nYou can modify your vessel in the register page : " + u.getVesselid() + "\nThe account is linked to the ARW database you can login here : https://reports.nwa2coco.fr if you prefer the web version");
            button.getBackground().setAlpha(100);
            button.setText("Log Out");
            button.setEnabled(true);
        } else {
            vs.setText("Hey ! You are not login or register yet, please login or register, you should use the same account as in ARW ( https://reports.nwa2coco.fr ) !");
            button.getBackground().setAlpha(0);
            button.setText("");
            button.setEnabled(false);
        }

        button.setOnClickListener(v -> {
            MainActivity.getStore().clearUser();
            MainActivity.getStore().setUserLoggedIn(false);
            Snackbar.make(view, "You are log out.", 3000).show();
            vs.setText("Hey ! You are not login or register yet, please login or register, you should use the same account as in ARW ( https://reports.nwa2coco.fr ) !");
            button.getBackground().setAlpha(0);
            button.setText("");
            button.setEnabled(false);
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
}
