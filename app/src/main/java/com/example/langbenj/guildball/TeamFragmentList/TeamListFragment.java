package com.example.langbenj.guildball.TeamFragmentList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.R;
import com.example.langbenj.guildball.DataAssemblers.Team;


public class TeamListFragment extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {

        View view= fragmentInflater.inflate(R.layout.team_list_recycler_view, target, false);

        RecyclerView rvTeams = (RecyclerView) view.findViewById(R.id.team_recycler);
        // Create adapter passing in the sample user data
        TeamListAdapter adapter = new TeamListAdapter();
        // Attach the adapter to the RecyclerView to populate items
        rvTeams.setAdapter(adapter);

        rvTeams.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }







}
