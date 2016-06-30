package com.langco.langbenj.guildball.DamageTracking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.langco.langbenj.guildball.DataAssemblers.Player;
import com.langco.langbenj.guildball.Helpers.App;
import com.langco.langbenj.guildball.PlayerInfo.PlayerInfoPagerAdapter;
import com.langco.langbenj.guildball.R;


public class PlayPlayerInfoFragment extends Fragment {
    private Player mCurrentPlayer;
    private View mCurrentView;
    private boolean mExtraPlayer = false;
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {
        View view = fragmentInflater.inflate(R.layout.play_player_info, target, false);
        App.bus.register(this);
        mCurrentView=view;

        //Check to see if the extra player is on the team currently. This triggers special exceptions
        checkExtraPlayer();

        //Pull the current player list
        String [] player_list = App.getPlayList();

        //Init the buttons for the top nav and set the text on them to the player names
        final Button player_button_1 = (Button) view.findViewById(R.id.player_1_button);
        final Button player_button_2 = (Button) view.findViewById(R.id.player_2_button);
        final Button player_button_3 = (Button) view.findViewById(R.id.player_3_button);
        final Button player_button_4 = (Button) view.findViewById(R.id.player_4_button);
        final Button player_button_5 = (Button) view.findViewById(R.id.player_5_button);
        final Button player_button_6 = (Button) view.findViewById(R.id.player_6_button);
        final Button player_button_7 = (Button) view.findViewById(R.id.player_7_button);
        Button [] player_button_list = {player_button_1,player_button_2,player_button_3,player_button_4,player_button_5,player_button_6,player_button_7};

        for (int i=1; i<=6; i++) {
            App.setPlayPlayer(new Player(player_list[i-1]), i);
            changeButtonText(player_button_list[i-1],player_list[i-1]);
        }

        //If there's a 7th player then setup the button otherwise remove the button layout
        if (mExtraPlayer) {
            App.setPlayPlayer(new Player(player_list[6]),7);
            changeButtonText(player_button_7,player_list[6]);
        }
        else {
            ViewGroup layout = (ViewGroup) player_button_7.getParent();
            if(null!=layout)
                layout.removeView(player_button_7);
        }
        //OnClickListeners to set the current player based on the top nav click
        player_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(0);
            }
        });
        player_button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(1);
            }
        });
        player_button_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(2);
            }
        });
        player_button_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(3);
            }
        });
        player_button_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(4);
            }
        });
        player_button_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(5);
            }
        });
        player_button_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setupPlayer(6);
            }
        });


        //Setup the initial first player on the list and fill the fields so the screen isn't blank when a user enters it.
        setupPlayer(0);

        //Init the heal button and the damage button
        Button heal_button = (Button) view.findViewById(R.id.heal_button);
        Button damage_button = (Button) view.findViewById(R.id.damage_button);

        heal_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Add one to the current player's health and then rebuild the damage track. This will automatically shade damage red
                mCurrentPlayer.changeLife(1);
                buildDamageTrack(mCurrentView,mCurrentPlayer);

            }
        });

        damage_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Subtract one from the current player's health and then rebuild the damage track. This will automatically shade damage red
                mCurrentPlayer.changeLife(-1);
                buildDamageTrack(mCurrentView,mCurrentPlayer);
            }
        });

        return view;
    }

    private void checkExtraPlayer () {
        String [] player_list = App.getPlayList();

        for (int i=0; i<player_list.length; i++) {
            if (player_list[i].equals("Greede")) {
                //Greede is the current only case where there is an extra player. In the future this can be expanded.
                mExtraPlayer =true;
            }
        }
    }

    //Takes a player number looks up the player's info and sets up the display
    private void setupPlayer(int player_number) {

        mCurrentPlayer=App.getPlayPlayer(player_number);
        App.setCurrentPlayer(mCurrentPlayer);
        View t=mCurrentView;
        //Set the text fields to display the current player's info
        setPlayerInfoDisplay(mCurrentView, "player_info_name_field", mCurrentPlayer.getName(), "text");
        setPlayerInfoDisplay(mCurrentView, "item_move", mCurrentPlayer.getJog() + "/" + mCurrentPlayer.getSprint(), "text");
        setPlayerInfoDisplay(mCurrentView, "item_tac", mCurrentPlayer.getTac(), "text");
        setPlayerInfoDisplay(mCurrentView, "item_kick", mCurrentPlayer.getKickDice() + "/" + mCurrentPlayer.getKickDistance(), "text");
        setPlayerInfoDisplay(mCurrentView, "item_def", mCurrentPlayer.getDefense(), "text");
        setPlayerInfoDisplay(mCurrentView, "item_arm", mCurrentPlayer.getArmor(), "text");
        setPlayerInfoDisplay(mCurrentView, "item_inf", mCurrentPlayer.getInfluenceGenerated() + "/" + mCurrentPlayer.getMaxInfluence(), "text");
        setPlayerInfoDisplay(mCurrentView, "player_info_melee_range", "Melee Range: "+mCurrentPlayer.getMeleeRange(), "text");

        //Call the functions that dynamically set up the image resources
        buildPlaybook(mCurrentView, mCurrentPlayer);
        buildHealthTrack(mCurrentView,mCurrentPlayer);
        buildDamageTrack(mCurrentView,mCurrentPlayer);

        //Add the view pager that shows the player abilities
        ViewPager vpPager = (ViewPager) mCurrentView.findViewById(R.id.player_info_view_pager);
        //Make sure to use: getChildFragmentManager here. A bug pops up where pages will sometimes not display otherwise.
        PlayerInfoPagerAdapter adapterViewPager = new PlayerInfoPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);

    }

    //Function to change text and images programmatically
    private void setPlayerInfoDisplay(View view, String res_ID, String contents, String type) {
        int view_id;
        int image_id;

        switch (type) {
            case "text":
                //Pull the id of the view based on the generated string
                view_id = view.getResources().getIdentifier(res_ID, "id",getContext().getPackageName());
                //Set the value of the view
                TextView target_field = (TextView) view.findViewById(view_id);
                target_field.setText(contents);
                break;
            case "drawable":
                //Pull the id of the image based on the generated string
                image_id = view.getResources().getIdentifier(contents, "drawable", getContext().getPackageName());
                //Pull the id of the view based on the generated string
                view_id = view.getResources().getIdentifier(res_ID, "id", getContext().getPackageName());
                //Set the image displayed in the view
                ImageView target_image_field = (ImageView) view.findViewById(view_id);
                target_image_field.setImageResource(image_id);

                break;
        }
    }

    //Set up the health track to display the proper amount of circles and fill some of them with numbers based on the data
    private void buildHealthTrack(View view, Player player) {
        int player_health= player.getMaxLife();
        int [] icy_sponge_array = player.getIcySponge();
        String temp_target;
        String display_image;
        int image_id;

        for (int i=1; i<=30; i++) {
            temp_target = "health_" + i;
            if (i<player_health) {
                display_image = "health_empty";
            }
            else {
                display_image = "blank";
            }
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }

            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

      for (int i=1; i<icy_sponge_array.length; i++) {
            temp_target = "health_"+icy_sponge_array[i];
            display_image="health_"+i;
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }

            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");

        }

    }

    //Rebuild the health track switching circles to red if the damage to the player requires it.
    private void buildDamageTrack(View view, Player player) {
        int player_health= player.getMaxLife();
        int current_health = player.getCurrentLife();
        int [] icy_sponge_array = player.getIcySponge();
        String temp_target;
        String display_image;
        int image_id;

        for (int i=1; i<=current_health; i++) {
            temp_target = "health_" + i;
            display_image = "health_empty";
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

        for (int i=1; i<icy_sponge_array.length; i++) {
            temp_target = "health_"+icy_sponge_array[i];
            display_image="health_"+i;
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

        for (int i=current_health+1; i<=player_health; i++) {
            temp_target = "health_" + i;
            display_image = "health_full";
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

    }

    //Set up the playbook images based on the player data
    private void buildPlaybook(View view, Player player) {
        String [] top_row = player.getPlaybookTop();
        String [] bottom_row = player.getPlaybookBottom();
        String temp_target;
        String display_image;
        int image_id;
        for (int i=0; i<top_row.length; i++) {
            temp_target = "playbook_top_" + (i + 1);
            display_image = ("playbook_"+top_row[i]).toLowerCase();
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

        for (int i=top_row.length; i<8; i++) {
            temp_target = "playbook_top_" + (i+1);
            display_image = "playbook_blank";
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

        for (int i=0; i<bottom_row.length; i++) {
            temp_target = "playbook_bottom_" + (i + 1);
            display_image = ("playbook_"+bottom_row[i]).toLowerCase();
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            if (image_id==0) {
                display_image="playbook_no";
            }
            display_image=display_image.toLowerCase();
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }

        for (int i=bottom_row.length; i<8; i++) {
            temp_target = "playbook_bottom_" + (i+1);
            display_image = "playbook_blank";
            image_id = view.getResources().getIdentifier(display_image, "drawable", getContext().getPackageName());
            setPlayerInfoDisplay(view, temp_target,display_image, "drawable");
        }


    }

    public void changeButtonText(Button butt_reference, String new_text) {
        butt_reference.setText(new_text);
    }
}
