package ninja.jfr.travelswiss;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.search);
        final TextView textView = (TextView) findViewById(R.id.testText);


        final Calendar calendarDate = Calendar.getInstance();

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendarDate.set(year, month, dayOfMonth);

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText from = (EditText) findViewById(R.id.textFrom);
                EditText to = (EditText) findViewById(R.id.textTo);
                EditText time = (EditText) findViewById(R.id.textTime);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(calendarDate.getTime());
                textView.setText(date);
            }
        });
    }


}
