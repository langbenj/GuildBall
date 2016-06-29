package com.langco.langbenj.guildball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.langco.langbenj.guildball.DataAssemblers.Player;
import com.langco.langbenj.guildball.Helpers.App;
import com.langco.langbenj.guildball.Helpers.PlayDamageBusEvent;
import com.langco.langbenj.guildball.Helpers.PlayTeamListBusEvent;
import com.squareup.otto.Subscribe;

public class PlayTeamListFragment extends Fragment {
    View mCurrentView;

    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {
        String [] player_list = App.getPlayList();
        boolean greede=false;
        for (int i=0; i<player_list.length; i++) {
            if (player_list[i].equals("Greede")) {
                greede=true;
            }
        }
        View view = fragmentInflater.inflate(R.layout.play_team_list, target, false);
        if (greede) {
            view = fragmentInflater.inflate(R.layout.play_team_list_greede, target, false);
        }

        mCurrentView=view;
        final Button player_button_1 = (Button) view.findViewById(R.id.player_1_button);
        final Button player_button_2 = (Button) view.findViewById(R.id.player_2_button);
        final Button player_button_3 = (Button) view.findViewById(R.id.player_3_button);
        final Button player_button_4 = (Button) view.findViewById(R.id.player_4_button);
        final Button player_button_5 = (Button) view.findViewById(R.id.player_5_button);
        final Button player_button_6 = (Button) view.findViewById(R.id.player_6_button);
        final Button player_button_7 = (Button) view.findViewById(R.id.player_7_button);
        App.bus.register(this);


        App.setmPlayPlayer1(new Player(player_list[0]));
        App.setmPlayPlayer2(new Player(player_list[1]));
        App.setmPlayPlayer3(new Player(player_list[2]));
        App.setmPlayPlayer4(new Player(player_list[3]));
        App.setmPlayPlayer5(new Player(player_list[4]));
        App.setmPlayPlayer6(new Player(player_list[5]));
        if (greede) {
            App.setmPlayPlayer7(new Player(player_list[6]));
        }
        changeButtonText(player_button_1,player_list[0]);
        changeButtonText(player_button_2,player_list[1]);
        changeButtonText(player_button_3,player_list[2]);
        changeButtonText(player_button_4,player_list[3]);
        changeButtonText(player_button_5,player_list[4]);
        changeButtonText(player_button_6,player_list[5]);

        if (greede) {
            changeButtonText(player_button_7,player_list[6]);
        }


        player_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 App.bus.post(new PlayTeamListBusEvent(0));
            }
        });
        player_button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new PlayTeamListBusEvent(1));
            }
        });
        player_button_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new PlayTeamListBusEvent(2));
            }
        });
        player_button_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new PlayTeamListBusEvent(3));
            }
        });
        player_button_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new PlayTeamListBusEvent(4));
            }
        });
        player_button_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new PlayTeamListBusEvent(5));
            }
        });

        player_button_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.bus.post(new PlayTeamListBusEvent(6));
            }
        });

        return view;
    }

    @Subscribe
    public void playerPassed(PlayDamageBusEvent event) {
        Player player = event.getParameter();
        final Button player_button_1 = (Button) mCurrentView.findViewById(R.id.player_1_button);
        final Button player_button_2 = (Button) mCurrentView.findViewById(R.id.player_2_button);
        final Button player_button_3 = (Button) mCurrentView.findViewById(R.id.player_3_button);
        final Button player_button_4 = (Button) mCurrentView.findViewById(R.id.player_4_button);
        final Button player_button_5 = (Button) mCurrentView.findViewById(R.id.player_5_button);
        final Button player_button_6 = (Button) mCurrentView.findViewById(R.id.player_6_button);
        final Button player_button_7 = (Button) mCurrentView.findViewById(R.id.player_7_button);
        String [] player_list = App.getPlayList();
        String player_name=player.getName();
        String button_text;
        if (player_name.equals(player_list[0])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_1.setText(button_text);
        }
        else if (player_name.equals(player_list[1])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_2.setText(button_text);
        }
        else if (player_name.equals(player_list[2])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_3.setText(button_text);
        }
        else if (player_name.equals(player_list[3])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_4.setText(button_text);
        }
        else if (player_name.equals(player_list[4])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_5.setText(button_text);
        }
        else if (player_name.equals(player_list[5])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_6.setText(button_text);
        }
        else if (player_name.equals(player_list[6])) {
            button_text=player_name+"-"+String.valueOf(player.getCurrentLife());
            player_button_7.setText(button_text);
        }
    }

    public void changeButtonText(Button butt_reference, String new_text) {
        butt_reference.setText(new_text);
    }

}
