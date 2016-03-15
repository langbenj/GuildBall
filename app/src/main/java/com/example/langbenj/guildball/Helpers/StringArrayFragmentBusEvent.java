package com.example.langbenj.guildball.Helpers;


public class StringArrayFragmentBusEvent {
    private String[] mParameter;

    //The current method only handles strings. Need to change it so that it accepts any object and casts it on the other end TODO
    public StringArrayFragmentBusEvent(String[] passedItem) {
        mParameter=passedItem;
    }

    public String[] getParameter() {
        return mParameter;
    }
}
