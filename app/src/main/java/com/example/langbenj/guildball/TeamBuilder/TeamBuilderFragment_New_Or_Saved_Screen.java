package com.example.langbenj.guildball.TeamBuilder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.StringFragmentBusEvent;
import com.example.langbenj.guildball.Helpers.TeamBuilderListFragmentBusEvent;
import com.example.langbenj.guildball.R;


public class TeamBuilderFragment_New_Or_Saved_Screen extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {

        View view= fragmentInflater.inflate(R.layout.team_builder_recycler_view, target, false);

        RecyclerView rvTeamBuilder = (RecyclerView) view.findViewById(R.id.team_builder_recycler);

        TeamBuilderAdapter_New_Or_Saved_Screen adapter = new TeamBuilderAdapter_New_Or_Saved_Screen();
        // Attach the adapter to the RecyclerView to populate items
        rvTeamBuilder.setAdapter(adapter);

        rvTeamBuilder.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button guild_info_button = (Button) view.findViewById(R.id.create_new_team_button);
        guild_info_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new StringFragmentBusEvent("new_team_button"));
            }
        });

        return view;
    }







}
