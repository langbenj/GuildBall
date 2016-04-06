package com.example.langbenj.guildball.Helpers;


import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.DataAssemblers.Team;
import com.squareup.otto.Bus;

import java.util.ArrayList;

public class App extends Application {

    private static Application sApplication;

    public static final Bus bus = new Bus();

    public static Application getApplication() {
        return sApplication;
    }

    //Method of pulling root Context without passing through methods pulled from Stack Overflow: http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    private static Team mTeam;
    public static void setTeam(Team mTeam) {
        App.mTeam = mTeam;
    }
    public static Team getTeam() {
        return mTeam;
    }

    private static League mLeague;
    public static void setLeague(League league) {
        App.mLeague = league;
    }
    public static League getLeague() {
        return mLeague;
    }

    public static String getTeamByName(String teamName) {
        Team [] all_teams = mLeague.getTeams();
        for (Team tempTeam:all_teams) {
            if (teamName.equals(tempTeam.getTeamName())) {
                return tempTeam.getTeamName();
            }
        }
        return null;
    }

    public static int getImageViewResourceByName(View view, String aString, String package_name)
    {
        int resId = view.getResources().getIdentifier(aString, "ImageView", package_name);
        return resId;
    }

    private static Player mCurrentPlayer;
    public static Player getCurrentPlayer() {
        return mCurrentPlayer;
    }
    public static void setCurrentPlayer(Player mCurrentPlayer) {
        App.mCurrentPlayer = mCurrentPlayer;
    }

    private static View mCurrentView;
    public static View getCurrentView() {
        return mCurrentView;
    }
    public static void setCurrentView(View current_view) {
        App.mCurrentView = current_view;
    }

    public static String mCurrentSection;
    public static String getCurrentSection() {
        return mCurrentSection;
    }

    public static void setCurrentSection(String mCurrentSection) {
        App.mCurrentSection = mCurrentSection;
    }


    public static ArrayList<String> mTeamCreatePlayerList = new ArrayList<>();

    public static ArrayList<String> getmTeamCreatePlayerList() {
        return mTeamCreatePlayerList;
    }

    public static void setmTeamCreatePlayerList(ArrayList<String> mTeamCreatePlayerList) {
        App.mTeamCreatePlayerList = mTeamCreatePlayerList;
    }

    public static String mCurrentTeam;

    public static String getCurrentTeam() {
        return mCurrentTeam;
    }

    public static void setCurrentTeam(String team_name) {
        App.mCurrentTeam = team_name;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}


