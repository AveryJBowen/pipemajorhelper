package app.avery.pipemajorhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class AttendanceRecyclerViewAdapter extends RecyclerView.Adapter
        <AttendanceRecyclerViewAdapter.ViewHolder>{
    private List<HashMap<String, String>> playerList;
    private Context context;
    private OnAttendanceItemClick callback;

    public AttendanceRecyclerViewAdapter(List<HashMap<String, String>> playerList, Context cxt, OnAttendanceItemClick listener){
        this.playerList = playerList;
        this.callback = listener;
        this.context = cxt;
    }

    @Override
    public AttendanceRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String pName = playerList.get(position).get("PlayerName");
        holder.playerName.setText(pName);
    }

    @Override
    public int getItemCount(){
        return playerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView playerName;

        public ViewHolder(View view){
            super(view);
            playerName = view.findViewById(R.id.player_name_item);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView playerField = view.findViewById(R.id.player_name_item);
            String nameSelected = playerField.getText().toString();
            callback.onAttendanceClick(nameSelected);
        }
    }
}
