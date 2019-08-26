package uss.versailles.ara;

public class User {

    private String name;
    private String username;
    private String report;
    private String vesselid;
    private String messengerid;
    private String scc;
    private String uuid;


    public User(String name, String username, String report, String vesselid, String messengerid, String scc, String uuid) {
        this.name = name;
        this.username = username;
        this.report = report;
        this.vesselid = vesselid;
        this.messengerid = messengerid;
        this.scc = scc;
        this.uuid = uuid;
    }

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getMessengerid() {
        return messengerid;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getReport() {
        return report;
    }

    public String getUsername() {
        return username;
    }


    public String getVesselid() {
        return vesselid;
    }

    public String getScc() {
        return scc;
    }
}
