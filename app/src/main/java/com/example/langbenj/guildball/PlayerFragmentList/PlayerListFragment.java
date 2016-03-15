package com.example.langbenj.guildball.PlayerFragmentList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.R;
import com.example.langbenj.guildball.DataAssemblers.Team;




public class PlayerListFragment extends Fragment  {

    public static String TEAM_NAME;

    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {

        View view= fragmentInflater.inflate(R.layout.player_list_recycler_view, target, false);
        RecyclerView rvTeams = (RecyclerView) view.findViewById(R.id.player_recycler);

        //Accept passed team index of the team to load
        Bundle bundle = this.getArguments();
        int teamIndex = bundle.getInt("TeamIndex", 0);
        //Pull in the League and using the index find the proper team
        League temp_league=App.getLeague();
        Team [] temp_team=temp_league.getTeams();
        Team temp_team_to_create = temp_team[teamIndex];

        // Create adapter passing in the sample user data
        PlayerListAdapter adapter = new PlayerListAdapter(temp_team_to_create.getTeam());
        // Attach the adapter to the RecyclerView to populate items
        rvTeams.setAdapter(adapter);

        rvTeams.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }







}
