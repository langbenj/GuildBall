package com.example.langbenj.guildball.PlayerInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langbenj.guildball.DataAssemblers.Ability;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.R;


public class LegendaryPlayPagerFragment extends Fragment {
    public static String font_size;

    // newInstance constructor for creating fragment with arguments
    public static LegendaryPlayPagerFragment newInstance() {
        return new LegendaryPlayPagerFragment();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.legendary_play_pager_layout, container, false);
        Player player = App.getCurrentPlayer();


        Ability[] legendary_play_list = player.getLegendaryPlays();
        Ability legendary_play=legendary_play_list[0];
        font_size = player.getFontSize();

        TextView target_field = (TextView) view.findViewById(R.id.legendary_play_name);
        target_field.setText((CharSequence) legendary_play.getName());
        target_field.setTextSize(Integer.parseInt(font_size));
        target_field = (TextView) view.findViewById(R.id.legendary_play_description);
        target_field.setText((CharSequence) legendary_play.getAbilityDescription());
        target_field.setTextSize(Integer.parseInt(font_size));


        return view;
    }
}
