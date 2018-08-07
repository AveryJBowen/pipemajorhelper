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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JobActivity extends AppCompatActivity {
    public String bandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        Intent i = getIntent();
        bandName = i.getStringExtra("Name of Band");

        TextView jobListingField = findViewById(R.id.job_band_name);
        jobListingField.setText("All " + bandName + " Jobs");

        setUpView();
    }

    public void setUpView(){
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
            JobRecyclerViewAdapter jobRecyclerViewAdapter = new JobRecyclerViewAdapter(cursorMap, this);
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

    public void addJobClick(View v){
        setContentView(R.layout.add_job_view);
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
}
