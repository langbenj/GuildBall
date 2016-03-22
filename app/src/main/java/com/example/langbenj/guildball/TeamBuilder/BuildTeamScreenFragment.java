package com.example.langbenj.guildball.TeamBuilder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.DataAssemblers.Team;
import com.example.langbenj.guildball.Helpers.AddRemovePlayerBusEvent;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.PlayerFragmentList.PlayerListAdapter;
import com.example.langbenj.guildball.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class BuildTeamScreenFragment extends Fragment {

    private ViewGroup mCurrentViewGroup;
    private static String TAG = "BuildTeamScreenFragment";
    private String mCaptain="";
    private String mMascot="";
    private ArrayList<String> mPlayerList = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        View view = fragmentInflater.inflate(R.layout.team_construction_page, target, false);
        RecyclerView rvTeams = (RecyclerView) view.findViewById(R.id.player_list_recycler);

        mCurrentViewGroup=target;

        //Accept passed team index of the team to load
        Bundle bundle = this.getArguments();
        int teamIndex = bundle.getInt("TeamIndex", 0);


        //TODO Figure out how to determine if the object is already registered with OTTO
        try
        {
            App.bus.register(this);
        }
        catch(Exception e)
        {
            Log.d(TAG, "Problem with registration of OTTO bus: " + e.getMessage());
        }




        //Pull in the League and using the index find the proper team
        League temp_league=App.getLeague();
        Team[] temp_team=temp_league.getTeams();
        Team temp_team_to_create = temp_team[teamIndex];

        //Add the team logo to the upper right of the page
        ImageView image_view = (ImageView) view.findViewById(R.id.team_construction_logo_image);
        String logo_image = (temp_team_to_create.getTeamName()+"_logo").toLowerCase();
        Context context = App.getContext();
        int image_id = context.getResources().getIdentifier(logo_image, "drawable", context.getPackageName());
        image_view.setImageResource(image_id);

        // Create adapter passing in the sample user data
        PlayerListAdapter adapter = new PlayerListAdapter(temp_team_to_create.getTeam());
        // Attach the adapter to the RecyclerView to populate items
        rvTeams.setAdapter(adapter);
        rvTeams.setLayoutManager(new LinearLayoutManager(getActivity()));



        //TODO this isn't working, it crashes. This is needed to update the list info when the user clicks from a player info page
        //updatePlayerTextFields();
        return view;

    }

    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void addOrRemovePlayer (AddRemovePlayerBusEvent event) {
        String [] passed_string_array=event.getParameter();
        String player_name = passed_string_array[0];
        String todo = passed_string_array[1];
        Player target_player = getPlayerInfo(player_name);

        switch (todo) {
            case "add":
                switch (target_player.getType()) {
                    case "Captain":
                        mCaptain=target_player.getName();
                        break;
                    case "Mascot":
                        mMascot=target_player.getName();
                        break;

                    case "":
                        if (mPlayerList.size()<6) {
                            mPlayerList.add(target_player.getName());
                        }
                        break;
                }
                break;
            case "remove":
                switch (target_player.getType()) {
                    case "Captain":
                        mCaptain="";
                        break;
                    case "Mascot":
                        mMascot="";
                        break;

                    case "":
                        mPlayerList.remove(target_player.getName());
                        break;
                }
                break;
        }

        updatePlayerTextFields();

    }

    private void updatePlayerTextFields() {
        Context context = App.getContext();

        //Set Captain text field
        int field_id= context.getResources().getIdentifier("team_builder_captain", "id", context.getPackageName());
        TextView target_view= (TextView) mCurrentViewGroup.findViewById(field_id);
        target_view.setText(mCaptain);

        //Set Mascot text field
        field_id=context.getResources().getIdentifier("team_builder_mascot", "id", context.getPackageName());
        target_view= (TextView) mCurrentViewGroup.findViewById(field_id);
        target_view.setText(mMascot);

        //Set player fields 1-6
        //TODO change the amount of fields to allow different size lists.
        for (int x=0; x<mPlayerList.size(); x++) {
            field_id=context.getResources().getIdentifier("team_builder_player"+(x+1), "id", context.getPackageName());
            target_view= (TextView) mCurrentViewGroup.findViewById(field_id);
            target_view.setText(mPlayerList.get(x));
        }
        for (int y=mPlayerList.size(); y<6; y++) {
            field_id=context.getResources().getIdentifier("team_builder_player"+(y+1), "id", context.getPackageName());
            target_view= (TextView) mCurrentViewGroup.findViewById(field_id);
            target_view.setText("");
        }
    }

    private Player getPlayerInfo (String player_name) {
        //In the team section this handler is called when a new team is being built and a guild is chosen. It should open the team builder screen
        League temp_league= App.getLeague();
        String [] team_list=temp_league.getTeamNameArray();
        Player return_player;
        Team temp_team;
        for (int x=0; x<team_list.length; x++) {
            temp_team=temp_league.getTeam(team_list[x]);
            return_player=temp_team.getPlayer(player_name);
            if (return_player != null) {
                return return_player;
            }
        }
        return null;

    }
}

