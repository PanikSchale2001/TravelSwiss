package ninja.jfr.travelswiss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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

        addTolist("a","b", "c", "c");

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
                                TextView text = findViewById(R.id.testText);
                                text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
                                TextView text = findViewById(R.id.testText);
                                text.setText(sHour + ":" + sMinute);
                                time.setText(sHour + ":" + sMinute);
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
                TextView time = findViewById(R.id.textTime);
                String timeString = time.getText().toString();
                TextView date = findViewById(R.id.textDate);
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
                launchActivity(fromString, toString, timeString, dateString);
                addFragment(fromString, toString, timeString, dateString);

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

    public void addFragment(String from, String to, String time, String date) {
        Intent intent = new Intent(this, ConnectionBoard.class);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        intent.putExtra("time", time);
        intent.putExtra("date", date);
        startActivity(intent);
    }


    public void addTolist(String from, String to, String time, String date) {

        final ListView listview = (ListView) findViewById(R.id.listview);

        ConnectionService connectionService = new ConnectionService();
        List<List<Connection>> listOfConnection = (List<List<Connection>>) connectionService.getAllConnections(from, to, time, date);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };


        final List<List<Connection>> connectionList = new ArrayList<>();

        for (int i = 0; i< listOfConnection.size(); i++) {
            //the same
        }


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }


        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fragment_connection, parent, false);
            TextView from = view.findViewById(R.id.from);
            TextView to = view.findViewById(R.id.to);
            TextView fromTime = view.findViewById(R.id.fromTime);
            TextView toTime = view.findViewById(R.id.toTime);
            TextView travelTime = view.findViewById(R.id.travelTime);
            TextView fromTrack = view.findViewById(R.id.fromTrack);
            TextView toTrack = view.findViewById(R.id.toTrack);

            return super.getView(position, convertView, parent);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
