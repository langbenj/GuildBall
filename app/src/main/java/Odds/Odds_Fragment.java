package Odds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.langbenj.guildball.Helpers.App;
import com.example.langbenj.guildball.R;

import java.util.ArrayList;
import java.util.List;

public class Odds_Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        View view = fragmentInflater.inflate(R.layout.odds_layout, target, false);


        return view;

    }


    public void onStart() {
        super.onStart();
    }


}

