package com.example.langbenj.guildball.TeamBuilder;

import android.app.Activity;
import android.content.Context;
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


        //These two event trackers watch the list name and update the title
        final EditText editText = (EditText) view.findViewById(R.id.new_list_name_edit_box);
        final TextView title_text = (TextView) view.findViewById(R.id.team_construction_list_name);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Context context=App.getContext();
                    String pass_text=editText.getText().toString();
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
                String pass_text=editText.getText().toString();
                title_text.setText(pass_text);
                Log.d(TAG,pass_text);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        if (mPlayerButtons.size()==0) { //Only init the array if it's new
            for (int i = 0; i < 16; i++) {
                mPlayerButtons.add("ic_add_box_black_48dp");
            }
        }

        //TODO figure out how to programatically pick out the resource for R.id.______ This will remove thse if/else

        String [] player_list = temp_team_to_create.getPlayerNameArray();

        if (player_list.length>=1) {
            updatePlayerNames(view, R.id.team_player1, player_list[0]);
        }
        else {
            mPlayerButtons.set(0,"blank");
            updatePlayerNames(view, R.id.team_player1, "");
        }

        if (player_list.length>=2) {
            updatePlayerNames(view, R.id.team_player2, player_list[1]);
        }
        else {
            mPlayerButtons.set(1,"blank");
            updatePlayerNames(view, R.id.team_player2, "");
        }

        if (player_list.length>=3) {
            updatePlayerNames(view, R.id.team_player3, player_list[2]);
        }
        else {
            mPlayerButtons.set(2,"blank");
            updatePlayerNames(view, R.id.team_player3, "");
        }

        if (player_list.length>=4) {
            updatePlayerNames(view, R.id.team_player4, player_list[3]);
        }
        else {
            mPlayerButtons.set(3,"blank");
            updatePlayerNames(view, R.id.team_player4, "");
        }

        if (player_list.length>=5) {

            updatePlayerNames(view, R.id.team_player5, player_list[4]);

        }
        else {
            mPlayerButtons.set(4,"blank");
            updatePlayerNames(view, R.id.team_player5, "");
        }
        if (player_list.length>=6) {
            updatePlayerNames(view, R.id.team_player6, player_list[5]);
        }
        else {
            mPlayerButtons.set(5,"blank");
            updatePlayerNames(view, R.id.team_player6, "");
        }
        if (player_list.length>=7) {
            updatePlayerNames(view, R.id.team_player7, player_list[6]);
        }
        else {
            mPlayerButtons.set(6,"blank");
            updatePlayerNames(view, R.id.team_player7, "");
        }


        if (player_list.length>=8) {
            updatePlayerNames(view, R.id.team_player8, player_list[7]);
        }
        else {
            mPlayerButtons.set(7,"blank");
            updatePlayerNames(view, R.id.team_player8, "");
        }
        if (player_list.length>=9) {
            updatePlayerNames(view, R.id.team_player9, player_list[8]);
        }
        else {
            mPlayerButtons.set(8,"blank");
            updatePlayerNames(view, R.id.team_player9, "");
        }

        if (player_list.length>=10) {
            updatePlayerNames(view, R.id.team_player10, player_list[9]);
        }
        else {
            mPlayerButtons.set(9,"blank");
            updatePlayerNames(view, R.id.team_player10, "");
        }

        if (player_list.length>=11) {
            updatePlayerNames(view, R.id.team_player11, player_list[10]);
        }
        else {
            updatePlayerNames(view, R.id.team_player11, "");
            mPlayerButtons.set(10,"blank");
        }

        if (player_list.length>=12) {
            updatePlayerNames(view, R.id.team_player12, player_list[11]);
        }
        else {
            mPlayerButtons.set(11,"blank");
            updatePlayerNames(view, R.id.team_player12, "");
        }

        if (player_list.length>=13) {
            updatePlayerNames(view, R.id.team_player13, player_list[12]);
        }
        else {
            mPlayerButtons.set(12,"blank");
            updatePlayerNames(view, R.id.team_player13, "");
        }
        if (player_list.length>=14) {
            updatePlayerNames(view, R.id.team_player14, player_list[13]);
        }
        else {
            mPlayerButtons.set(13,"blank");
            updatePlayerNames(view, R.id.team_player14, "");
        }
        if (player_list.length>=15) {
            updatePlayerNames(view, R.id.team_player15, player_list[14]);
        }
        else {
            mPlayerButtons.set(14,"blank");
            updatePlayerNames(view, R.id.team_player15, "");
        }
        if (player_list.length>=16) {
            updatePlayerNames(view, R.id.team_player16, player_list[15]);
        }
        else {
            mPlayerButtons.set(15,"blank");
            updatePlayerNames(view, R.id.team_player16, "");
        }

        //TODO Need to refactor this, to be frank, spaghetti code

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
                TextView player_view= (TextView) v.findViewById(R.id.team_player16);
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


    public void changePlayerList(String player_name, String add_or_remove) {


    }

    //Using the Otto library to pass items across a bus. Implemented in App
    @Subscribe
    public void addOrRemovePlayer (AddRemovePlayerBusEvent event) {
        String[] passed_string_array = event.getParameter();
        String player_name = passed_string_array[0];
        String todo = passed_string_array[1];
        Player target_player = getPlayerInfo(player_name);
        String player_display_name = "";

        switch (todo) {
            case "add":
                switch (target_player.getType()) {
                    case "Captain":

                        if (mPlayerList.size() < 10) {
                            player_display_name = target_player.getName();
                            mPlayerList.add(player_display_name);
                        }
                        break;
                    case "Mascot":
                        if (mPlayerList.size() < 10) {
                            player_display_name = target_player.getName();
                            mPlayerList.add(player_display_name);
                        }
                        break;
                    case "":
                        if (mPlayerList.size() < 10) {
                            player_display_name = target_player.getName();
                            mPlayerList.add(player_display_name);
                        }
                        break;
                }
                break;
            case "remove":
                mPlayerList.remove(target_player.getName());
                break;
        }

        Log.d(TAG, target_player.getType()+" "+player_display_name + " "+todo);

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
        Context context = App.getContext();



        //TODO refactor this code to be a function or loop
        Button image_view;
        int image_id;
        updateButtonImages(mCurrentView,R.id.team_player1_image,mPlayerButtons.get(0));
        updateButtonImages(mCurrentView,R.id.team_player2_image,mPlayerButtons.get(1));
        updateButtonImages(mCurrentView,R.id.team_player3_image,mPlayerButtons.get(2));
        updateButtonImages(mCurrentView,R.id.team_player4_image,mPlayerButtons.get(3));
        updateButtonImages(mCurrentView,R.id.team_player5_image,mPlayerButtons.get(4));
        updateButtonImages(mCurrentView,R.id.team_player6_image,mPlayerButtons.get(5));
        updateButtonImages(mCurrentView,R.id.team_player7_image,mPlayerButtons.get(6));
        updateButtonImages(mCurrentView,R.id.team_player8_image,mPlayerButtons.get(7));
        updateButtonImages(mCurrentView,R.id.team_player9_image,mPlayerButtons.get(8));
        updateButtonImages(mCurrentView,R.id.team_player10_image,mPlayerButtons.get(9));
        updateButtonImages(mCurrentView,R.id.team_player11_image,mPlayerButtons.get(10));
        updateButtonImages(mCurrentView,R.id.team_player12_image,mPlayerButtons.get(11));
        updateButtonImages(mCurrentView,R.id.team_player13_image,mPlayerButtons.get(12));
        updateButtonImages(mCurrentView,R.id.team_player14_image,mPlayerButtons.get(13));
        updateButtonImages(mCurrentView,R.id.team_player15_image,mPlayerButtons.get(14));
        updateButtonImages(mCurrentView,R.id.team_player16_image,mPlayerButtons.get(15));
    }

    private Player getPlayerInfo (String player_name) {
        //In the team section this handler is called when a new team is being built and a guild is chosen. It should open the team builder screen
        League temp_league= App.getLeague();
        String [] team_list=temp_league.getTeamNameArray();
        Player return_player;
        Team temp_team;
        for (int x=0; x<team_list.length; x++) {
            temp_team=temp_league.getTeam(team_list[x]);
            return_player=temp_team.getPlayer(player_name);
            if (return_player != null) {
                return return_player;
            }
        }
        return null;

    }
}

