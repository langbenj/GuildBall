package com.example.langbenj.guildball.TeamBuilder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.DataAssemblers.Team;
import com.example.langbenj.guildball.Databases.SavedTeamsDbHelper;
import com.example.langbenj.guildball.Helpers.AddRemovePlayerBusEvent;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.PlayerListFragmentBusEvent;
import com.example.langbenj.guildball.R;
import com.squareup.otto.Subscribe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;


public class BuildTeamScreenFragment extends Fragment {

    private ViewGroup mCurrentViewGroup;
    private View mCurrentView;
    private static String TAG = "BuildTeamScreenFragment";
    private ArrayList<String> mPlayerList = new ArrayList<>();
    private ArrayList<String> mPlayerButtons = new ArrayList<>();
    private ArrayList<Integer> mPlayerButtonResourceIDs = new ArrayList<>();
    private ArrayList<Integer> mPlayerTextFieldsResourceIDs = new ArrayList<>();
    private String mLoadTeamFlag="no";
    private String mLoadTeamID;
    private SavedTeamsDbHelper current_database;
    private SQLiteDatabase db;
    private Cursor mLoadResults;
    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        View view = fragmentInflater.inflate(R.layout.team_construction_page, target, false);
        mCurrentView = view;
        mCurrentViewGroup=target;
        current_database = App.getSavedTeamsDB();
        db = current_database.getWritableDatabase();
        //Accept passed team index of the team to load
        Bundle bundle = this.getArguments();
        int teamIndex = bundle.getInt("TeamIndex", 0);
        if (bundle.size()==2) {
            mLoadTeamFlag="yes";
            mLoadTeamID=bundle.getString("LoadedTeamID");
            String [] temp_ID_arry = {mLoadTeamID};

            String[] fields_to_return = {"teamname","team","player1","player2","player3","player4","player5","player6","player7","player8","player9","player10","player11","player12","player13","player14","player15","player16"
                    };
            String selection = "team_id" + " LIKE ?";
            mLoadResults = db.query("teams", fields_to_return, selection, temp_ID_arry, null, null, null);
            mLoadResults.moveToFirst();
            String team = mLoadResults.getString(mLoadResults.getColumnIndexOrThrow("team"));
        }
        else {
            mLoadTeamFlag="no";
        }


        //TODO Figure out how to determine if the object is already registered with OTTO
        try
        {
            App.bus.register(this);
        }
        catch(Exception e)
        {
            Log.d(TAG, "Problem with registration of OTTO bus: " + e.getMessage());
        }




        //Pull in the League and using the index find the proper team
        League temp_league=App.getLeague();
        Team[] temp_team=temp_league.getTeams();
        Team temp_team_to_create = temp_team[teamIndex];

        //Add the team logo to the upper right of the page
        ImageView image_view = (ImageView) view.findViewById(R.id.team_construction_logo_image);
        if (mLoadTeamFlag=="yes") {
            TextView temp_text_view =(TextView) view.findViewById(R.id.team_construction_list_name);
            temp_text_view.setText(mLoadResults.getString(mLoadResults.getColumnIndexOrThrow("teamname")));
        }
        String logo_image = (temp_team_to_create.getTeamName()+"_logo").toLowerCase();
        App.setCurrentTeam(temp_team_to_create.getTeamName());
        final Context context = App.getContext();
        int image_id = context.getResources().getIdentifier(logo_image, "drawable", context.getPackageName());
        image_view.setImageResource(image_id);

        //Set up arrays of the Button resources and player names. This is quicker than running getIdentifier over and over again in a for loop.

        mPlayerButtonResourceIDs.add(R.id.team_player1_image);
        mPlayerButtonResourceIDs.add(R.id.team_player2_image);
        mPlayerButtonResourceIDs.add(R.id.team_player3_image);
        mPlayerButtonResourceIDs.add(R.id.team_player4_image);
        mPlayerButtonResourceIDs.add(R.id.team_player5_image);
        mPlayerButtonResourceIDs.add(R.id.team_player6_image);
        mPlayerButtonResourceIDs.add(R.id.team_player7_image);
        mPlayerButtonResourceIDs.add(R.id.team_player8_image);
        mPlayerButtonResourceIDs.add(R.id.team_player9_image);
        mPlayerButtonResourceIDs.add(R.id.team_player10_image);
        mPlayerButtonResourceIDs.add(R.id.team_player11_image);
        mPlayerButtonResourceIDs.add(R.id.team_player12_image);
        mPlayerButtonResourceIDs.add(R.id.team_player13_image);
        mPlayerButtonResourceIDs.add(R.id.team_player14_image);
        mPlayerButtonResourceIDs.add(R.id.team_player15_image);
        mPlayerButtonResourceIDs.add(R.id.team_player16_image);

