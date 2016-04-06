package com.example.langbenj.guildball.TeamBuilder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.DataAssemblers.Team;
import com.example.langbenj.guildball.Helpers.AddRemovePlayerBusEvent;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.StringFragmentBusEvent;
import com.example.langbenj.guildball.PlayerFragmentList.PlayerListAdapter;
import com.example.langbenj.guildball.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class BuildTeamScreenFragment extends Fragment {

    private ViewGroup mCurrentViewGroup;
    private View mCurrentView;
    private static String TAG = "BuildTeamScreenFragment";
    private ArrayList<String> mPlayerList = new ArrayList<>();
    private ArrayList<String> mPlayerButtons = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        View view = fragmentInflater.inflate(R.layout.team_construction_page, target, false);
        mCurrentView = view;
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
        App.setCurrentTeam(temp_team_to_create.getTeamName());
        Context context = App.getContext();
        int image_id = context.getResources().getIdentifier(logo_image, "drawable", context.getPackageName());
        image_view.setImageResource(image_id);

        if (mPlayerButtons.size()==0) { //Only init the array if it's new
            for (int i = 0; i < 14; i++) {
                mPlayerButtons.add("ic_add_box_black_48dp");
            }
        }

        Button add_button_1 = (Button) view.findViewById(R.id.team_player1_image);
        add_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(0) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(0, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else {
                    mPlayerButtons.set(0, "ic_add_box_black_48dp");
                }
                updatePlayerTextFields();
            }
        });

        Button add_button_2 = (Button) view.findViewById(R.id.team_player2_image);
        add_button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(1) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(1, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else {
                    mPlayerButtons.set(1, "ic_add_box_black_48dp");
                }
                updatePlayerTextFields();
            }
        });


        return view;

    }

    public void onStart() {
        super.onStart();
        updatePlayerTextFields();

    }


    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void addOrRemovePlayer (AddRemovePlayerBusEvent event) {
        String[] passed_string_array = event.getParameter();
        String player_name = passed_string_array[0];
        String todo = passed_string_array[1];
        Player target_player = getPlayerInfo(player_name);
        String player_display_name = "";

        switch (todo) {
            case "add":
                switch (target_player.getType()) {
                    case "Captain":

                        if (mPlayerList.size() < 10) {
                            player_display_name = target_player.getName();
                            mPlayerList.add(player_display_name);
                        }
                        break;
                    case "Mascot":
                        if (mPlayerList.size() < 10) {
                            player_display_name = target_player.getName();
                            mPlayerList.add(player_display_name);
                        }
                        break;
                    case "":
                        if (mPlayerList.size() < 10) {
                            player_display_name = target_player.getName();
                            mPlayerList.add(player_display_name);
                        }
                        break;
                }
                break;
            case "remove":
                mPlayerList.remove(target_player.getName());
                break;
        }

        Log.d(TAG, target_player.getType()+" "+player_display_name + " "+todo);

                updatePlayerTextFields();

        }



    private void updatePlayerTextFields() {
        Context context = App.getContext();
        Button image_view = (Button) mCurrentView.findViewById(R.id.team_player1_image);
        int image_id = context.getResources().getIdentifier(mPlayerButtons.get(0), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);

        image_view = (Button) mCurrentView.findViewById(R.id.team_player2_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(1), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player3_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(2), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player4_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(3), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player5_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(4), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player6_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(5), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player7_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(6), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player8_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(7), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player9_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(8), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);


        image_view = (Button) mCurrentView.findViewById(R.id.team_player10_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(9), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);

        image_view = (Button) mCurrentView.findViewById(R.id.team_player11_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(10), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);

        image_view = (Button) mCurrentView.findViewById(R.id.team_player12_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(11), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);

        image_view = (Button) mCurrentView.findViewById(R.id.team_player13_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(12), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);

        image_view = (Button) mCurrentView.findViewById(R.id.team_player14_image);
        image_id = context.getResources().getIdentifier(mPlayerButtons.get(13), "drawable", context.getPackageName());
        image_view.setBackgroundResource(image_id);
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

