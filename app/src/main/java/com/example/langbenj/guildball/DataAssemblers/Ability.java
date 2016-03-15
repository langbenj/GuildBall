package com.example.langbenj.guildball.DataAssemblers;

import android.content.Context;

import com.example.langbenj.guildball.Helpers.App;

public class Ability {

    private String mType;
    private String mName;
    private String mAbilityDescription;
    private String mCost;
    private String mGuildBallCost;
    private String mRange;
    private String mZones;
    private String mSustain;

    public Ability(String play_name) {

        if (play_name.length() != 0){

            //Please reference the App class. This is a workaround to get context outside of the main method
            Context context = App.getContext();

            int play_id = context.getResources().getIdentifier(play_name, "array", context.getPackageName());
            String[] playArray = context.getResources().getStringArray(play_id);

            int play_length = playArray.length;
            mType = playArray[0];
            mName = playArray[1];
            mAbilityDescription = playArray[2];
            if (play_length > 3) {
                //This check allows the same object to be used for all types of abilities. Character Plays are the only ones with 8 items.
                mCost = playArray[3];
                mGuildBallCost = playArray[4];
                mRange = playArray[5];
                mZones = playArray[6];
                mSustain = playArray[7];
            }
        }
    }

    //Get and set methods for properties of the Class

    public String getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public String getAbilityDescription() {
        return mAbilityDescription;
    }

    public String getCost() {
        return mCost;
    }

    public String getGuildBallCost() {
        return mGuildBallCost;
    }

    public String getRange() {
        return mRange;
    }

    public String getZones() {
        return mZones;
    }

    public String getSustain() {
        return mSustain;
    }

}