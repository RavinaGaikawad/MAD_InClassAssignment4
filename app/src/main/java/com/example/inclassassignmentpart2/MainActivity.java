package com.example.inclassassignmentpart2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tv_minresult;
    TextView tv_maxresult;
    TextView tv_avgresult;
    SeekBar sb_runseek;
    Button bt_generate;
    int number;
    TextView tv_number;
    ProgressBar pb_loading;
    LinearLayout ll_progress;
    ArrayList<Double> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("InClass4");
        tv_avgresult = findViewById(R.id.tv_avgresult);
        tv_maxresult = findViewById(R.id.tv_maxresult);
        tv_minresult = findViewById(R.id.tv_minresult);
        sb_runseek = findViewById(R.id.sb_runseek);
        pb_loading = findViewById(R.id.pb_loading);
        ll_progress = findViewById(R.id.ll_progress);
        bt_generate = findViewById(R.id.bt_generate);
        tv_number = findViewById(R.id.tv_number);

        sb_runseek.setMax(10);

        sb_runseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (progress == 1) {
                    tv_number.setText(progress + " Time");
                } else {
                    tv_number.setText(progress + " Times");
                }
                number = progress;
            }
        });

        bt_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (number == 0) {
                    Toast.makeText(MainActivity.this, "Please select the complexity level.", Toast.LENGTH_SHORT).show();
                } else {
                    new getNumberAsyncTask().execute(1000);
                }
            }
        });
    }


    public class getNumberAsyncTask extends AsyncTask<Integer, Integer, ArrayList<Double>> {

        ProgressBar pbLoading = new ProgressBar(MainActivity.this);

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {

            Double sum = 0.0;
            Double avg;
            Double max = arrayList.get(0);
            Double min = arrayList.get(0);
            pbLoading.setVisibility(View.GONE);
            ll_progress.setVisibility(View.GONE);

            //Average calculate
            for (int i = 0; i < doubles.size(); i++) {
                sum += doubles.get(i);
            }
            avg = sum / doubles.size();

            //minimum calculate
            for (int i = 1; i < doubles.size(); i++) {
                if (doubles.get(i) > max) {
                    max = doubles.get(i);
                }
            }

            for (int i = 1; i < doubles.size(); i++) {
                if (doubles.get(i) < min) {
                    min = doubles.get(i);
                }
            }

            tv_minresult.setText(min.toString());
            tv_maxresult.setText(max.toString());
            tv_avgresult.setText(avg.toString());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {

            arrayList = HeavyWork.getArrayNumbers(number);
            return arrayList;
        }

        @Override
        protected void onPreExecute() {
            ll_progress.setVisibility(View.VISIBLE);
            pb_loading.setVisibility(View.VISIBLE);
        }
    }
}
