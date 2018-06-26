package app.avery.pipemajorhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
