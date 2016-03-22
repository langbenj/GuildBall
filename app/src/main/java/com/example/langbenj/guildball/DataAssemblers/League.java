package com.example.langbenj.guildball.DataAssemblers;

import android.content.Context;

import com.example.langbenj.guildball.Helpers.App;

public class League {
    private static League sLeague;
    private Team[] mLeague;


    private String [] mTeamNameArray;


    public League(){
        //Need to shift this to looping through the array of teams TODO
        Context context = App.getContext();
        int teams_id = context.getResources().getIdentifier("TeamList", "array", context.getPackageName());
        mTeamNameArray = context.getResources().getStringArray(teams_id);
        mLeague = buildLeague(mTeamNameArray);
    }

    public Team[] buildLeague(String [] passedTeamList) {
        int array_length= passedTeamList.length;
        Team [] outputArray= new Team[array_length];
        for (int i = 0; i < array_length; i++) {
            Team tempTeam=new Team(passedTeamList[i]);
            outputArray[i] = new Team(passedTeamList[i]);
        }
        return outputArray;
    }

    public Team[] getTeams() {
        return mLeague;
    }

   public Team getTeam(String team_name) {
        for (Team team : mLeague) {
            Player[] temp_team_check=team.getTeam();
            if (temp_team_check[0].getTeam().equals(team_name)) {
                return team;
            }
        }
           return null;
        }

    public String[] getTeamNameArray() {
        return mTeamNameArray;
    }
}
