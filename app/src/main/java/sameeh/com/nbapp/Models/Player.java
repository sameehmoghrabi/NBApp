package sameeh.com.nbapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by samee on 4/30/2018.
 */

public class Player  implements Serializable{

    @SerializedName("_id")
    private String id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("status")
    private String status;

    @SerializedName("playerId")
    private String playerID;

    public Player(String firstName, String lastName, String status, String playerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.playerID = playerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getimageURL() {return "https://nba-players.herokuapp.com/players/"+lastName+"/"+firstName; }

    public String getId() { return id; }

    @Override
    public String toString() {
        return "Player{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", playerID='" + playerID + '\'' +
                '}';
    }
}
