package uss.versailles.ara.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        System.out.println(view == null);
        views.setMovementMethod(LinkMovementMethod.getInstance());

        if(MainActivity.isRegistred()){
            TextView vs = view.findViewById(R.id.viewweb);
            User u = MainActivity.database.userDao().getUser();
            vs.setText("Hello : " + u.getName() + ". How are you ?\nYour SCC is : " + u.getScc() + "\nYou can modify your vessel in the register page. " + u.getVesselid());
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
