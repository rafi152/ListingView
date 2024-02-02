package org.meicode.listing_date_time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder> {
    Interface recyclerviewinterface;
    Context context;
    ArrayList<Modelclass> taskList;

    public DisplayAdapter(Context context, Interface recyclerviewinterface, ArrayList<Modelclass> arrayList) {
        this.context = context;
        this.recyclerviewinterface = recyclerviewinterface;
        this.taskList = arrayList;
    }
    @NonNull
    @Override
    public DisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Title.setText(taskList.get(position).getTitle());
        holder.Time.setText(taskList.get(position).getTime());

//        Date CurrentTime = Calendar.getInstance().getTime();
//        String date = DateFormat.getTimeInstance(DateFormat.FULL).format(CurrentTime);
//        holder.time.setText(date);
//        String time = "";
//        if (model.time == null) {
//            time = new SimpleDateFormat("hh:mm:a", Locale.getDefault()).format(new Date());
//            holder.Time.setText(time);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.textview_1);
            Time = itemView.findViewById(R.id.textview_2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerviewinterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerviewinterface.OnItemClick(pos);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recyclerviewinterface!=null){
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerviewinterface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
}

