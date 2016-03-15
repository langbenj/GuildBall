package com.example.langbenj.guildball.Helpers;


public class TeamBuilderListFragmentBusEvent {
    private String mParameter;

    //The current method only handles strings. Need to change it so that it accepts any object and casts it on the other end TODO
    public TeamBuilderListFragmentBusEvent(String passedItem) {
        mParameter=passedItem;
    }

    public String getParameter() {
        return mParameter;
    }
}
