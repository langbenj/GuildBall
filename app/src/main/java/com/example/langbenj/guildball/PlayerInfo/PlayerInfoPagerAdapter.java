package com.example.langbenj.guildball.PlayerInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.langbenj.guildball.DataAssemblers.Ability;
import com.example.langbenj.guildball.DataAssemblers.Player;
import com.example.langbenj.guildball.Helpers.App;


public class PlayerInfoPagerAdapter extends FragmentPagerAdapter {

    private int mNumberOfItems;
    private Ability [] mCharacterPlays;
    private Ability [] mCharacterTraits;
    private Ability [] mHeroicPlays;
    private Ability [] mLegendaryPlays;


        public PlayerInfoPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            mNumberOfItems=0;
            Player current_player= App.getCurrentPlayer();
            mCharacterPlays = current_player.getCharacterPlays();
            if (mCharacterPlays[0].getName()!=null) {
                mNumberOfItems++;
            }
            mCharacterTraits = current_player.getCharacterTraits();
            if (mCharacterTraits[0].getName()!=null) {
                mNumberOfItems++;
            }
            mHeroicPlays = current_player.getHeroicPlays();
            if (mHeroicPlays[0].getName()!=null) {
                mNumberOfItems++;
            }
            mLegendaryPlays = current_player.getLegendaryPlays();
            if (mLegendaryPlays[0].getName()!=null) {
                mNumberOfItems++;
            }


            return mNumberOfItems;
        }
        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {



                    if (mCharacterPlays[0].getName()!=null && position==0) {
                        return CharacterPlayPagerFragment.newInstance();
                    }
                   else if (mCharacterTraits[0].getName()!=null && position==1) {
                        return CharacterTraitsPagerFragment.newInstance();
                    }
                    else if (mHeroicPlays[0].getName()!=null && position==2) {
                        return HeroicPlayPagerFragment.newInstance();
                   }
                    else if (mLegendaryPlays[0].getName()!=null && position==2) {
                        return LegendaryPlayPagerFragment.newInstance();
                    }
                    else if (mLegendaryPlays[0].getName()!=null && position==3) {
                        return LegendaryPlayPagerFragment.newInstance();
                    }
                    else {
                        return null;
                    }



        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            if (mCharacterPlays[0].getName()!=null && position==0) {
                return "Character Plays";
            }
            else if (mCharacterTraits[0].getName()!=null && position==1) {
                return "Character Traits";
            }
            else if (mHeroicPlays[0].getName()!=null && position==2) {
                return "Heroic Play";
            }
            else if (mLegendaryPlays[0].getName()!=null && position==2) {
                return "Legendary Play";
            }
            else if (mLegendaryPlays[0].getName()!=null && position==3) {
                return "Legendary Play";
            }
            else {
                return "None";
            }

        }


    }


