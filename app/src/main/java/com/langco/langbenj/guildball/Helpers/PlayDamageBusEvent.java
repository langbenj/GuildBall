package com.langco.langbenj.guildball.Helpers;


import com.langco.langbenj.guildball.DataAssemblers.Player;

public class PlayDamageBusEvent {
    private Player mParameter;

    //The current method only handles strings. Need to change it so that it accepts any object and casts it on the other end TODO
    public PlayDamageBusEvent(Player passedItem) {
        mParameter=passedItem;
    }

    public Player getParameter() {
        return mParameter;
    }
}
