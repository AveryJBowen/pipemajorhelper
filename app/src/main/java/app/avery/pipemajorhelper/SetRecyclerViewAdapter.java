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

public class SetRecyclerViewAdapter extends RecyclerView.Adapter<SetRecyclerViewAdapter.ViewHolder>{
    private List<String> wholeSetList;
    private List<String> selectedList;
    private boolean selectedListExists = false;
    private Context context;
    private OnMusicItemClick callback;

    public SetRecyclerViewAdapter(List<String> setList, Context cxt, OnMusicItemClick listener){
        this.wholeSetList = setList;
        this.context = cxt;
        this.callback = listener;
    }

    public SetRecyclerViewAdapter(List<String> setList, List<String> selectedList, Context cxt, OnMusicItemClick listener){
        this.wholeSetList = setList;
        this.selectedList = selectedList;
        this.selectedListExists = true;
        this.context = cxt;
        this.callback = listener;
    }

    @Override
    public SetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_picker_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String sName = wholeSetList.get(position);
        holder.setName.setText(sName);
        if(selectedListExists){
            if(selectedList.contains(sName)){
                holder.selectionState.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount(){
        return wholeSetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView setName;
        public CheckBox selectionState;

        public ViewHolder(View view){
            super(view);
            setName = view.findViewById(R.id.set_name);
            selectionState = view.findViewById(R.id.set_select);

            view.setOnClickListener(this);
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        addSetToList(setName.getText().toString());
                    }
                    else {
                        removeSetFromList(setName.getText().toString());
                    }
                }
            });
        }

        public void removeSetFromList(String name){
            callback.onMusicUnclick(name);
        }

        public void addSetToList(String name){
            callback.onMusicClick(name);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
