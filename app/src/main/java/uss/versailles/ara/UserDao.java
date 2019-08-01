package uss.versailles.ara;

import androidx.room.*;


@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    User[] allUsers();


    @Query("SELECT * FROM users WHERE scc = :scc")
    User byScc(String scc);

    @Insert()
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("UPDATE users SET vesselid= :vesselid WHERE scc= :scc")
    void update(String vesselid, String scc);

    @Update
    void update(User user);

    @Query("SELECT * FROM users")
    User getUser();
}
