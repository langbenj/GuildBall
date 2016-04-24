package Countdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.langbenj.guildball.R;





public class CountdownFragment extends Fragment {
    private static View view;
    private static TextView countdown_1_textview;
    private static TextView countdown_2_textview;
    public static CountDownTimer countdown1;
    public static CountDownTimer countdown2;
    public static String countdown1_status="stop";
    public static String countdown2_status="stop";
    private static long countdown1_store=2700000;
    private static long countdown2_store=2700000;
    private static Button countdown_button_1;
    private static Button countdown_button_2;

    public View onCreateView(LayoutInflater fragmentInflater, ViewGroup target, Bundle savedInstanceState) {
        view = fragmentInflater.inflate(R.layout.timer_layout, target, false);
        countdown_1_textview = (TextView) view.findViewById(R.id.countdown1);
        countdown_1_textview.setRotation(180);
        countdown_2_textview = (TextView) view.findViewById(R.id.countdown2);
        countdown_1_textview.setText((formatTime(countdown1_store / 1000)).toString());
        countdown_2_textview.setText((formatTime(countdown2_store / 1000)).toString());

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
        countdown_button_1 = (Button) view.findViewById(R.id.timer_button_1);
        countdown_button_1.setRotation(180);
        countdown_button_2 = (Button) view.findViewById(R.id.timer_button_2);


        countdown_button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (countdown1_status=="stop") {
                    countdown1_status="play";
                    countdown2_status="stop";
                    startCountDown1();
                    countdown2.cancel();
                    countdown_button_1.setText("Stop");
                    countdown_button_2.setText("Start");
                }
                else  {
                    countdown2_status="play";
                    countdown1_status="stop";
                    countdown1.cancel();
                    startCountDown2();
                    countdown_button_1.setText("Start");
                    countdown_button_2.setText("Stop");
                }


            }

        });

        countdown_button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (countdown2_status=="stop") {
                    countdown2_status="play";
                    countdown1_status="stop";
                    countdown1.cancel();
                    startCountDown2();
                    countdown_button_1.setText("Start");
                    countdown_button_2.setText("Stop");
                }
                else {
                    countdown1_status="play";
                    countdown2_status="stop";
                    startCountDown1();
                    countdown2.cancel();
                    countdown_button_1.setText("Stop");
                    countdown_button_2.setText("Start");
                }
            }
        });
        return view;


    }

    public void onStop() {
        super.onStop();
      countdown1_status="stop";
      countdown2_status="stop";
      countdown1_store=2700000;
      countdown2_store=2700000;
        countdown1.cancel();
        countdown2.cancel();
    }


    private void startCountDown1() {
        countdown1 = new CountDownTimer(countdown1_store, 1000) {
        public void onTick(long millisUntilFinished) {
            countdown_1_textview.setText((formatTime(millisUntilFinished / 1000)).toString());
            countdown1_store=millisUntilFinished;
        }

        public void onFinish() {
            countdown_1_textview.setText("DONE!");
        }
        }.start();
    }

    private void startCountDown2() {
        countdown2 = new CountDownTimer(countdown2_store, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown_2_textview.setText((formatTime(millisUntilFinished / 1000)).toString());
                countdown2_store=millisUntilFinished;
            }

            public void onFinish() {
                countdown_2_textview.setText("Time's Up!");
            }
        }.start();
    }

    public static String formatTime(long seconds) {

        return String.format("%02d:%02d",((seconds / 60) % 60),seconds%60);
    }

}
