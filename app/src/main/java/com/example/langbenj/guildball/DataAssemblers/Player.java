package com.example.langbenj.guildball.DataAssemblers;

import android.content.Context;
import android.util.Log;

import com.example.langbenj.guildball.Helpers.App;

public class Player {
    private String mName;
    private String mTeam;
    private String mDefault_Image;
    private String mCustom_Image;
    private String mJog;
    private String mSprint;
    private String mTac;
    private String mKickDice;
    private String mKickDistance;
    private String mDefense;
    private String mArmor;
    private String mInfluenceGenerated;
    private String mMaxInfluence;
    private int mMaxLife;
    private int mCurrentLife;
    private int[] mIcySponge;
    private String mSeason;
    private String mMeleeRange;
    private String mBaseSize;
    private Ability[] mCharacterTraits;
    private Ability [] mHeroicPlays;
    private Ability [] mLegendaryPlays;
    private Ability [] mCharacterPlays;
    private String [] mPlaybookTop;
    private String [] mPlaybookBottom;
    private String mType;
    private String [] mAttributes;
    private String mFontSize;
    private String TAG="Player.java";

    //Constructor for creating a player object. Player's name that is passed in ties to the PlayerInformation.xml file

    public Player(String player_name) {

        //Please reference the App class. This is a workaround to get context outside of the main method
        Context context = App.getContext();

        int player_id = context.getResources().getIdentifier(player_name, "array", context.getPackageName());


            String[] playerArray = context.getResources().getStringArray(player_id);
            mName=playerArray[0];
            mTeam=playerArray[1];
            mDefault_Image=playerArray[2];
            mCustom_Image=playerArray[3];
            mJog=playerArray[4];
            mSprint=playerArray[5];
            mTac=playerArray[6];
            mKickDice=playerArray[7];
            mKickDistance=playerArray[8];
            mDefense=playerArray[9];
            mArmor=playerArray[10];
            mInfluenceGenerated=playerArray[11];
            mMaxInfluence=playerArray[12];
            mMaxLife=Integer.parseInt(playerArray[13]);
            mCurrentLife=mMaxLife;
            mIcySponge=stringArrayToInt(playerArray[14]);
            mSeason=playerArray[15];
            mMeleeRange=playerArray[16];
            mBaseSize=playerArray[17];
            mCharacterTraits=getAbilities(playerArray[18]);
            mHeroicPlays=getAbilities(playerArray[19]);
            mLegendaryPlays=getAbilities(playerArray[20]);
            mCharacterPlays=getAbilities(playerArray[21]);
            String tempSplitVal=playerArray[22];
            mPlaybookTop=tempSplitVal.split(",");
            tempSplitVal=playerArray[23];
            mPlaybookBottom=tempSplitVal.split(",");
            mType=playerArray[24];
            tempSplitVal=playerArray[25];
            mAttributes=tempSplitVal.split(",");
        mFontSize=playerArray[26];

    }

    //Converts the comma delimited ability string into an array which then is used to generate the Ability objects
    private Ability [] getAbilities (String passed_array) {
        String[] parseArray=passed_array.split(",");
        int array_length=parseArray.length;
        Ability[] outputArray = new Ability[array_length];
        for (int i = 0; i < array_length; i++) {
            outputArray[i] = new Ability(parseArray[i]);
        }
        return outputArray;
    }

    public int changeLife (int amount) {
        //conditionally change the life amount and return it
        if (mCurrentLife +amount>=0 && mCurrentLife +amount<= mMaxLife) {
            mCurrentLife +=amount;
        }
        return mCurrentLife;
    }

    //Utility method to convert a comma delimited string into an array of int
    public int[] stringArrayToInt(String passed_array) {
        String[] parseArray=passed_array.split(",");
        int array_length=parseArray.length;
        int [] outputArray=new int[array_length];
        for (int i=0; i<array_length; i++) {
            outputArray[i]=Integer.parseInt(parseArray[i]);
        }
        return outputArray;
    }

    //Getter and Setter methods for the interior values, yeah there's a lot of them.

    public String getName() {
        return mName;
    }

    public String getTeam() {
        return mTeam;
    }

    public String getDefault_Image() {
        return mDefault_Image;
    }

    public String getCustom_Image() {
        return mCustom_Image;
    }

    public void setCustom_Image(String mCustom_Image) {
        this.mCustom_Image = mCustom_Image;
    }

    public String getJog() {
        return mJog;
    }

    public String getSprint() {
        return mSprint;
    }

    public String getTac() {
        return mTac;
    }

    public String getKickDice() {
        return mKickDice;
    }

    public String getKickDistance() {
        return mKickDistance;
    }

    public String getDefense() {
        return mDefense;
    }

    public String getArmor() {
        return mArmor;
    }

    public String getInfluenceGenerated() {
        return mInfluenceGenerated;
    }

    public String getMaxInfluence() {
        return mMaxInfluence;
    }

    public int getMaxLife() {
        return mMaxLife;
    }

    public int getCurrentLife() {
        return mCurrentLife;
    }

    public void setCurrentLife(int mCurrentLife) {
        this.mCurrentLife = mCurrentLife;
    }

    public int[] getIcySponge() {
        return mIcySponge;
    }

    public String getSeason() {
        return mSeason;
    }

    public String getMeleeRange() {
        return mMeleeRange;
    }

    public String getBaseSize() {
        return mBaseSize;
    }

    public Ability[] getCharacterTraits() {
        return mCharacterTraits;
    }

    public Ability[] getHeroicPlays() {
        return mHeroicPlays;
    }

    public Ability[] getLegendaryPlays() {
        return mLegendaryPlays;
    }

    public Ability[] getCharacterPlays() {
        return mCharacterPlays;
    }

    public String[] getPlaybookTop() {
        return mPlaybookTop;
    }

    public String[] getPlaybookBottom() {
        return mPlaybookBottom;
    }

    public String getType() {
        return mType;
    }

    public String[] getAttributes() {
        return mAttributes;
    }

    public String getFontSize() {
        return mFontSize;
    }
}
