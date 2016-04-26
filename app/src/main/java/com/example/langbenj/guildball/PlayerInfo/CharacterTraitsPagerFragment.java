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


public class CharacterTraitsPagerFragment extends Fragment {
    private String title;
    private int page;
    public static String font_size;

    // newInstance constructor for creating fragment with arguments
    public static CharacterTraitsPagerFragment newInstance() {
        CharacterTraitsPagerFragment mCharacterTraits = new CharacterTraitsPagerFragment();
        return mCharacterTraits;
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
                target_field.setTextSize(Integer.parseInt(font_size));
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

        View view = inflater.inflate(R.layout.character_traits_pager_layout, container, false);
        Player player = App.getCurrentPlayer();


        Ability[] character_abilities = player.getCharacterTraits();
        String temp_target;
        String temp_value;
        int i=0;
        font_size = player.getFontSize();
        for (Ability ability:character_abilities) {
            temp_target = "character_trait_name_" + (i + 1);
            temp_value = ability.getName();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");
            temp_target = "character_trait_description_" + (i + 1);
            temp_value = ability.getAbilityDescription();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");
            i++;
        }
        /*for (int i=0;i<character_abilities.length; i++) {
            temp_target = "character_trait_name_" + (i + 1);
            temp_value = character_abilities[i].getName();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");
            temp_target = "character_trait_description_" + (i + 1);
            temp_value = character_abilities[i].getAbilityDescription();
            setPlayerInfoDisplay(view, temp_target, temp_value, "text");
        }*/
        return view;
    }
}
