package app.avery.pipemajorhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder>{
    private List<String> playerList;
    private Context context;

    public PlayerRecyclerViewAdapter(List<String> playerModelList, Context cxt){
        playerList = playerModelList;
        context = cxt;
    }

    @Override
    public PlayerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_picker_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String pName = playerList.get(position);
        holder.playerName.setText(pName);
    }

    @Override
    public int getItemCount(){
        return playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView playerName;
        public CheckBox selectionState;

        public ViewHolder(View view){
            super(view);
            playerName = view.findViewById(R.id.player_name);
            selectionState = view.findViewById(R.id.player_select);

            view.setOnClickListener(this);
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Toast.makeText(PlayerRecyclerViewAdapter.this.context, "Player is " +
                                playerName.getText(), Toast.LENGTH_SHORT).show();
                    }
                    else {

                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            //TO DO: add set name to the list, add to the job table
        }
    }

}
