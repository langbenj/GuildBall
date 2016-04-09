package com.example.langbenj.guildball.PlayerFragmentList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langbenj.guildball.Helpers.AddRemovePlayerBusEvent;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.PlayerListFragmentBusEvent;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.R;

import java.util.ArrayList;

//This class is used whenever a recycler list of players on a team are needed. It pulls the players information and displays it in a scrollable list
//Some functions of this will only work if a paticular section that is set in App.getCurrentSection()
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder>{

    private Player[] mTeam;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mPlayerNameField;
        public TextView mMovField;
        public TextView mTacField;
        public TextView mKickField;
        public TextView mDefField;
        public TextView mArmField;
        public TextView mInfField;
        public ImageView mImageNameField;
        public boolean mNotOnTeam=true;
        public String current_section = App.getCurrentSection();

        public ViewHolder(View itemView) {
            super(itemView);
            //Assign references to the TextViews in the XML layout. These will be used later to write info to the fields
            mPlayerNameField = (TextView) itemView.findViewById(R.id.player_name_field);
            mMovField = (TextView) itemView.findViewById(R.id.item_move);
            mTacField = (TextView) itemView.findViewById(R.id.item_tac);
            mKickField = (TextView) itemView.findViewById(R.id.item_kick);
            mDefField = (TextView) itemView.findViewById(R.id.item_def);
            mArmField = (TextView) itemView.findViewById(R.id.item_arm);
            mInfField = (TextView) itemView.findViewById(R.id.item_inf);
            mImageNameField = (ImageView) itemView.findViewById(R.id.player_list_logo_image);

            //Setup the onClickListener to handle click events on each view (individual row)
            itemView.setOnClickListener(this);

            //Setup the onClickListener for the add/remove button that is on the team builder list
            if (current_section.equals("lists")) {
                ImageButton image_button = (ImageButton) itemView.findViewById(R.id.add_player_to_team_button);
                image_button.setImageResource(R.drawable.ic_add_circle_outline_black_36dp);
                image_button.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {


            String passed_player= (String) mPlayerNameField.getText();
            App.setCurrentPlayer(null);
            App.bus.post(new PlayerListFragmentBusEvent(passed_player));

        }
    }

    public PlayerListAdapter(Player[] team) {
        //Initialize the adapter
        //Registers this class into the Otto Library's bus
        App.bus.register(this);
        mTeam=team;
    }

    @Override
    public PlayerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //The purpose of this function is to select a XML file for the fragment and inflate it
        String current_section = App.getCurrentSection();
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View playerView;
        playerView =inflater.inflate(R.layout.player_list_item, parent, false);
        return new ViewHolder(playerView);
    }


    @Override
    public void onBindViewHolder(PlayerListAdapter.ViewHolder viewHolder, int position) {
        //Initial setup for the Recycler View
        Context context = App.getContext();
        String current_section = App.getCurrentSection();

        // Get the player info based on the current position
        Player player= mTeam[position];
        String team_name=player.getTeam();

        //Set the text for the view
        TextView textView = viewHolder.mPlayerNameField;
        textView.setText(player.getName());

        //Pull the logo image view from the viewHolder
        ImageView image_view = viewHolder.mImageNameField;
        //build the string name of the team logo
        String logo_image = (team_name + "_logo").toLowerCase();
        //Pull the image id and set it to the View
        int image_id = context.getResources().getIdentifier(logo_image, "drawable", context.getPackageName());
        image_view.setImageResource(image_id);


        if (current_section.equals("guilds")) {
            // These are only activated if you are in the guild list they display the player's stats
            // Set text fields based on the generated player info
            textView = viewHolder.mMovField;
            String movement = player.getJog() + "/" + player.getSprint();
            textView.setText(movement);
            textView = viewHolder.mTacField;
            textView.setText(player.getTac());
            String kick_string = player.getKickDice() + "/" + player.getKickDistance();
            textView = viewHolder.mKickField;
            textView.setText(kick_string);
            textView = viewHolder.mDefField;
            textView.setText(player.getDefense());
            textView = viewHolder.mArmField;
            textView.setText(player.getArmor());
            String inf_string = player.getInfluenceGenerated() + "/" + player.getMaxInfluence();
            textView = viewHolder.mInfField;
            textView.setText(inf_string);
        }
    }

    @Override
    public int getItemCount() {
        //This required function determines how long the Recycler List is.
        return mTeam.length;
    }
}
