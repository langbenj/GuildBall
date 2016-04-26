package com.example.langbenj.guildball;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.langbenj.guildball.R;





public class Credits extends Fragment {


    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {
        View view = fragmentInflater.inflate(R.layout.credits_layout, target, false);

        return view;


    }

}
