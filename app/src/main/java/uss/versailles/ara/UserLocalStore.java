package uss.versailles.ara;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDB;

    public UserLocalStore(Context co) {
        userLocalDB = co.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEdit = userLocalDB.edit();
        spEdit.putString("name", user.getName());
        spEdit.putString("scc", user.getScc());
        spEdit.putString("username", user.getUsername());
        spEdit.putString("vessel", user.getVesselid());
        spEdit.putString("messengerid", user.getVesselid());
        spEdit.putString("report", user.getReport());
        spEdit.apply();
    }

    public User getLoggedUser() {
        return new User(userLocalDB.getString("name", ""), userLocalDB.getString("username", ""), userLocalDB.getString("report", ""), userLocalDB.getString("vessel", ""), userLocalDB.getString("messengerid", ""), userLocalDB.getString("scc", ""));
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEdit = userLocalDB.edit();
        spEdit.putBoolean("loggedIn", loggedIn);
        spEdit.apply();
    }

    public void clearUser() {
        SharedPreferences.Editor spEdit = userLocalDB.edit();
        spEdit.clear();
        spEdit.apply();
    }

    public boolean isLoggedIn() {
        return userLocalDB.getBoolean("loggedIn", false);
    }

    public SharedPreferences getUserLocalDB() {
        return userLocalDB;
    }
}
