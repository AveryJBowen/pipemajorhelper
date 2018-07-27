package app.avery.pipemajorhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class JobActivity extends AppCompatActivity {
    public String bandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        Intent i = getIntent();
        bandName = i.getStringExtra("Name of Band");
    }

    public void mainClick(View v){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void addJobClick(View v){
        RecyclerView setRecyclerView;
        RecyclerView playerRecyclerView;

        setContentView(R.layout.add_job_view);
        setRecyclerView = findViewById(R.id.set_lst);
        playerRecyclerView = findViewById(R.id.player_lst);

        LinearLayoutManager setRecyclerLayoutManager = new LinearLayoutManager(this);
        setRecyclerView.setLayoutManager(setRecyclerLayoutManager);
        SetRecyclerViewAdapter setRecyclerViewAdapter = new SetRecyclerViewAdapter(getSets(), this);
        setRecyclerView.setAdapter(setRecyclerViewAdapter);

        LinearLayoutManager playerRecyclerLayoutManager = new LinearLayoutManager(this);
        playerRecyclerView.setLayoutManager(playerRecyclerLayoutManager);
        PlayerRecyclerViewAdapter playerRecyclerViewAdapter = new PlayerRecyclerViewAdapter(getPlayers(), this);
        playerRecyclerView.setAdapter(playerRecyclerViewAdapter);
    }

    public List<String> getSets(){
        List<String> setList = new ArrayList<String>();
        //TO DO: GET THIS INFORMATION FROM DATABASE
        //Testing info below:
        setList.add("First Set");
        setList.add("Balmoral Set");
        setList.add("Farewell to the Creeks Set");

        return setList;
    }

    public List<String> getPlayers(){
        List<String> playerList = new ArrayList<String>();
        //TO DO: GET THIS INFORMATION FROM DATABASE
        //Testing info below:
        playerList.add("Avery Bowen");
        playerList.add("Mark Bartfeld");
        playerList.add("Frank George");

        return playerList;
    }
}
