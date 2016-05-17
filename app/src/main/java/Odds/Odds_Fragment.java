package Odds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.langco.langbenj.guildball.R;

import java.util.ArrayList;

public class Odds_Fragment extends Fragment {
    private static View view;

    @Override
    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {
        view = fragmentInflater.inflate(R.layout.odds_layout, target, false);
        Button target_up_button = (Button) view.findViewById(R.id.target_up);
        Button target_down_button = (Button) view.findViewById(R.id.target_down);
        Button dice_up_button = (Button) view.findViewById(R.id.dice_up);
        Button dice_down_button = (Button) view.findViewById(R.id.dice_down);
        Button submit_button = (Button) view.findViewById(R.id.submit_button);




        target_up_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView target_field = (TextView) view.findViewById(R.id.target_text);
                setUpDownFields(target_field, 1, 2, 6);
            }
        });

        target_down_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView target_field = (TextView) view.findViewById(R.id.target_text);
                setUpDownFields(target_field,-1,2,6);
            }
        });

        dice_up_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView dice_field = (TextView) view.findViewById(R.id.dice_text);
                setUpDownFields(dice_field, 1, 1, 13);
            }
        });

        dice_down_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView dice_field = (TextView) view.findViewById(R.id.dice_text);
                setUpDownFields(dice_field,-1,1,13);
            }
        });



        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView target_field = (TextView) view.findViewById(R.id.target_text);
                TextView dice_field = (TextView) view.findViewById(R.id.dice_text);



                int target = Integer.parseInt(target_field.getText().toString());
                int dice = Integer.parseInt(dice_field.getText().toString());

                ArrayList<Double> probability_list = new ArrayList<>();
                ArrayList<Double> compiled_probability_list = new ArrayList<>();

                for (int i=0; i<=13; i++) {
                    probability_list.add ( (calculateProbability(target, i, dice)*100));
                }
                double prob;
                for (int x=0; x<=dice; x++) {
                    prob=0;
                    for (int y=x; y<=dice; y++) {
                        prob = probability_list.get(y)+prob;
                    }
                    compiled_probability_list.add(prob);

                }


                String  result = "100%";
                TextView result_textView = (TextView) view.findViewById(R.id.result_0);
                result_textView.setText(result);


                result = (String.format( "%.1f", compiled_probability_list.get(1)))+"%";
                if (result.equals("100.0%")) {
                    result="100%";
                }
                result_textView = (TextView) view.findViewById(R.id.result_1);
                result_textView.setText(result);

                if (2>dice) {
                    result = "";
                }
                else {


                    result = (String.format( "%.1f", compiled_probability_list.get(2)))+"%";
                    if (result.equals("100.0%")) {
                        result="100%";
                    }
                }
                result_textView = (TextView) view.findViewById(R.id.result_2);
                result_textView.setText(result);

                if (3>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(3)))+"%";
                    if (result.equals("100.0%")) {
                        result="100%";
                    }
                }
                result_textView = (TextView) view.findViewById(R.id.result_3);
                result_textView.setText(result);

                if (4>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(4)))+"%";
                    if (result.equals("100.0%")) {
                        result="100%";
                    }
                }
                result_textView = (TextView) view.findViewById(R.id.result_4);
                result_textView.setText(result);

                if (5>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(5)))+"%";
                    if (result.equals("100.0%")) {
                        result="100%";
                    }
                }
                result_textView = (TextView) view.findViewById(R.id.result_5);
                result_textView.setText(result);

                if (6>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(6)))+"%";
                    if (result.equals("100.0%")) {
                        result="100%";
                    }
                }
                result_textView = (TextView) view.findViewById(R.id.result_6);
                result_textView.setText(result);

                if (7>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(7)))+"%";
                    if (result.equals("100.0%")) {
                        result="100%";
                    }
                }
                result_textView = (TextView) view.findViewById(R.id.result_7);
                result_textView.setText(result);


                if (8>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(8)))+"%";

                }
                result_textView = (TextView) view.findViewById(R.id.result_8);
                result_textView.setText(result);


                if (9>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(9)))+"%";

                }
                result_textView = (TextView) view.findViewById(R.id.result_9);
                result_textView.setText(result);

                if (10>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(10)))+"%";

                }
                result_textView = (TextView) view.findViewById(R.id.result_10);
                result_textView.setText(result);

                if (11>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(11)))+"%";

                }
                result_textView = (TextView) view.findViewById(R.id.result_11);
                result_textView.setText(result);


                if (12>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(12)))+"%";
                }
                result_textView = (TextView) view.findViewById(R.id.result_12);
                result_textView.setText(result);

                if (13>dice) {
                    result = "";
                }
                else {
                    result = (String.format( "%.1f", compiled_probability_list.get(13)))+"%";

                }
                result_textView = (TextView) view.findViewById(R.id.result_13);
                result_textView.setText(result);

            }
        });


        return view;

    }

    public static void setUpDownFields (TextView targetView, int ammount, int lowerBound, int upperBound) {
        int current_target_field_value = Integer.parseInt((String) targetView.getText());
       if (ammount<0) {
           if (current_target_field_value >lowerBound) {
               current_target_field_value=current_target_field_value+ammount;
           }
       }
        else {
           if ( current_target_field_value <upperBound) {
               current_target_field_value=current_target_field_value+ammount;
           }
       }
        targetView.setText(String.valueOf(current_target_field_value));
    }


    public float fact (float n){
        float result=1;
        for (int i=1; i<=n; i++){
            result=result*i;
        }
        return result;
    }

    public double calculateProbability(int targetNumber, int successes, int dice) {
        double result=0;
        double r=Math.pow(((6.0-targetNumber+1.0)/6.0),successes );
        result= ((fact(dice) / (fact(successes) * (fact(dice - successes)))))*( Math.pow(((6.0-targetNumber+1.0)/6.0),successes ))*( Math.pow(((6.0-(6.0-targetNumber+1.0))/6.0),(dice-successes)) );
        return result;
    }
    public void onStart() {
        super.onStart();
    }


}