        mPlayerTextFieldsResourceIDs.add(R.id.team_player1);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player2);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player3);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player4);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player5);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player6);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player7);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player8);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player9);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player10);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player11);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player12);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player13);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player14);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player15);
        mPlayerTextFieldsResourceIDs.add(R.id.team_player16);



        //These two event trackers watch the list name and update the title
        final EditText editText = (EditText) view.findViewById(R.id.new_list_name_edit_box);
        final TextView title_text = (TextView) view.findViewById(R.id.team_construction_list_name);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Context context = App.getContext();
                    String pass_text = editText.getText().toString();
                    title_text.setText(pass_text);
                    handled = true;
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return handled;
            }
        });

        Button title_set = (Button) view.findViewById(R.id.list_title_set);
        title_set.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pass_text = editText.getText().toString();
                title_text.setText(pass_text);
                Log.d(TAG, pass_text);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        if (mPlayerButtons.size()==0) { //Only init the array if it's new
            for (int i = 0; i < 16; i++) {
                mPlayerButtons.add("ic_add_box_black_48dp");
            }
        }


        //Set the player names
        final String [] player_list = temp_team_to_create.getPlayerNameArray();

        for (int i=0; i<16; i++) {

            //This piece of code will set up the page if the player was loaded.

            if (mLoadTeamFlag!="no") {
                if (player_list.length >= (i + 1)) {
                    if (checkIfLoadedPlayer(player_list[i])) {
                        mPlayerButtons.set(i, (App.getCurrentTeam() + "_logo").toLowerCase());
                    }
                }
            }


            if (player_list.length>= (i + 1)) {
                updatePlayerNames(view, mPlayerTextFieldsResourceIDs.get(i), player_list[i]);


            }
            else {

                mPlayerButtons.set(i,"blank");
                updatePlayerNames(view, mPlayerTextFieldsResourceIDs.get(i), "");
            }

        }


        //TODO Need to figure out how to dynamically set up buttons to remove duplicate code

        //Sets up the listeners for each button and player name.
        Button add_button_1 = (Button) view.findViewById(R.id.team_player1_image);
        add_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(0) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(0, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(0).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(0, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        TextView player_name_click = (TextView) view.findViewById(R.id.team_player1);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player1);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });



        Button add_button_2 = (Button) view.findViewById(R.id.team_player2_image);
        add_button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(1) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(1, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(1).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(1, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player2);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player2);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_3 = (Button) view.findViewById(R.id.team_player3_image);
        add_button_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(2) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(2, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(2).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(2, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player3);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player3);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_4 = (Button) view.findViewById(R.id.team_player4_image);
        add_button_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(3) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(3, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(3).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(3, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player4);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player4);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_5 = (Button) view.findViewById(R.id.team_player5_image);
        add_button_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(4) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(4, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(4).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(4, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player5);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player5);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_6 = (Button) view.findViewById(R.id.team_player6_image);
        add_button_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(5) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(5, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(5).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(5, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player6);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player6);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_7 = (Button) view.findViewById(R.id.team_player7_image);
        add_button_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(6) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(6, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(6).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(6, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player7);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player7);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_8 = (Button) view.findViewById(R.id.team_player8_image);
        add_button_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(7) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(7, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(7).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(7, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player8);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player8);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_9 = (Button) view.findViewById(R.id.team_player9_image);
        add_button_9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(8) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(8, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(8).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(8, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player9);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player9);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_10 = (Button) view.findViewById(R.id.team_player10_image);
        add_button_10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(9) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(9, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(9).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(9, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player10);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player10);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_11 = (Button) view.findViewById(R.id.team_player11_image);
        add_button_11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(10) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(10, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(10).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(10, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player11);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player11);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_12 = (Button) view.findViewById(R.id.team_player12_image);
        add_button_12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(11) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(11, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(11).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(11, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player12);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player12);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_13 = (Button) view.findViewById(R.id.team_player13_image);
        add_button_13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(12) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(12, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(12).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(12, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player14);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player14);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_14 = (Button) view.findViewById(R.id.team_player14_image);
        add_button_14.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(13) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(13, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(13).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(13, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        player_name_click = (TextView) view.findViewById(R.id.team_player14);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player14);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_15 = (Button) view.findViewById(R.id.team_player15_image);
        add_button_15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(14) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(14, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(14).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(14, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });


        player_name_click = (TextView) view.findViewById(R.id.team_player15);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view= (TextView) v.findViewById(R.id.team_player15);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        Button add_button_16 = (Button) view.findViewById(R.id.team_player16_image);
        add_button_16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mPlayerButtons.get(15) == "ic_add_box_black_48dp") {
                    mPlayerButtons.set(15, (App.getCurrentTeam() + "_logo").toLowerCase());
                } else if (mPlayerButtons.get(15).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                    mPlayerButtons.set(15, "ic_add_box_black_48dp");
                }
                updatePlayerDisplay();
            }
        });

        //This button triggers the saving of lists.
        Button save_team = (Button) view.findViewById(R.id.save_team);
        save_team.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //First we need to pull in all of the variables:
                String team=App.getCurrentTeam();
                for (int i=0; i<16; i++) {
                    if (mPlayerButtons.get(i).equals((App.getCurrentTeam() + "_logo").toLowerCase())) {
                        mPlayerList.add(player_list[i]);
                    }
                    else {
                        mPlayerList.add("");
                    }
                }


                //TODO Need to shift to an async thread
                String[] fields_to_return = {"teamname"};
                String selection = "teamname" + " LIKE ?";
                String [] criteria = {(String) title_text.getText()};
                Cursor mCheckResults = db.query("teams", fields_to_return, selection, criteria, null, null, null);
                if (mCheckResults.getCount()!=0) {
                    //If the database query returns a 0 value the team is not in the db. Update the db rather than creating a new entry
                    ContentValues values = new ContentValues();
                    values.put("team", team);
                    for (int x = 1; x <= 16; x++) {
                        if (mPlayerList.get(x-1).equals("")) {
                            values.put("player" + (x), "");
                        } else {
                            values.put("player" + (x), mPlayerList.get(x-1));
                        }
                    }
// Which row to update, based on the ID

                    try
                    {
                        Toast.makeText(App.getContext(), "List Saved", Toast.LENGTH_SHORT).show();
                        int count = db.update("teams", values, selection, criteria);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(App.getContext(), "Problem With Saving List", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Problem with updating DB: " + e.getMessage());
                    }


                }
                else {
                // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put("team_id", UUID.randomUUID().toString());
                    values.put("teamname", title_text.getText().toString());
                    values.put("team", team);
                    for (int x = 1; x <= 16; x++) {
                        if (mPlayerList.get(x-1).equals("")) {
                            Toast.makeText(App.getContext(), "List Saved", Toast.LENGTH_SHORT).show();
                            values.put("player" + x, "");
                        } else {
                            Toast.makeText(App.getContext(), "Problem With Saving List", Toast.LENGTH_SHORT).show();
                            values.put("player" + x, mPlayerList.get(x-1));
                        }
                    }
                    // Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    try
                    {
                        newRowId = db.insert("teams", null, values);
                    }
                    catch(Exception e)
                    {
                        Log.d(TAG, "Problem with new DB: " + e.getMessage());
                    }
                }
                mCheckResults.close();
            }
        });




        player_name_click = (TextView) view.findViewById(R.id.team_player16);
        player_name_click.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView player_view = (TextView) v.findViewById(R.id.team_player16);
                String passed_player = (String) player_view.getText();
                App.setCurrentPlayer(null);
                App.bus.post(new PlayerListFragmentBusEvent(passed_player));
            }
        });

        return view;

    }

    private void updatePlayerNames (View view, int fieldID, String player_name) {
        TextView player_name_field = (TextView) view.findViewById(fieldID);
        player_name_field.setText(player_name);
        return;
    }

    public void onStart() {
        super.onStart();
        updatePlayerDisplay();

    }

    public boolean checkIfLoadedPlayer (String player_name) {


        mLoadResults.moveToFirst();

        boolean return_value=false;
        ArrayList<String> loaded_list = new ArrayList<>();

        //18 is the total amount of records returned in the DB Query
        for (int i=0; i<mLoadResults.getColumnCount (); i++) {
            String player=mLoadResults.getString(i);
            loaded_list.add(player);
        }

        if (loaded_list.contains(player_name)) {
            return_value=true;
        }

      return return_value;

    }


    private void updateButtonImages (View view, int buttonID, String button_drawable) {

            Context context = App.getContext();
            Button image_view = (Button) view.findViewById(buttonID);
            int image_id = context.getResources().getIdentifier(button_drawable, "drawable", context.getPackageName());
            image_view.setBackgroundResource(image_id);

        return;
    }

    private void updatePlayerDisplay() {
        for (int i=0; i<=15; i++) {
             updateButtonImages(mCurrentView, mPlayerButtonResourceIDs.get(i), mPlayerButtons.get(i));
        }
    }


}

