package ninja.jfr.travelswiss;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ConnectionBoard extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_board);

        TextView textView = findViewById(R.id.display);
        Intent intent = getIntent();

        String date = intent.getStringExtra("date");
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String time = intent.getStringExtra("time");


        textView.setText("Date: "+date+" |from: "+from+" |to: "+to+" |time: "+time);

    }
}
