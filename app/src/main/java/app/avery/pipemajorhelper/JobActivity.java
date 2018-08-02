package app.avery.pipemajorhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
        setContentView(R.layout.add_job_view);
    }

    public void startAddJob(View v){
        EditText job = findViewById(R.id.jobName);
        String jobName = job.getText().toString();
        Job newJob = new Job(jobName);
        Intent addJobIntent = new Intent(this, AddJobActivity.class);
        addJobIntent.putExtra("Job", newJob);
        startActivity(addJobIntent);
    }
}
