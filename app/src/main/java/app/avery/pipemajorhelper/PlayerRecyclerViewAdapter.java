package app.avery.pipemajorhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter
        <PlayerRecyclerViewAdapter.ViewHolder>{
    private List<String> wholePlayerList;
    private List<String> selectedList;
    private boolean selectedListExists = false;
    private Context context;
    private OnPlayerItemClick callback;

    public PlayerRecyclerViewAdapter(List<String> playerList, Context cxt, OnPlayerItemClick listener){
        this.wholePlayerList = playerList;
        this.callback = listener;
        this.context = cxt;
    }

    public PlayerRecyclerViewAdapter(List<String> playerList, List<String> selectedList, Context cxt, OnPlayerItemClick listener){
        this.wholePlayerList = playerList;
        this.selectedList = selectedList;
        this.selectedListExists = true;
        this.context = cxt;
        this.callback = listener;
    }

    @Override
    public PlayerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_picker_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String pName = wholePlayerList.get(position);
        holder.playerName.setText(pName);
        if(selectedListExists){
            if(selectedList.contains(pName)){
                holder.selectionState.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount(){
        return wholePlayerList.size();
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
                        addPlayerToAttendanceList(playerName.getText().toString());
                    }
                    else {
                        removePlayerFromAttendanceList(playerName.getText().toString());
                    }
                }
            });
        }

        public void removePlayerFromAttendanceList(String name){
            callback.onPlayerUnclick(name);
            //Toast.makeText(PlayerRecyclerViewAdapter.this.context, "Player " +
                    //name + " removed from the attendance list", Toast.LENGTH_SHORT).show();
        }

        public void addPlayerToAttendanceList(String name){
            callback.onPlayerClick(name);
            //Toast.makeText(PlayerRecyclerViewAdapter.this.context, "Player " +
                    //name + " is on the attendance list", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {

        }
    }

}
