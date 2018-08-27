package app.avery.pipemajorhelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase bandDB;
    public String bandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            String select = "SELECT BandName FROM Info;";
            dbHelper = new DBHelper(this);
            bandDB = dbHelper.openDB();
            Cursor c = bandDB.rawQuery(select, null);
            if(c.moveToFirst()){
                bandName = c.getString(c.getColumnIndex("BandName"));
                setContentView(R.layout.activity_main);
                TextView bandView = findViewById(R.id.welcomeBand);
                bandView.setText(bandName);
            }
            else {
                setContentView(R.layout.info_input_view);
            }
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public void enterClick(View v){
        EditText band = findViewById(R.id.BandName);
        EditText user = findViewById(R.id.UserName);
        String info = "INSERT INTO Info (BandName, User) VALUES ('" + band.getText().toString() +
                "','" + user.getText().toString() + "');";
        try{
            dbHelper = new DBHelper(this);
            bandDB = dbHelper.openDB();
            bandDB.execSQL(info);
            bandDB.close();
            dbHelper.close();
            bandName = band.getText().toString();
            finish();
            startActivity(getIntent());
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void playerClick(View v){
        Intent playerIntent = new Intent(this, PlayerActivity.class);
        playerIntent.putExtra("Name of Band", bandName);
        playerIntent.putExtra("Detail", "");
        startActivity(playerIntent);
    }

    public void jobClick(View v){
        Intent jobIntent = new Intent(this, JobActivity.class);
        jobIntent.putExtra("Name of Band", bandName);
        startActivity(jobIntent);
    }

    public void musicClick(View v){
        Intent musicIntent = new Intent(this, MusicActivity.class);
        musicIntent.putExtra("Name of Band", bandName);
        startActivity(musicIntent);
    }

    public void changeDataClick(View v){
        String removeData = "DELETE FROM Info WHERE BandName='" + bandName + "';";
        String emptyJobs = "DELETE FROM Jobs;";
        String emptyAttendance = "DELETE FROM Attendance;";
        String emptySets = "DELETE FROM MusicPlayed;";
        try{
            dbHelper = new DBHelper(this);
            bandDB = dbHelper.openDB();

            bandDB.execSQL(removeData);
            bandDB.execSQL(emptyJobs);
            bandDB.execSQL(emptyAttendance);
            bandDB.execSQL(emptySets);

            bandDB.close();
            dbHelper.close();
            setContentView(R.layout.info_input_view);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
        }
    }


}
