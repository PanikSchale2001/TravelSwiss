package ninja.jfr.travelswiss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

/*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText date = findViewById(R.id.textDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                TextView text = findViewById(R.id.testText);
                                text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        EditText time = findViewById(R.id.textTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);
                // date picker dialog
                TimePickerDialog picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TextView text = findViewById(R.id.testText);
                                text.setText(hour + ":" + minute);
                                time.setText(hour + ":" + minute);
                            }
                        }, hour, minute, true);
                picker.show();
            }
        });

        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v){
                EditText from = findViewById(R.id.textFrom);
                String fromString = from.getText().toString();
                EditText to = findViewById(R.id.textTo);
                String toString = to.getText().toString();
                EditText time = findViewById(R.id.textTime);
                String timeString = time.getText().toString();
                EditText date = findViewById(R.id.textDate);
                String dateString = date.getText().toString();
                /*
                try {
                    List<List<Connection>> connections = new ConnectionService().getAllConnections(fromString, toString, dateString, timeString).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                */
                Log.e("from", fromString +" "+toString+" "+timeString+" "+dateString);
                ConnectionService connectionService = new ConnectionService();
                connectionService.getAllConnections(fromString,toString,dateString,timeString);
                launchActivity(fromString, toString, timeString, dateString);

            }
        });
}

    public void launchActivity(String from, String to, String time, String date) {
        Intent intent = new Intent(this, ConnectionBoard.class);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        intent.putExtra("time", time);
        intent.putExtra("date", date);
        startActivity(intent);
    }


}
