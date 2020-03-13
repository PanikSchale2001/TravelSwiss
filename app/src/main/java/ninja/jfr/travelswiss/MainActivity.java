package ninja.jfr.travelswiss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView date = findViewById(R.id.textDate);
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
                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        TextView time = findViewById(R.id.textTime);
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
                                time.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minute, true);
                picker.show();
            }
        });

        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            List<Connection> connection = null;
            List<List<Connection>> listOfAllConnections = null;

            @Override
            public void onClick (View v){
                EditText from = findViewById(R.id.textFrom);
                String fromString = from.getText().toString();
                EditText to = findViewById(R.id.textTo);
                String toString = to.getText().toString();
                TextView time = findViewById(R.id.textTime);
                String timeString = time.getText().toString();
                TextView date = findViewById(R.id.textDate);
                String dateString = date.getText().toString();

                listView= findViewById(R.id.listView);
                ConnectionService connectionService = new ConnectionService();

                try {
                    Future<List<List<Connection>>> task = connectionService.getAllConnections(fromString, toString, dateString, timeString);
                    listOfAllConnections = task.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                departureDestination = new String[listOfAllConnections.size()];
                departureTime = new String[listOfAllConnections.size()];
                departurePlatform = new String[listOfAllConnections.size()];
                arrivalDestination = new String[listOfAllConnections.size()];
                arrivalTime = new String[listOfAllConnections.size()];
                arrivalPlatform = new String[listOfAllConnections.size()];
                travelTime = new String[listOfAllConnections.size()];

                for (int i = 0; i < listOfAllConnections.size(); i++){
                    connection = listOfAllConnections.get(i);

                    Connection firstConnection = connection.get(0);
                    departureDestination[i] = firstConnection.getDepartureDestination().getName();
                    departureTime[i] = firstConnection.getDepartureDate();
                    departurePlatform[i] = firstConnection.getDeparturePlatform();

                    Connection lastConnection = null;
                    for (int j = 0;j<connection.size();j++){
                        lastConnection = connection.get(j);
                    }

                    arrivalDestination[i] = lastConnection.getArrivalDestination().getName();
                    arrivalTime[i] = lastConnection.getArrivalDate();
                    arrivalPlatform[i] = lastConnection.getArrivalPlatform();
                }
                MyAdapter myAdapter = new MyAdapter(MainActivity.this, departureDestination, departureTime,departurePlatform,arrivalDestination,arrivalTime,arrivalPlatform,travelTime);
                listView.setAdapter(myAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    //Todo funktion wenn man auf die Verbindung klickt.
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0){
                            launchActivity(listOfAllConnections.get(position));
                        }
                        if (position == 1){
                            launchActivity(listOfAllConnections.get(position));
                        }
                        if (position == 2){
                            launchActivity(listOfAllConnections.get(position));
                        }
                        if (position == 3){
                            launchActivity(listOfAllConnections.get(position));
                        }
                    }
                });
            }
        });
}

    public String convertDate(Long time){
        Date newDate = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(newDate);

    }

    public void launchActivity(List<Connection> connection) {
        Log.e("test", String.valueOf(connection));

        Gson gson = new Gson();
        String json = gson.toJson(connection);

        Intent intent = new Intent(this, ConnectionBoard.class);
        intent.putExtra("connection", json);
        startActivity(intent);
    }

    //TODO Objekte erstellen und mit werten befüllen
    ListView listView;
    String[] departureDestination;
    String[] departureTime;
    String[] departurePlatform;
    String[] arrivalDestination;
    String[] arrivalTime;
    String[] arrivalPlatform;
    String[] travelTime;

class MyAdapter extends ArrayAdapter<String>{
    Context context;
    String rDepartureDestination[];
    String rDepartureTime[];
    String rDeparturePlatform[];
    String rArrivalDestination[];
    String rArrivalTime[];
    String rArrivalPlatform[];
    String rTravelTime[];




    MyAdapter (Context c, String departureDestination[], String departureTime[], String departurePlatform[], String arrivalDestination[], String arrivalTime[], String arrivalPlatform[], String travelTime[]){
        super(c,R.layout.row,R.id.departureDestination, departureDestination);
        this.context = c;
        this.rDepartureDestination = departureDestination;
        this.rDepartureTime = departureTime;
        this.rDeparturePlatform = departurePlatform;
        this.rArrivalDestination = arrivalDestination;
        this.rArrivalTime = arrivalTime;
        this.rArrivalPlatform = arrivalPlatform;
        this.rTravelTime = travelTime;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent,false);
        TextView myDepartureDestination = row.findViewById(R.id.departureDestination);
        TextView myDepartureTime = row.findViewById(R.id.departureTime);
        TextView myDeparturePlatform = row.findViewById(R.id.departurePlatform);
        TextView myTravelTime = row.findViewById(R.id.travelTime);
        TextView myArrivalDestination = row.findViewById(R.id.arrivalDestination);
        TextView myArrivalTime = row.findViewById(R.id.arrivalTime);
        TextView myArrivalPlatform = row.findViewById(R.id.arrivalPlatform);

        myDepartureDestination.setText(rDepartureDestination[position]);
        myDepartureTime.setText(rDepartureTime[position]);
        myDeparturePlatform.setText(rDeparturePlatform[position]);
        myTravelTime.setText(rTravelTime[position]);
        myArrivalDestination.setText(rArrivalDestination[position]);
        myArrivalTime.setText(rArrivalTime[position]);
        myArrivalPlatform.setText(rArrivalPlatform[position]);

        return row;
    }
}

}
