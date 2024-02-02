package org.meicode.listing_date_time;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Interface{
    TextView day;
    Button save,upgrade,delete;
    private ArrayList<String>item;
    private ArrayAdapter<String>itemadapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Interface recyclerview_interface;
    private DisplayAdapter adapter;
    EditText title,time;
    int position=0;

    ArrayList<Modelclass>TaskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        day=findViewById(R.id.textView1);
        save=findViewById(R.id.save);
        title=findViewById(R.id.edittitle);
        time=findViewById(R.id.edittime);
        upgrade=findViewById(R.id.retrieve);
        delete=findViewById(R.id.delete);

        buildrecycler();
        loaddata();

        item=new ArrayList<>();
        itemadapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,item);
        recyclerView.setAdapter(adapter);

//date and time set in header
        Date CurrentTime=Calendar.getInstance().getTime();
        String dateFormat=DateFormat.getTimeInstance(DateFormat.FULL).format(CurrentTime);
        String[] splitdate=dateFormat.split("");
        day.setText(CurrentTime.toString());
//        day.setText(splitdate[0]);
//        year.setText(splitdate[1]);
        Log.d("mylog",CurrentTime.toString());
//        Log.d("mylog",dateFormat);
        Log.d("mylog",splitdate[0]);
//        Log.d("mylog",splitdate[1]);
//        Log.d("mylog",splitdate[2]);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date CurrentTime=Calendar.getInstance().getTime();
                String dateFormat=DateFormat.getTimeInstance(DateFormat.FULL).format(CurrentTime);
                String[] splitdate=dateFormat.split("");
                time.setText(CurrentTime.toString());
                TaskList.add(new Modelclass(time.getText().toString(),title.getText().toString()));
                savedata();
                adapter.notifyDataSetChanged();
            }
        });
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date CurrentTime=Calendar.getInstance().getTime();
                String dateFormat=DateFormat.getTimeInstance(DateFormat.FULL).format(CurrentTime);
                String[] splitdate=dateFormat.split("");
                time.setText(CurrentTime.toString());
                TaskList.get(position).setTime(time.getText().toString());
                TaskList.get(position).setTitle(title.getText().toString());
                savedata();
                adapter.notifyDataSetChanged();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskList.clear();
                savedata();
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void savedata(){
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json= gson.toJson(TaskList);
        editor.putString("list",json);
        editor.apply();
    }
    private void loaddata(){
        Log.d("rafi", "Load data started");
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("list",null);
        Type type=new TypeToken<ArrayList<Modelclass>>(){}.getType();
        TaskList=gson.fromJson(json,type);
        Log.d("rafi", "Load data Size "+TaskList.size());
        adapter=new DisplayAdapter(this,this,TaskList);
        recyclerView.setAdapter(adapter);
        Log.d("rafi","the recyclerview is"+adapter);
    }
    private void buildrecycler() {
        TaskList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        adapter=new DisplayAdapter(this,this,TaskList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void OnItemClick(int position) {
        title.setText(TaskList.get(position).getTitle());
        this.position=position;
    }
    @Override
    public void onItemLongClick(int position) {
        String remove=TaskList.get(position).getTitle();
        for (int i=0;i<TaskList.size();i++){
            if (remove.equals(TaskList.get(i).getTitle())){
                TaskList.remove(i);
            }
            else {
            }
        }
        adapter.notifyDataSetChanged();
        savedata();
        Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
    }
}