package com.langco.langbenj.guildball.Helpers;


public class PlayTeamListBusEvent {
    private int mParameter;

    //The current method only handles strings. Need to change it so that it accepts any object and casts it on the other end TODO
    public PlayTeamListBusEvent(int passedItem) {
        mParameter=passedItem;
    }

    public int getParameter() {
        return mParameter;
    }
}
