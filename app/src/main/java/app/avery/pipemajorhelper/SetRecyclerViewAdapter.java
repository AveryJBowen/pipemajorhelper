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

public class SetRecyclerViewAdapter extends RecyclerView.Adapter<SetRecyclerViewAdapter.ViewHolder>{
    private List<String> setList;
    private Context context;

    public SetRecyclerViewAdapter(List<String> setModelList, Context cxt){
        setList = setModelList;
        context = cxt;
    }

    @Override
    public SetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_picker_recycler_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String sName = setList.get(position);
        holder.setName.setText(sName);
    }

    @Override
    public int getItemCount(){
        return setList.size();
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
                        Toast.makeText(SetRecyclerViewAdapter.this.context, "Set is " +
                                setName.getText(), Toast.LENGTH_SHORT).show();
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
