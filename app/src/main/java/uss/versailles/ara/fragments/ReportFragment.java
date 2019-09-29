package uss.versailles.ara.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import fr.colin.arssdk.ARSdk;
import uss.versailles.ara.MainActivity;
import uss.versailles.ara.R;
import uss.versailles.ara.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ReportFragment extends Fragment {

    public static ReportFragment newInstance() {
        ReportFragment.editMode = false;
        return (new ReportFragment());
    }


    public static boolean editMode = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!MainActivity.getStore().isLoggedIn()) {
            Snackbar.make(getView(), "You are not logged, please go to register section", 3000);
            MainActivity.navigationView.getMenu().getItem(1).setChecked(false);
            MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
            MainActivity.showMainFrag(getFragmentManager());
            return;
        }

        new QuerySyncReportTask().execute();
        User u = MainActivity.getStore().getLoggedUser();
        fr.colin.arssdk.objects.User arUser = new fr.colin.arssdk.objects.User(u.getName(), u.getScc(), u.getVesselid(), u.getReport(), u.getUuid());
        EditText textArea = view.findViewById(R.id.tarea);
        Button send = view.findViewById(R.id.sendreport);
        Button editmode = view.findViewById(R.id.editmode);

        editmode.setOnClickListener(v -> {
            User us = MainActivity.getStore().getLoggedUser();
            if (!editMode) {
                ReportFragment.editMode = true;
                textArea.setText(us.getReport());
            } else {
                ReportFragment.editMode = false;
                textArea.setText("");
            }
        });
        send.setOnClickListener(v -> {
            User us = MainActivity.getStore().getLoggedUser();
            send.setEnabled(false);
            //TODO : SEND REPORT
            String report = "";
            System.out.println(editMode);
            if (!editMode) {
                report = us.getReport() + "\n" + textArea.getText().toString();
            } else {
                report = textArea.getText().toString();
            }
            MainActivity.getStore().updateReport(report);
            arUser.setReport(report);
            new SendReport().execute(report);
            Snackbar.make(view, "Your report was sended to the server", 2000).show();
            textArea.setText("");
            ReportFragment.editMode = false;
            send.setEnabled(true);
            MainActivity.navigationView.getMenu().getItem(1).setChecked(false);
            MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
            MainActivity.showMainFrag(getFragmentManager());
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static class QuerySyncReportTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... users) {
            User u = MainActivity.getStore().getLoggedUser();
            fr.colin.arssdk.objects.User arUser = new fr.colin.arssdk.objects.User(u.getName(), u.getScc(), u.getVesselid(), u.getReport(), u.getUuid());
            try {
                String syncReport = ARSdk.DEFAULT_INSTANCE.syncronize(arUser);
                syncReport = syncReport.replace("\\n", "\n");
                System.out.println(syncReport);
                MainActivity.getStore().updateReport(syncReport);
                return syncReport;
            } catch (IOException e) {
                return "nothing to report";
            }
        }
    }

    public static class SendReport extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String report = strings[0];

            MainActivity.getStore().updateReport(report);
            User u = MainActivity.getStore().getLoggedUser();
            fr.colin.arssdk.objects.User arUser = new fr.colin.arssdk.objects.User(u.getName(), u.getScc(), u.getVesselid(), report, u.getUuid());
            try {
                ARSdk.DEFAULT_INSTANCE.postUserReport(arUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
