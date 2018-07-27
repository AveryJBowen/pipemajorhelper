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
        setContentView(R.layout.add_job_view);
        setRecyclerView = findViewById(R.id.set_lst);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        setRecyclerView.setLayoutManager(recyclerLayoutManager);

        SetRecyclerViewAdapter setRecyclerViewAdapter = new SetRecyclerViewAdapter(getSets(), this);
        setRecyclerView.setAdapter(setRecyclerViewAdapter);
    }

    public List<String> getSets(){
        List<String> setList = new ArrayList<String>();
        setList.add("First Set");
        setList.add("Balmoral Set");
        setList.add("Farewell to the Creeks Set");

        return setList;
    }
}
