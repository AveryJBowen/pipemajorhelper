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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements OnRosterItemClick {
    String bandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        bandName = i.getStringExtra("Name of Band");

        if(i.getStringExtra("Detail").isEmpty()){
            setContentView(R.layout.activity_player);
            setUpView();
        }
        else{
            String name = i.getStringExtra("Detail");
            onRosterClick(name);
        }
    }

    public void setUpView(){
        TextView playerListingField = findViewById(R.id.player_band_name);
        playerListingField.setText("All " + bandName + " Players");
        try{
            String select = "SELECT Name, Rank FROM Players;";
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase bandDB = dbHelper.openDB();
            Cursor c = bandDB.rawQuery(select, null);
            List<HashMap<String, String>> cursorMap = new ArrayList<HashMap<String, String>>();
            while(c.moveToNext()){
                HashMap<String, String> map = new HashMap<>();
                map.put("Player", c.getString(0));
                map.put("Rank", c.getString(1));
                cursorMap.add(map);
            }
            c.close();
            bandDB.close();
            dbHelper.close();

            RecyclerView rosterRecyclerView = findViewById(R.id.roster_recycler_view);
            LinearLayoutManager rosterRecyclerLayoutManager = new LinearLayoutManager(this);
            rosterRecyclerView.setLayoutManager(rosterRecyclerLayoutManager);
            RosterRecyclerViewAdapter rosterRecyclerViewAdapter = new RosterRecyclerViewAdapter(cursorMap, this, this);
            rosterRecyclerView.setAdapter(rosterRecyclerViewAdapter);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRosterClick(String name){
        setContentView(R.layout.player_detail_view);
        //TODO: set detail view
    }

    public void restartPlayerActivity(View view){
        setContentView(R.layout.activity_player);
        setUpView();
    }

    public void startAddPlayer(View view){
        EditText player = findViewById(R.id.new_player_name);
        if(!player.getText().toString().isEmpty()){
            String playerName = player.getText().toString();
            Intent addPlayerIntent = new Intent(this, AddPlayerActivity.class);
            addPlayerIntent.putExtra("Band", bandName);
            addPlayerIntent.putExtra("Player", playerName);
            startActivity(addPlayerIntent);
        }
        else{
            Toast.makeText(this, "You must enter a player name!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPlayerClick(View v){
        setContentView(R.layout.add_player_view);
    }

    public void mainClick(View v){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
