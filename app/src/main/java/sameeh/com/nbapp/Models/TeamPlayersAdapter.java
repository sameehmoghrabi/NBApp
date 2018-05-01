package sameeh.com.nbapp.Models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sameeh.com.nbapp.R;

/**
 * Created by samee on 4/30/2018.
 */

public class TeamPlayersAdapter extends RecyclerView.Adapter<TeamPlayersAdapter.ViewHolder>{
    private List<Player> teamPlayers = new ArrayList<>();
    public  TeamPlayersAdapter(List<Player> teamPlayers){this.teamPlayers = teamPlayers;}
    @Override
    public TeamPlayersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(TeamPlayersAdapter.ViewHolder holder, int position) {

        Player player = teamPlayers.get(position);
        Context context = holder.itemView.getContext();

        holder.firstName.setText(player.getFirstName());
        holder.lastName.setText(player.getLastName());
        holder.status.setText(player.getStatus());
        holder.playerID.setText(player.getPlayerID());

        String imageURL = player.getimageURL();

        Picasso.get().load(imageURL).into(holder.poster);

        if(holder.poster.getDrawable() == null)
            holder.poster.setImageResource(R.drawable.unknown_player);

    }

    @Override
    public int getItemCount() {
        return teamPlayers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView firstName;
        TextView lastName;
        TextView status;
        TextView playerID;


        public ViewHolder(View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            status = itemView.findViewById(R.id.status);
            playerID = itemView.findViewById(R.id.playerID);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
