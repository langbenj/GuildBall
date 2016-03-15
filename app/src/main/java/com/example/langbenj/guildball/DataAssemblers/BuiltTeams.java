package com.example.langbenj.guildball.DataAssemblers;

import java.util.ArrayList;

public class BuiltTeams {


    private String mTeamName;
    private String mGuild;
    private Player[] mPlayers;
    private ArrayList<String> mPlayerNames = new ArrayList<String>();


    public BuiltTeams(String team_name, String guild, Player[] players) {
        mTeamName = team_name;
        mGuild = guild;
        mPlayers = players;
        for (Player player : mPlayers) {
            mPlayerNames.add(player.getTeam());
        }
    }

    public void SaveTeam() {
        //Write team to DB once ready
    }


    public ArrayList<String> getPlayerNames() {
        return mPlayerNames;
    }

    public void setPlayerNames(ArrayList<String> mPlayerNames) {
        this.mPlayerNames = mPlayerNames;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public String getGuild() {
        return mGuild;
    }

    public void setGuild(String mGuild) {
        this.mGuild = mGuild;
    }

    public Player[] getPlayers() {
        return mPlayers;
    }

    public void setPlayers(Player[] mPlayers) {
        this.mPlayers = mPlayers;
    }

}

