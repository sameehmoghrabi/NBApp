package sameeh.com.nbapp.Models.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by samee on 4/1/2018.
 */

@Entity()
public class PlayerDAO {

    @Id
    private String id;
    @NotNull
    private String fname;
    @NotNull    private String lname;
    @NotNull    private String age;
    @NotNull    private String playerID;

    @Generated(hash = 1616153167)
    public PlayerDAO(String id, @NotNull String fname, @NotNull String lname, @NotNull String age, @NotNull String playerID) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.playerID = playerID;
    }

    @Generated(hash = 1794758161)
    public PlayerDAO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
