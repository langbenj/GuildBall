package com.example.langbenj.guildball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.StringFragmentBusEvent;



public class Start_Menu extends Fragment {



        @Override
        public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {

            View view = fragmentInflater.inflate(R.layout.application_start_menu, target, false);


            Button guild_info_button = (Button) view.findViewById(R.id.guild_info_button);
            guild_info_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String passed_button="guild_info";
                    App.bus.post(new StringFragmentBusEvent(passed_button));
                }
            });

            Button team_button = (Button) view.findViewById(R.id.team_builder_button);
            team_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String passed_button="team_builder";
                    App.bus.post(new StringFragmentBusEvent(passed_button));
                }
            });

            Button odds_button = (Button) view.findViewById(R.id.odds_button);
            odds_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String passed_button="odds_button";
                    App.bus.post(new StringFragmentBusEvent(passed_button));
                }
            });


            return view;

        }
    }

