package com.langco.langbenj.guildball.DamageTracking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.langco.langbenj.guildball.Helpers.App;
import com.langco.langbenj.guildball.R;

public class PlayFragment extends Fragment {

    private static View view;

    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {

       String [] player_list = App.getPlayList();
        boolean greede=false;
        for (int i=0; i<player_list.length; i++) {
            if (player_list[i].equals("Greede")) {
                greede=true;
            }
        }


        view = fragmentInflater.inflate(R.layout.play_main, target, false);


        PlayPlayerInfoFragment info_fragment = new PlayPlayerInfoFragment();


        FragmentTransaction damage_transaction = getChildFragmentManager().beginTransaction();
        damage_transaction.replace(R.id.player_info_container, info_fragment);
        damage_transaction.addToBackStack(null);
        damage_transaction.commit();
        return view;
    }
}

