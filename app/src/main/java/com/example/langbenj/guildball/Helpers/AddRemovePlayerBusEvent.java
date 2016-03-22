package com.example.langbenj.guildball.Helpers;


public class AddRemovePlayerBusEvent {
    private String[] mParameterArray;

    //These bus events can probably be refactored into one or two events TODO
    public AddRemovePlayerBusEvent (String[] passedParameters) {
        mParameterArray=passedParameters;
    }

    public String [] getParameter() {

        return mParameterArray;
    }
}
