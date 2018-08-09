package app.avery.pipemajorhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class JobRecyclerViewAdapter extends RecyclerView.Adapter<JobRecyclerViewAdapter.ViewHolder> {
    private List<HashMap<String, String>> allJobsList;
    private Context context;
    private OnJobItemClick callback;

    public JobRecyclerViewAdapter(List<HashMap<String, String>> jobListing, Context cxt, OnJobItemClick listener){
        this.allJobsList = jobListing;
        this.context = cxt;
        this.callback = listener;
    }

    @Override
    public JobRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String jobName = allJobsList.get(position).get("EventName");
        String jobDate = allJobsList.get(position).get("Date");
        holder.jobName.setText(jobName);
        holder.jobDate.setText(jobDate);
    }

    @Override
    public int getItemCount(){
        return allJobsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView jobName;
        public TextView jobDate;

        public ViewHolder(View view){
            super(view);
            jobName = view.findViewById(R.id.job_name_item);
            jobDate = view.findViewById(R.id.job_date_item);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TextView jobSelected = view.findViewById(R.id.job_name_item);
            String jobName = jobSelected.getText().toString();
            callback.onJobClick(jobName);
            //Toast.makeText(context.getApplicationContext(), "Job selected: " + jobName, Toast.LENGTH_SHORT).show();
        }
    }
}
