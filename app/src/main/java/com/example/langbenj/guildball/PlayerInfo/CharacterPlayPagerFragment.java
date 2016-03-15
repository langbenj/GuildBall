package com.example.langbenj.guildball.PlayerInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langbenj.guildball.DataAssemblers.Ability;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.R;


public class CharacterPlayPagerFragment extends Fragment {

    // newInstance constructor for creating fragment with arguments
    public static CharacterPlayPagerFragment newInstance() {
        return new CharacterPlayPagerFragment();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void setPlayerInfoDisplay(View view, String res_ID, String contents, String type) {
        int view_id;
        int image_id;
        switch (type) {
            case "text":
                view_id = view.getResources().getIdentifier(res_ID, "id", getContext().getPackageName());
                TextView target_field = (TextView) view.findViewById(view_id);
                target_field.setText((CharSequence) contents);

                break;
            case "drawable":
                image_id = view.getResources().getIdentifier(contents, "drawable", getContext().getPackageName());
                view_id = view.getResources().getIdentifier(res_ID, "id", getContext().getPackageName());
                ImageView target_image_field = (ImageView) view.findViewById(view_id);
                target_image_field.setImageResource(image_id);
                break;
        }
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_play_pager_layout, container, false);
        Player current_player = App.getCurrentPlayer();
        Ability[] character_abilities = current_player.getCharacterPlays();
        String temp_target;
        String temp_value="";
        for (int i=0;i<character_abilities.length; i++) {
            temp_target = "character_play_name_" + (i + 1);
            temp_value = character_abilities[i].getName();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");

            if (character_abilities[i].getGuildBallCost().equals("") || character_abilities[i].getCost().equals("")) {
                temp_value = character_abilities[i].getCost();
            }
            else {
                temp_value = character_abilities[i].getCost()+" /";
            }
            temp_target = "character_play_cost_" + (i + 1);
           // temp_value = character_abilities[i].getCost();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");

            temp_target = "character_play_" + (i + 1) + "_gb_cost";
            temp_value = "gb_cost_"+character_abilities[i].getGuildBallCost();
            setPlayerInfoDisplay(view, temp_target, temp_value, "drawable");

            temp_target = "character_play_range_" + (i + 1);
            temp_value = character_abilities[i].getRange();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");

            temp_target = "character_play_zone_" + (i + 1);
            temp_value = character_abilities[i].getZones();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");

            temp_target = "character_play_sustain_" + (i + 1);
            temp_value = character_abilities[i].getSustain();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");

            temp_target = "character_play_description_" + (i + 1);
            temp_value = character_abilities[i].getAbilityDescription();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");
        }

        return view;
    }

    public void destroyItem(ViewGroup viewPager, int position, Object object) {
        viewPager.removeView((View) object);
    }
}
