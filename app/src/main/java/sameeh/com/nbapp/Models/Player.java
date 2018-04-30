package sameeh.com.nbapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by samee on 4/30/2018.
 */

public class Player  implements Serializable{

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("status")
    private String status;

    @SerializedName("playerID")
    private String playerID;

 //   private String imageURL ;

    public Player(String firstName, String lastName, String status, String playerID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.playerID = playerID;
   //     imageURL = "https://nba-players.herokuapp.com/players/"+lastName+"/"+firstName;
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

  //  public String getImageURL() {
   //     return imageURL;
   // }
}
