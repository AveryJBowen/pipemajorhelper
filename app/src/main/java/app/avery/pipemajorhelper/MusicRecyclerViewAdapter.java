package app.avery.pipemajorhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter
        <MusicRecyclerViewAdapter.ViewHolder>{
    private List<HashMap<String, String>> setList;
    private Context context;
    private OnSetItemClick callback;

    public MusicRecyclerViewAdapter(List<HashMap<String, String>> setList, Context cxt, OnSetItemClick listener){
        this.setList = setList;
        this.callback = listener;
        this.context = cxt;
    }

    @Override
    public MusicRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_list_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String sName = setList.get(position).get("SetName");
        holder.setName.setText(sName);
    }

    @Override
    public int getItemCount(){
        return setList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView setName;

        public ViewHolder(View view){
            super(view);
            setName = view.findViewById(R.id.set_name_item);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView setField = view.findViewById(R.id.set_name_item);
            String musicSelected = setField.getText().toString();
            callback.OnSetClick(musicSelected);
        }
    }
}
