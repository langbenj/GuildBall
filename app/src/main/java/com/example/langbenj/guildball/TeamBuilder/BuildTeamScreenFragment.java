package com.example.langbenj.guildball.TeamBuilder;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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

import com.example.langbenj.guildball.DataAssemblers.League;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.DataAssemblers.Team;
import com.example.langbenj.guildball.Helpers.AddRemovePlayerBusEvent;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.Helpers.PlayerListFragmentBusEvent;
import com.example.langbenj.guildball.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class BuildTeamScreenFragment extends Fragment {

    private ViewGroup mCurrentViewGroup;
    private View mCurrentView;
    private static String TAG = "BuildTeamScreenFragment";
    private ArrayList<String> mPlayerList = new ArrayList<>();
    private ArrayList<String> mPlayerButtons = new ArrayList<>();
    private ArrayList<Integer> mPlayerButtonResourceIDs = new ArrayList<>();
    private ArrayList<Integer> mPlayerTextFieldsResourceIDs = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        View view = fragmentInflater.inflate(R.layout.team_construction_page, target, false);
        mCurrentView = view;
        mCurrentViewGroup=target;

        //Accept passed team index of the team to load
        Bundle bundle = this.getArguments();
        int teamIndex = bundle.getInt("TeamIndex", 0);


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
        String [] player_list = temp_team_to_create.getPlayerNameArray();

        for (int i=0; i<16; i++) {
            if (player_list.length>=(i+1)) {
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

