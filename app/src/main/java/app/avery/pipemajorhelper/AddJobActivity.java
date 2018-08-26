package app.avery.pipemajorhelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddJobActivity extends AppCompatActivity implements OnPlayerItemClick, OnMusicItemClick {
    String bandName;
    String jobName;
    List<String> attendanceList = new ArrayList<String>();
    List<String> musicList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        jobName = i.getStringExtra("Job");
        bandName = i.getStringExtra("Band");

        setContentView(R.layout.activity_add_job);

        TextView jobID = findViewById(R.id.new_job_name);
        jobID.setText(jobName);

        RecyclerView setRecyclerView;
        RecyclerView playerRecyclerView;

        setRecyclerView = findViewById(R.id.set_lst);
        playerRecyclerView = findViewById(R.id.player_lst);

        LinearLayoutManager setRecyclerLayoutManager = new LinearLayoutManager(this);
        setRecyclerView.setLayoutManager(setRecyclerLayoutManager);
        SetRecyclerViewAdapter setRecyclerViewAdapter = new SetRecyclerViewAdapter(getSets(), this, this);
        setRecyclerView.setAdapter(setRecyclerViewAdapter);

        LinearLayoutManager playerRecyclerLayoutManager = new LinearLayoutManager(this);
        playerRecyclerView.setLayoutManager(playerRecyclerLayoutManager);
        PlayerRecyclerViewAdapter playerRecyclerViewAdapter = new PlayerRecyclerViewAdapter(getPlayers(), this, this);
        playerRecyclerView.setAdapter(playerRecyclerViewAdapter);
    }

    public List<String> getSets(){
        List<String> setList = new ArrayList<String>();
        //TODO: GET THIS INFORMATION FROM DATABASE
        //Testing info below:
        setList.add("First Set");
        setList.add("Balmoral Set");
        setList.add("Farewell to the Creeks Set");

        return setList;
    }

    public List<String> getPlayers(){
        List<String> playerList = new ArrayList<String>();
        //TODO: GET THIS INFORMATION FROM DATABASE
        //Testing info below:
        playerList.add("Avery Bowen");
        playerList.add("Mark Bartfeld");
        playerList.add("Frank George");

        return playerList;
    }

    @Override
    public void onPlayerClick(String name) {
        attendanceList.add(name);
    }

    @Override
    public void onPlayerUnclick(String name) {
        attendanceList.remove(name);
    }

    @Override
    public void onMusicClick(String name) {
        musicList.add(name);
    }

    @Override
    public void onMusicUnclick(String name) {
        musicList.remove(name);
    }

    private boolean doesDateExist(){
        boolean dateExists = false;
        TextView dateView = findViewById(R.id.jobDate);
        if(!dateView.getText().toString().isEmpty()){
            dateExists = true;
        }
        return dateExists;
    }

    private boolean attendanceTaken(){
        boolean attendanceToken = false;
        if(!attendanceList.isEmpty()){
            attendanceToken = true;
        }
        return attendanceToken;
    }

    private boolean setsEntered(){
        boolean musicToken = false;
        if(!musicList.isEmpty()){
            musicToken = true;
        }
        return musicToken;
    }

    public void enterJobToDB(View v){
        DBHelper dbHelper;
        SQLiteDatabase bandDB;

        EditText dateField = findViewById(R.id.jobDate);
        String date = dateField.getText().toString();

        EditText pitchField = findViewById(R.id.pitch);
        int pitch = Integer.parseInt(pitchField.getText().toString());

        EditText tempField = findViewById(R.id.temp);
        int temp = Integer.parseInt(tempField.getText().toString());

        Spinner weatherSpinner = findViewById(R.id.weatherSpinner);
        String weather = weatherSpinner.getSelectedItem().toString();

        boolean dateExists = doesDateExist();

        //Jobs may be entered as 'placeholders' - with only a name, empty strings are acceptable
        try {
            dbHelper = new DBHelper(this);
            bandDB = dbHelper.openDB();

            String execStatement = "INSERT INTO Jobs (EventName, Date, ChanterPitch, Weather, Temperature) " +
                    "VALUES ('" + jobName + "','" + date + "','" + pitch + "','" + weather + "','" +
                    temp + "');";
            bandDB.execSQL(execStatement);

            bandDB.close();
            dbHelper.close();
        }

        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(dateExists && attendanceTaken()){
            try{
                dbHelper = new DBHelper(this);
                bandDB = dbHelper.openDB();

                for(String player : attendanceList){
                    String execStatement = "INSERT INTO Attendance (PlayerName, JobName, Date) " +
                            "VALUES ('" + player + "','" + jobName + "','" + date + "');";
                    bandDB.execSQL(execStatement);
                }

                bandDB.close();
                dbHelper.close();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if(dateExists && setsEntered()){
            try{
                dbHelper = new DBHelper(this);
                bandDB = dbHelper.openDB();

                for(String set : musicList){
                    String execStatement = "INSERT INTO MusicPlayed (SetName, JobName, Date) " +
                            "VALUES ('" + set + "','" + jobName + "','" + date + "');";
                    bandDB.execSQL(execStatement);
                }

                bandDB.close();
                dbHelper.close();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        Intent backToJobIntent = new Intent(this, JobActivity.class);
        backToJobIntent.putExtra("Name of Band", bandName);
        startActivity(backToJobIntent);
    }
}
