package com.example.langbenj.guildball;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.LoadSavedTeamBusEvent;
import com.example.langbenj.guildball.Helpers.StringArrayFragmentBusEvent;
import com.example.langbenj.guildball.Helpers.StringFragmentBusEvent;
import com.example.langbenj.guildball.Helpers.TeamListFragmentBusEvent;
import com.example.langbenj.guildball.Helpers.PlayerListFragmentBusEvent;
import com.example.langbenj.guildball.PlayerFragmentList.PlayerListFragment;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.example.langbenj.guildball.PlayerInfo.PlayerInfoFragment;
import com.example.langbenj.guildball.TeamBuilder.BuildTeamScreenFragment;
import com.example.langbenj.guildball.TeamBuilder.TeamBuilderFragment_New_Or_Saved_Screen;
import com.example.langbenj.guildball.TeamFragmentList.TeamListFragment;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Initialize the League containing all of the teams, players and plays
        loadLeague();
        //Register on the OTTO bus
        App.bus.register(this);
        //Start the fragment that contains the home page
        launchStartMenuFragment();
    }




    //Side navigation code from standard google library with a few small changes
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_guilds) {
            App.setCurrentSection("guilds");
            launchTeamListFragment();

        } else if (id == R.id.nav_lists) {
            App.setCurrentSection("lists");
            TeamBuilderFragment_New_Or_Saved_Screen teambuilder_fragment = new TeamBuilderFragment_New_Or_Saved_Screen();
            FragmentTransaction teambuilder_transaction = getSupportFragmentManager().beginTransaction();
            teambuilder_transaction.replace(R.id.fragment_container, teambuilder_fragment);
            teambuilder_transaction.addToBackStack(null);
            teambuilder_transaction.commit();

        } else if (id == R.id.nav_play) {
            App.setCurrentSection("play");
            Toast.makeText(App.getContext(), "Play - Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_models) {
            App.setCurrentSection("models");
            Toast.makeText(App.getContext(), "Models - Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_stats) {
            App.setCurrentSection("stats");
            Toast.makeText(App.getContext(), "Stats - Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            App.setCurrentSection("share");
            Toast.makeText(App.getContext(), "Share - Coming Soon", Toast.LENGTH_SHORT).show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //TODO These launchers can be combined into one refactoring is definitely needed.


    public void launchTeamListFragment() {
        //Check to see if there is a fragment in the container and remove it if there is
        Fragment to_release = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (to_release!=null) {
            getSupportFragmentManager().beginTransaction().remove(to_release).commit();
        }
        // Create a new Fragment to be placed in the activity layout
        TeamListFragment team_list_fragment = new TeamListFragment();
        // Add the fragment to the 'team_list_container' FrameLayout
        String VIEW_TAG="team_navigator";
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, team_list_fragment, VIEW_TAG).commit();
    }


    public void launchStartMenuFragment() {
        //Check to see if there is a fragment in the container and remove it if there is
        Fragment to_release = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (to_release!=null) {
            getSupportFragmentManager().beginTransaction().remove(to_release).commit();
        }
        // Create a new Fragment to be placed in the activity layout
        Start_Menu start_menu_fragment = new Start_Menu();
        // Add the fragment to the 'team_list_container' FrameLayout
        String VIEW_TAG="start_menu";
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, start_menu_fragment, VIEW_TAG).commit();

    }


    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void launchPlayerList(TeamListFragmentBusEvent event) {
        //Find the index of the team based on the name sadly there is no indexOf in Java. TODO write indexOf helper function
        String teamName=event.getParameter();
        League temp_league= App.getLeague();
        String [] team_list=temp_league.getTeamNameArray();
        int team_index=-1;
        for (int x=0; x<team_list.length; x++) {
            if (team_list[x].equals(teamName)) {
                team_index=x;
            }
        }
        //Create the new fragment
        PlayerListFragment newFragment = new PlayerListFragment();
        //Need to pass an int value of the clicked Team into the fragment. Need to find it based on the team name.
        Bundle args = new Bundle();
        args.putInt("TeamIndex", team_index);
        newFragment.setArguments(args);
        //Create the fragment and launch it
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void fragmentLauncherTeamList(LoadSavedTeamBusEvent event) {
        String team_name=event.getParameter();
        League temp_league= App.getLeague();
        String [] team_list=temp_league.getTeamNameArray();
        int team_index=-1;
        for (int x=0; x<team_list.length; x++) {
            if (team_list[x].equals(team_name)) {
                team_index=x;
            }
        }
        BuildTeamScreenFragment construct_team_fragment = new BuildTeamScreenFragment();
        Bundle args = new Bundle();
        args.putInt("TeamIndex", team_index);
        construct_team_fragment.setArguments(args);
        FragmentTransaction construct_team_transaction = getSupportFragmentManager().beginTransaction();
        construct_team_transaction.replace(R.id.fragment_container, construct_team_fragment);
        construct_team_transaction.addToBackStack(null);
        construct_team_transaction.commit();

    }

    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void startMenuFragmentLauncher(StringFragmentBusEvent event) {
        //This is the handler for button presses from the start menu.
        String menu_item=event.getParameter();
        TeamListFragment teamlist_fragment = new TeamListFragment();
        FragmentTransaction teamlist_transaction = getSupportFragmentManager().beginTransaction();
        switch (menu_item) {
            case "guild_info":
                App.setCurrentSection("guilds");
                TeamListFragment newFragment = new TeamListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case "team_builder":
                App.setCurrentSection("lists");
                TeamBuilderFragment_New_Or_Saved_Screen teambuilder_fragment = new TeamBuilderFragment_New_Or_Saved_Screen();
                FragmentTransaction teambuilder_transaction = getSupportFragmentManager().beginTransaction();
                teambuilder_transaction.replace(R.id.fragment_container, teambuilder_fragment);
                teambuilder_transaction.addToBackStack(null);
                teambuilder_transaction.commit();
                break;
            case "new_team_button":
                teamlist_transaction.replace(R.id.fragment_container, teamlist_fragment);
                teamlist_transaction.addToBackStack(null);
                teamlist_transaction.commit();
                break;
            case "load_team_button":
                teamlist_transaction.replace(R.id.fragment_container, teamlist_fragment);
                teamlist_transaction.addToBackStack(null);
                teamlist_transaction.commit();
                break;
        }
    }

    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void fragmentLauncherStringArray(StringArrayFragmentBusEvent event) {
        String [] passed_string_array=event.getParameter();
        String category=passed_string_array[0];
        String value=passed_string_array[1];
        switch (category) {
            case "build_team_choose_guild":
                //In the team section this handler is called when a new team is being built and a guild is chosen. It should open the team builder screen
                League temp_league= App.getLeague();
                String [] team_list=temp_league.getTeamNameArray();
                int team_index=-1;
                for (int x=0; x<team_list.length; x++) {
                    if (team_list[x].equals(value)) {
                        team_index=x;
                    }
                }
                BuildTeamScreenFragment construct_team_fragment = new BuildTeamScreenFragment();
                Bundle args = new Bundle();
                args.putInt("TeamIndex", team_index);
                construct_team_fragment.setArguments(args);
                FragmentTransaction construct_team_transaction = getSupportFragmentManager().beginTransaction();
                construct_team_transaction.replace(R.id.fragment_container, construct_team_fragment);
                construct_team_transaction.addToBackStack(null);
                construct_team_transaction.commit();
                break;

        }

    }




    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void playerInfoLauncher(PlayerListFragmentBusEvent event) {
        //Find the index of the team based on the name Look into if there's an easier way TODO
        String playerName=event.getParameter();
        //Create the new fragment
        PlayerInfoFragment newFragment = new PlayerInfoFragment();
        //Need to pass an int value of the clicked Team into the fragment. Need to find it based on the team name.
        Bundle args = new Bundle();
        args.putString("PlayerName", playerName);
        newFragment.setArguments(args);
        //Create the fragment and launch it
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadLeague() {
        //Create the league and add it to the App
        League created_league = new League();
        App.setLeague(created_league);
    }
}
