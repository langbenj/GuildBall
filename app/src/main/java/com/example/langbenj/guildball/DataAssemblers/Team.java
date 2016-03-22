package com.example.langbenj.guildball.DataAssemblers;


import android.content.Context;
import android.util.Log;

import com.example.langbenj.guildball.Helpers.App;

public class Team {


    private String [] mPlayerNameArray;
    private Player[] mTeam;
    private String mTeamName;
    private String TAG = "Team.java";

  public Team(String team_name) {
      //Please reference the App class. This is a workaround to get context outside of the main method
        Context context = App.getContext();
        mTeamName=team_name;
        int team_id = context.getResources().getIdentifier(team_name, "array", context.getPackageName());
        mPlayerNameArray = context.getResources().getStringArray(team_id);

          mTeam = buildTeam(mPlayerNameArray);

    }

    private Player[] buildTeam(String[] parseArray) {
        int array_length= mPlayerNameArray.length;
        Player [] outputArray= new Player[array_length];
        for (int i = 0; i < array_length; i++) {
            outputArray[i] = new Player(parseArray[i]);
        }
        return outputArray;
    }

    public Player[] getTeam() {
        return mTeam;
    }

    public Player getPlayer(int player_index) {
        return mTeam[player_index];
    }

    public Player getPlayer(String player_name) {
        for (Player temp_player : mTeam) {
            if (temp_player.getName().equals(player_name)) {
                return temp_player;
            }
        }
        return null;
    }

    public String[] getPlayerNameArray() {
        return mPlayerNameArray;
    }

    public String getTeamName() {
        return mTeamName;
    }

}
