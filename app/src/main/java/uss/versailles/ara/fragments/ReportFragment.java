package uss.versailles.ara.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import uss.versailles.ara.MainActivity;
import uss.versailles.ara.R;

import java.io.IOException;

public class ReportFragment extends Fragment {

    public static ReportFragment newInstance() {
        ReportFragment.editMode = false;
        ReportFragment.storedReport = "";
        return (new ReportFragment());
    }


    public static boolean editMode = false;
    public static String storedReport = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (!MainActivity.getStore().isLoggedIn()) {
            Snackbar.make(view, "You are not logged, please go to register section", 3000);
            return;
        }
        /*
        User user = MainActivity.database.userDao().getUser();
        fr.colin.arssdk.objects.User arUser = user.transform();
        EditText textArea = view.findViewById(R.id.tarea);
        Button send = view.findViewById(R.id.sendreport);
        Button editmode = view.findViewById(R.id.editmode);
        try {
            storedReport = MainActivity.SDK.syncronize(arUser).replace("\\n", "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (storedReport.equalsIgnoreCase(""))
            return;
        if (editMode) {
            textArea.setText(storedReport);
        }


        editmode.setOnClickListener(v -> {
            if (!editMode) {
                ReportFragment.editMode = true;
                textArea.setText(storedReport);
            } else {
                ReportFragment.editMode = false;
                textArea.setText("");
            }
        });

        send.setOnClickListener(v -> {
            send.setEnabled(false);
            //TODO : SEND REPORT
            String report = "";
            System.out.println(editMode);
            if (!editMode) {
                report = storedReport + "\n" + textArea.getText().toString();
            } else {
                report = textArea.getText().toString();
            }
            fr.colin.arssdk.objects.User sd = MainActivity.database.userDao().getUser().transform();
            sd.setReport(report);
            try {
                MainActivity.SDK.postUserReport(sd);
                Snackbar.make(view, "Your report was sended to the server", 2000).show();
                textArea.setText("");
                ReportFragment.editMode = false;
                send.setEnabled(true);
                ReportFragment.storedReport = "";
                MainActivity.navigationView.getMenu().getItem(1).setChecked(false);
                MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
                MainActivity.showMainFrag(getFragmentManager());
            } catch (IOException e) {
                Snackbar.make(view, "Error please retry", 2000).show();
            }
        });
*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
