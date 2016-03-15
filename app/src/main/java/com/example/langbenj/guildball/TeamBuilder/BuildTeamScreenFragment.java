package com.example.langbenj.guildball.TeamBuilder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.DataAssemblers.Team;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.PlayerFragmentList.PlayerListAdapter;
import com.example.langbenj.guildball.R;


public class BuildTeamScreenFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        View view = fragmentInflater.inflate(R.layout.team_construction_page, target, false);
        RecyclerView rvTeams = (RecyclerView) view.findViewById(R.id.player_list_recycler);



        //Accept passed team index of the team to load
        Bundle bundle = this.getArguments();
        int teamIndex = bundle.getInt("TeamIndex", 0);

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


        return view;

    }
}

