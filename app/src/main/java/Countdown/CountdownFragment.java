package Countdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.langco.langbenj.guildball.Helpers.App;
import com.langco.langbenj.guildball.Helpers.StringFragmentBusEvent;
import com.langco.langbenj.guildball.R;





public class CountdownFragment extends Fragment {
    private static View view;
    private static TextView countdown_1_textview;
    private static TextView countdown_2_textview;
    private static TextView countdown_time_view;
    private static TextView overtime_1;
    private static TextView overtime_2;
    public static CountDownTimer countdown1;
    public static CountDownTimer countdown2;
    public static String countdown1_status="stop";
    public static String countdown2_status="stop";
    private static long countdown1_store=2700000;
    private static long countdown2_store=2700000;
    private static Button countdown_button_1;
    private static Button countdown_button_2;
    private static Button pause_button;
    private static String timeout_1 = "no";
    private static String timeout_2 = "no";
    private static Button countdown_time_set;

    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {


        App.bus.post(new StringFragmentBusEvent("keep_screen_on"));
        view = fragmentInflater.inflate(R.layout.timer_layout, target, false);
        countdown_1_textview = (TextView) view.findViewById(R.id.countdown1);
        countdown_1_textview.setRotation(180);
        countdown_2_textview = (TextView) view.findViewById(R.id.countdown2);
        countdown_1_textview.setText((formatTime(countdown1_store / 1000)).toString());
        countdown_2_textview.setText((formatTime(countdown2_store / 1000)).toString());

        overtime_1 = (TextView) view.findViewById(R.id.overtime1);
        overtime_2 = (TextView) view.findViewById(R.id.overtime2);

        countdown1=new CountDownTimer(countdown1_store, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown_1_textview.setText((formatTime(millisUntilFinished / 1000)).toString());
                countdown1_store=millisUntilFinished;
            }

            public void onFinish() {
                countdown_2_textview.setText("DONE!");
            }
        };

        countdown2=new CountDownTimer(countdown2_store, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown_2_textview.setText((formatTime(millisUntilFinished / 1000)).toString());
                countdown2_store=millisUntilFinished;
            }

