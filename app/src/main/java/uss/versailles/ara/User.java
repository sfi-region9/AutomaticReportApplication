package uss.versailles.ara;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    private String name;


    @ColumnInfo(name = "scc")
    private String scc;

    @ColumnInfo(name = "report")
    private String report;

    @ColumnInfo(name = "vesselid")
    private String vesselid;

    public String getScc() {
        return scc;
    }

    public String getVesselid() {
        return vesselid;
    }

    public String getReport() {
        return report;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setVesselid(String vesselid) {
        this.vesselid = vesselid;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public fr.colin.arssdk.objects.User transform() {
        return new fr.colin.arssdk.objects.User(name, scc, vesselid, report);
    }

    public static User from(fr.colin.arssdk.objects.User user){
        return new User(user.getName(), user.getScc(), user.getReport(), user.getVesselid());
    }

    public User(String name, String scc, String report, String vesselid) {
        this.name = name;
        this.scc = scc;
        this.report = report;
        this.vesselid = vesselid;
    }

}
