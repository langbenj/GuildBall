package com.example.langbenj.guildball.PlayerInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.R;




public class PlayerInfoFragment extends Fragment {

    public Player mCurrentPlayer;
    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {

        View view = fragmentInflater.inflate(R.layout.player_info, target, false);

        //Accept passed team index of the team to load
        Bundle bundle = this.getArguments();
        Player player = new Player(bundle.getString("PlayerName", ""));
        mCurrentPlayer=player;
        //Set the various text and image fields with the information from the xml. setPlayerInfoDisplay handles all of the hard work.
        setPlayerInfoDisplay(view, "player_info_name_field", player.getName(), "text");
        setPlayerInfoDisplay(view, "item_move", player.getJog() + "/" + player.getSprint(), "text");
        setPlayerInfoDisplay(view, "item_tac", player.getTac(), "text");
        setPlayerInfoDisplay(view, "item_kick", player.getKickDice() + "/" + player.getKickDistance(), "text");
        setPlayerInfoDisplay(view, "item_def", player.getDefense(), "text");
        setPlayerInfoDisplay(view, "item_arm", player.getArmor(), "text");
        setPlayerInfoDisplay(view, "item_inf", player.getInfluenceGenerated() + "/" + player.getMaxInfluence(), "text");
        setPlayerInfoDisplay(view, "player_image", player.getName().toLowerCase(), "drawable");
        setPlayerInfoDisplay(view, "player_info_melee_range", "Melee Range: "+player.getMeleeRange(), "text");


        //Construct the playbook values for the player top and bottom
        buildPlaybook(view, player);
        buildHealthTrack(view,player);

        //This could cause sync issues, maybe, look into it TODO
        App.setCurrentPlayer(player);
        App.setCurrentView(view);
        //Build the pager for the Character info so that we don't have to scroll.

       ViewPager vpPager = (ViewPager) view.findViewById(R.id.player_info_view_pager);
        //Make sure to use: getChildFragmentManager here. A bug pops up where pages will sometimes not display otherwise.
       PlayerInfoPagerAdapter adapterViewPager = new PlayerInfoPagerAdapter(getChildFragmentManager());
      vpPager.setAdapter(adapterViewPager);
        return view;
    }

    private void setPlayerInfoDisplay(View view, String res_ID, String contents, String type) {
        int view_id;
        int image_id;
        switch (type) {
            case "text":
                view_id = view.getResources().getIdentifier(res_ID, "id", getContext().getPackageName());
                TextView target_field = (TextView) view.findViewById(view_id);
                target_field.setText(contents);
                break;
            case "drawable":
                image_id = view.getResources().getIdentifier(contents, "drawable", getContext().getPackageName());
                view_id = view.getResources().getIdentifier(res_ID, "id", getContext().getPackageName());
                ImageView target_image_field = (ImageView) view.findViewById(view_id);
                target_image_field.setImageResource(image_id);
                break;
        }
    }

    private void buildHealthTrack(View view, Player player) {
        int player_health= player.getMaxLife();
        int [] icy_sponge_array = player.getIcySponge();
        String temp_target;
        String display_image;
        int image_id;

        for (int i=player_health+1; i<=30; i++) {
            temp_target = "health_" + i;
            display_image = "blank";
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
}




