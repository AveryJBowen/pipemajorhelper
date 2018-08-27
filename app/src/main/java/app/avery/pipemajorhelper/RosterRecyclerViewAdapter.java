package app.avery.pipemajorhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

class RosterRecyclerViewAdapter extends RecyclerView.Adapter<RosterRecyclerViewAdapter.ViewHolder>{
    private List<HashMap<String, String>> allPlayersList;
    private Context context;
    private OnRosterItemClick callback;

    public RosterRecyclerViewAdapter(List<HashMap<String, String>> playerListing, Context cxt, OnRosterItemClick listener){
        this.allPlayersList = playerListing;
        this.context = cxt;
        this.callback = listener;
    }

    @Override
    public RosterRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roster_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String playerName = allPlayersList.get(position).get("Player");
        String playerRank = allPlayersList.get(position).get("Rank");
        holder.playerName.setText(playerName);
        holder.playerRank.setText(playerRank);
    }

    @Override
    public int getItemCount(){
        return allPlayersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView playerName;
        public TextView playerRank;

        public ViewHolder(View view){
            super(view);
            playerName = view.findViewById(R.id.roster_name_item);
            playerRank = view.findViewById(R.id.roster_rank_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            TextView rosterSelected = view.findViewById(R.id.roster_name_item);
            String playerName = rosterSelected.getText().toString();
            callback.onRosterClick(playerName);
        }
    }
}
