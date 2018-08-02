package app.avery.pipemajorhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddJobActivity extends AppCompatActivity implements OnPlayerItemClick, OnMusicItemClick {

    String jobName;
    List<String> attendanceList = new ArrayList<String>();
    List<String> musicList = new ArrayList<String>();
    Job newJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        newJob = (Job) i.getSerializableExtra("Job");
        jobName = newJob.getName();

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

    public void enterJobToDB(View v){
        String players = "";
        for(String s : attendanceList){
            players += s;
        }
        Toast.makeText(this, "The player list: " + players, Toast.LENGTH_LONG).show();
    }
}
