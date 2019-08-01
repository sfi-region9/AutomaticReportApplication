package uss.versailles.ara.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import fr.colin.arssdk.objects.User;
import fr.colin.arssdk.objects.Vessel;
import uss.versailles.ara.MainActivity;
import uss.versailles.ara.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RegisterFragment extends Fragment {

    public static RegisterFragment newInstance() {
        return (new RegisterFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = view.findViewById(R.id.spinnervessel);


        ArrayList<String> vessel = new ArrayList<>();
        for (Vessel v : MainActivity.allVessels) {
            vessel.add(v.getName().replace("_", " "));
        }
        ArrayAdapter<String> spinerArray = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, vessel);
        spinerArray.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinerArray);

        Button send = view.findViewById(R.id.send);
        EditText name = view.findViewById(R.id.name);
        EditText scc = view.findViewById(R.id.scc);


        HashMap<String, Integer> vessels = new HashMap<>();
        for (int i = 0; i < vessel.size(); i++) {
            vessels.put(MainActivity.allVessels.get(i).getVesselid(), i);
        }

        if (MainActivity.isRegistred()) {
            name.setEnabled(false);
            scc.setEnabled(false);
            uss.versailles.ara.User u = MainActivity.database.userDao().getUser();
            name.setText(u.getName());
            scc.setText(u.getScc());
            spinner.setSelection(vessels.get(u.getVesselid()));
        }

        send.setOnClickListener(v -> {
            String names = name.getText().toString();
            String scs = scc.getText().toString();
            if (names.length() == 0) {
                Snackbar.make(view, "Your name is empty", 3000).show();
                return;
            }
            if (scs.length() == 0) {
                Snackbar.make(view, "Your scc is empty", 3000).show();
                return;
            }
            if (spinner.getSelectedItem().toString().equalsIgnoreCase("")) {
                Snackbar.make(view, "Your vessel is empty", 3000).show();
                return;
            }
            if (!MainActivity.isRegistred()) {
                Vessel ve = null;
                for (Vessel vsd : MainActivity.allVessels) {
                    if (vsd.getName().equalsIgnoreCase(spinner.getSelectedItem().toString().replace(" ", "_"))) {
                        ve = vsd;
                        break;
                    }
                }
                if (ve == null)
                    return;

                User u = new User(names, scs, ve.getVesselid(), ve.getDefaul());
                try {
                    MainActivity.SDK.registerUser(u);
                    uss.versailles.ara.User us = new uss.versailles.ara.User(names, scs, ve.getDefaul(), ve.getVesselid());
                    MainActivity.database.userDao().insertAll(us);
                    Snackbar.make(view, "You are now logged, Welcome " + names, 3000).show();
                    name.setEnabled(false);
                    scc.setEnabled(false);
                    MainActivity.navigationView.getMenu().getItem(2).setChecked(false);
                    MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
                    MainActivity.showMainFrag(getFragmentManager());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Vessel ve = null;
                for (Vessel vsd : MainActivity.allVessels) {
                    if (vsd.getName().equalsIgnoreCase(spinner.getSelectedItem().toString().replace(" ", "_"))) {
                        ve = vsd;
                        break;
                    }
                }
                if (ve == null)
                    return;
                User u = MainActivity.database.userDao().getUser().transform();
                uss.versailles.ara.User s = uss.versailles.ara.User.from(u);


                s.setVesselid(ve.getVesselid());


                try {
                    MainActivity.database.userDao().update(ve.getVesselid(), s.getScc());


                    MainActivity.SDK.switchVessel(u, ve.getVesselid());


                    Snackbar.make(view, "You switched to the " + ve.getName(), 3000).show();
                    MainActivity.navigationView.getMenu().getItem(2).setChecked(false);
                    MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
                    MainActivity.showMainFrag(getFragmentManager());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });


    }
}
