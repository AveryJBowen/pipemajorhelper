package app.avery.pipemajorhelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JobActivity extends AppCompatActivity implements OnJobItemClick, OnAttendanceItemClick,
        OnSetItemClick, OnPlayerItemClick, OnMusicItemClick {
    public String bandName;
    public String jobToMod, dateToMod, weatherToMod, pitchToMod, tempToMod;
    List<String> attendanceListForMod = new ArrayList<String>();
    List<String> musicListForMod = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        Intent i = getIntent();
        bandName = i.getStringExtra("Name of Band");
        setUpView();
    }

    public void setUpView(){
        TextView jobListingField = findViewById(R.id.job_band_name);
        jobListingField.setText("All " + bandName + " Jobs");
        try{
            String select = "SELECT EventName, Date FROM Jobs;";
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase bandDB = dbHelper.openDB();
            Cursor c = bandDB.rawQuery(select, null);
            List<HashMap<String, String>> cursorMap = new ArrayList<HashMap<String, String>>();
            while(c.moveToNext()){
                HashMap<String, String> map = new HashMap<>();
                map.put("EventName", c.getString(0));
                map.put("Date", c.getString(1));
                cursorMap.add(map);
            }
            c.close();
            bandDB.close();
            dbHelper.close();

            RecyclerView jobRecyclerView = findViewById(R.id.job_recycler_view);
            LinearLayoutManager jobRecyclerLayoutManager = new LinearLayoutManager(this);
            jobRecyclerView.setLayoutManager(jobRecyclerLayoutManager);
            JobRecyclerViewAdapter jobRecyclerViewAdapter = new JobRecyclerViewAdapter(cursorMap, this, this);
            jobRecyclerView.setAdapter(jobRecyclerViewAdapter);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void mainClick(View v){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void restartJobActivity(View v){
        setContentView(R.layout.activity_job);
        setUpView();
    }

    public void addJobClick(View v){
        setContentView(R.layout.add_job_view);
    }

    public void modifyJob(View v){

        setContentView(R.layout.job_modify_view);
        EditText modName = findViewById(R.id.mod_job_name);
        EditText modDate = findViewById(R.id.mod_job_date);
        EditText modTemp = findViewById(R.id.mod_job_temp);
        EditText modPitch = findViewById(R.id.mod_job_pitch);
        RecyclerView attendToModView = findViewById(R.id.mod_job_player_lst);
        RecyclerView musicToModView = findViewById(R.id.mod_job_set_lst);

        modName.setText(jobToMod);
        modDate.setText(dateToMod);
        modTemp.setText(tempToMod);
        modPitch.setText(pitchToMod);

        //TODO: Fix this: should populate with all the sets, and show sets in current job as checked
        LinearLayoutManager setRecyclerLayoutManager = new LinearLayoutManager(this);
        musicToModView.setLayoutManager(setRecyclerLayoutManager);
        SetRecyclerViewAdapter setRecyclerViewAdapter = new SetRecyclerViewAdapter(musicListForMod, this, this);
        musicToModView.setAdapter(setRecyclerViewAdapter);

        LinearLayoutManager playerRecyclerLayoutManager = new LinearLayoutManager(this);
        attendToModView.setLayoutManager(playerRecyclerLayoutManager);
        PlayerRecyclerViewAdapter playerRecyclerViewAdapter = new PlayerRecyclerViewAdapter(attendanceListForMod, this, this);
        attendToModView.setAdapter(playerRecyclerViewAdapter);

    }

    public void deleteJob(View v){
        String jobToDelete;
        String dateToDelete;
        try{
            TextView nameField = findViewById(R.id.job_name_details);
            TextView dateField = findViewById(R.id.job_detail_date);

            jobToDelete = nameField.getText().toString();
            dateToDelete = dateField.getText().toString();

            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase bandDB = dbHelper.openDB();

            //Delete job from jobs table
            String jobExecSQL = "DELETE FROM Jobs WHERE EventName='" + jobToDelete + "';";
            bandDB.execSQL(jobExecSQL);

            //Delete job attendance list
            String attendExecSQL = "DELETE FROM Attendance WHERE JobName='" + jobToDelete + "' AND Date='" + dateToDelete + "';";
            bandDB.execSQL(attendExecSQL);

            //Delete job music set list
            String musicExecSQL = "DELETE FROM MusicPlayed WHERE JobName='" + jobToDelete + "' AND Date='" + dateToDelete + "';";
            bandDB.execSQL(musicExecSQL);

            bandDB.close();
            dbHelper.close();

            Toast.makeText(this, "Deleted job: " + jobToDelete, Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_job);
            setUpView();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void startAddJob(View v){
        EditText job = findViewById(R.id.jobName);
        if(!job.getText().toString().isEmpty()){
            String jobName = job.getText().toString();
            Intent addJobIntent = new Intent(this, AddJobActivity.class);
            addJobIntent.putExtra("Band", bandName);
            addJobIntent.putExtra("Job", jobName);
            startActivity(addJobIntent);
        }
        else{
            Toast.makeText(this, "You must enter a job name!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveJob(View v){
        //TODO: Complete gathering information and saving job to Jobs table

        //TODO: Update Attendance table

        //TODO: Update MusicPlayed table
    }

    //CLICKER LISTENER METHODS
    @Override
    public void onJobClick(String name, String date) {

        setContentView(R.layout.job_detail_view);
        TextView jobNameField = findViewById(R.id.job_name_details);
        TextView dateField = findViewById(R.id.job_detail_date);
        TextView tempField = findViewById(R.id.job_detail_temp);
        TextView pitchField = findViewById(R.id.job_detail_pitch);
        TextView weatherField = findViewById(R.id.job_detail_weather);
        RecyclerView attendView = findViewById(R.id.job_detail_attendance_list);
        RecyclerView musicView = findViewById(R.id.job_detail_music_list);

        jobNameField.setText(name);

        try{
            String selectJob = "SELECT * FROM Jobs WHERE EventName='" + name + "' AND Date='" + date + "';";
            String selectAttendance = "SELECT * FROM Attendance WHERE JobName='" + name + "' AND Date='" + date + "';";
            String selectMusic = "SELECT * FROM MusicPlayed WHERE JobName='" + name + "' AND Date='" + date + "';";

            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase bandDB = dbHelper.openDB();
            Cursor jobCursor = bandDB.rawQuery(selectJob, null);
            Cursor attendCursor = bandDB.rawQuery(selectAttendance, null);
            Cursor musicCursor = bandDB.rawQuery(selectMusic, null);

            List<HashMap<String, String>> jobCursorMap = new ArrayList<HashMap<String, String>>();
            List<HashMap<String, String>> playerCursorMap = new ArrayList<HashMap<String, String>>();
            List<HashMap<String, String>> setCursorMap = new ArrayList<HashMap<String, String>>();

            while(jobCursor.moveToNext()){
                HashMap<String, String> map = new HashMap<>();
                map.put("EventName", jobCursor.getString(0));
                jobToMod = jobCursor.getString(0);
                map.put("Date", jobCursor.getString(1));
                dateToMod = jobCursor.getString(1);
                map.put("Pitch", jobCursor.getString(2));
                pitchToMod = jobCursor.getString(2);
                map.put("Weather", jobCursor.getString(3));
                weatherToMod = jobCursor.getString(3);
                map.put("Temperature", jobCursor.getString(4));
                tempToMod = jobCursor.getString(4);
                jobCursorMap.add(map);
            }

            while (attendCursor.moveToNext()){
                attendanceListForMod.add(attendCursor.getString(0));
                HashMap<String, String> map = new HashMap<>();
                map.put("PlayerName", attendCursor.getString(0));
                playerCursorMap.add(map);
            }

            while (musicCursor.moveToNext()){
                musicListForMod.add(musicCursor.getString(0));
                HashMap<String, String> map = new HashMap<>();
                map.put("SetName", musicCursor.getString(0));
                setCursorMap.add(map);
            }

            jobCursor.close();
            attendCursor.close();
            musicCursor.close();

            bandDB.close();
            dbHelper.close();

            dateField.setText("Event Date: " + jobCursorMap.get(0).get("Date").toString());
            pitchField.setText("Pitch: " + jobCursorMap.get(0).get("Pitch").toString());
            tempField.setText("Temperature: " + jobCursorMap.get(0).get("Temperature").toString());
            weatherField.setText("Weather Conditions: " + jobCursorMap.get(0).get("Weather").toString());

            LinearLayoutManager attendanceRecyclerLayoutManager = new LinearLayoutManager(this);
            attendView.setLayoutManager(attendanceRecyclerLayoutManager);
            AttendanceRecyclerViewAdapter attendanceRecyclerViewAdapter =
                    new AttendanceRecyclerViewAdapter(playerCursorMap, this, this);
            attendView.setAdapter(attendanceRecyclerViewAdapter);

            LinearLayoutManager musicRecyclerLayoutManager = new LinearLayoutManager(this);
            musicView.setLayoutManager(musicRecyclerLayoutManager);
            MusicRecyclerViewAdapter musicRecyclerViewAdapter =
                    new MusicRecyclerViewAdapter(setCursorMap, this, this);
            musicView.setAdapter(musicRecyclerViewAdapter);

        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttendanceClick(String name) {
        Intent showPlayerDetail = new Intent(this, PlayerActivity.class);
        showPlayerDetail.putExtra("Detail", name);
        startActivity(showPlayerDetail);
    }

    @Override
    public void OnSetClick(String name) {
        Intent showSetDetail = new Intent(this, MusicActivity.class);
        showSetDetail.putExtra("Detail", name);
        startActivity(showSetDetail);
    }

    @Override
    public void onMusicClick(String name) {
        musicListForMod.add(name);
    }

    @Override
    public void onMusicUnclick(String name) {
        musicListForMod.remove(name);
    }

    @Override
    public void onPlayerClick(String name) {
        attendanceListForMod.add(name);
    }

    @Override
    public void onPlayerUnclick(String name) {
        attendanceListForMod.remove(name);
    }
}