            public void onFinish() {
                countdown_2_textview.setText("DONE!");
            }
        };


        countdown_time_view = (TextView) view.findViewById(R.id.time_value);
        countdown_time_set = (Button) view.findViewById(R.id.set_time_button);
        countdown_time_set.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    int time = (60000*Integer.parseInt(countdown_time_view.getText().toString()));
                    countdown1_store = (long) time;
                    countdown2_store = (long) time;
                    countdown_1_textview.setText((formatTime(countdown1_store / 1000)).toString());
                    countdown_2_textview.setText((formatTime(countdown2_store / 1000)).toString());
                }
                catch(Exception e) {
                    Toast.makeText(App.getContext(), "Not a valid time", Toast.LENGTH_SHORT).show();
                }
            }
        });



        countdown_button_1 = (Button) view.findViewById(R.id.timer_button_1);
        countdown_button_1.setRotation(180);
        countdown_button_2 = (Button) view.findViewById(R.id.timer_button_2);
        pause_button = (Button) view.findViewById(R.id.pause_button);

        countdown_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (countdown1_status=="stop") {
                    countdown1_status="play";
                    countdown2_status="stop";
                    if (timeout_1=="yes") {
                        countdown_1_textview.setText((formatTime(countdown1_store / 1000)).toString());
                        countdown1_store=60000;
                    }
                    startCountDown1();
                    countdown2.cancel();
                    countdown_button_1.setText("Stop");
                    countdown_button_2.setText("Start");
                }
                else  {
                    countdown2_status="play";
                    countdown1_status="stop";
                    if (timeout_2=="yes") {
                        countdown2_store=60000;
                        countdown_2_textview.setText((formatTime(countdown2_store / 1000)).toString());
                    }
                    countdown1.cancel();
                    startCountDown2();
                    countdown_button_1.setText("Start");
                    countdown_button_2.setText("Stop");
                }


            }

        });

        countdown_button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countdown_time_view.setVisibility(View.INVISIBLE);
                countdown_time_set.setVisibility(View.INVISIBLE);
                if (countdown2_status=="stop" ) {
                    countdown2_status="play";
                    countdown1_status="stop";
                    if (timeout_2=="yes") {
                        countdown2_store=60000;
                        countdown_2_textview.setText((formatTime(countdown2_store / 1000)).toString());
                    }
                    countdown1.cancel();
                    startCountDown2();
                    countdown_button_1.setText("Start");
                    countdown_button_2.setText("Stop");
                }
                else {
                    countdown1_status="play";
                    countdown2_status="stop";
                    if (timeout_1=="yes") {
                        countdown1_store=60000;
                        countdown_1_textview.setText((formatTime(countdown1_store / 1000)).toString());
                    }
                    startCountDown1();
                    countdown2.cancel();
                    countdown_button_1.setText("Stop");
                    countdown_button_2.setText("Start");
                }
            }
        });

        pause_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pause_button.getText().equals("Pause")) {
                    pause_countdown();
                }
                else {
                    reset_timers();
                }
            }

        });

        return view;


    }



    public void onPause() {
        super.onPause();
        App.bus.post(new StringFragmentBusEvent("let_screen_sleep"));
        countdown1_status="stop";
        countdown2_status="stop";
        countdown_button_2.setText("Start");
        countdown_button_1.setText("Start");
        countdown1.cancel();
        countdown2.cancel();

    }

    public void onResume() {
        super.onResume();
        countdown1_status="stop";
        countdown2_status="stop";
        countdown_button_2.setText("Start");
        countdown_button_1.setText("Start");
        countdown_1_textview.setText((formatTime(countdown1_store / 1000)));
        countdown_2_textview.setText((formatTime(countdown2_store / 1000)));
    }

    public void onStop() {
        super.onStop();
        App.bus.post(new StringFragmentBusEvent("let_screen_sleep"));
        countdown1_status="stop";
        countdown2_status="stop";
        countdown_button_2.setText("Start");
        countdown_button_1.setText("Start");
        countdown1.cancel();
        countdown2.cancel();

    }

    private void reset_timers() {
        countdown1_status="stop";
        countdown2_status="stop";
        countdown1_store=2700000;
        countdown2_store=2700000;
        countdown1.cancel();
        countdown2.cancel();
        countdown_1_textview.setText((formatTime(countdown1_store / 1000)));
        countdown_2_textview.setText((formatTime(countdown2_store / 1000)));
        pause_button.setText("Pause");
        pause_button.setVisibility(View.INVISIBLE);
        countdown_time_view.setVisibility(View.VISIBLE);
        countdown_time_set.setVisibility(View.VISIBLE);

    }

    private void pause_countdown() {
        countdown1.cancel();
        countdown2.cancel();
        countdown1_status="stop";
        countdown2_status="stop";
        countdown_button_2.setText("Start");
        countdown_button_1.setText("Start");
        pause_button.setText("Reset");
    }


    private void startCountDown1() {
        pause_button.setVisibility(View.VISIBLE);
        pause_button.setText("Pause");
        countdown_time_view.setVisibility(View.INVISIBLE);
        countdown_time_set.setVisibility(View.INVISIBLE);
        countdown1 = new CountDownTimer(countdown1_store, 1000) {
        public void onTick(long millisUntilFinished) {
            countdown_1_textview.setText((formatTime(millisUntilFinished / 1000)).toString());
            countdown1_store=millisUntilFinished;

        }

        public void onFinish() {
            timeout_1="yes";
            countdown1_store=60000;
            countdown_1_textview.setText((formatTime(countdown1_store / 1000)).toString());
            countdown1.cancel();
            countdown_button_2.setText("Stop");
            countdown_button_1.setText("Start");
            countdown1_status="stop";
            countdown2_status="play";
            overtime_1.setVisibility(View.VISIBLE);
            startCountDown2();
            countdown1.cancel();

        }
        }.start();
    }

    private void startCountDown2() {
        pause_button.setVisibility(View.VISIBLE);
        pause_button.setText("Pause");
        countdown_time_view.setVisibility(View.INVISIBLE);
        countdown_time_set.setVisibility(View.INVISIBLE);
        countdown2 = new CountDownTimer(countdown2_store, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown_2_textview.setText((formatTime(millisUntilFinished / 1000)).toString());
                countdown2_store=millisUntilFinished;

            }

            public void onFinish() {
                timeout_2="yes";
                countdown2_store=60000;
                countdown_2_textview.setText((formatTime(countdown2_store / 1000)).toString());
                countdown_button_1.setText("Stop");
                countdown_button_2.setText("Start");
                countdown1_status="play";
                countdown2_status="stop";
                startCountDown1();
                countdown2.cancel();
                overtime_2.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public static String formatTime(long seconds) {

        return String.format("%02d:%02d",((seconds / 60) % 60),seconds%60);
    }

}
