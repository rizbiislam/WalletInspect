package com.example.walletinspect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ViewActivity extends AppCompatActivity {
    TextView tvView,tvtitle,tvtime;
    Databasehelper dbhelper;
    boolean Exp;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view);
        setTitle("View Data");

        recyclerView = findViewById(R.id.rview);
        tvtitle = findViewById(R.id.tvtitle);
        tvtime = findViewById(R.id.tvtime);
        dbhelper = new Databasehelper(this);



        Intent intent = getIntent();
        Exp = intent.getBooleanExtra("Exp", false);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String todayDate = sdf.format(new Date());
        tvtime.setText("Current Time: " + todayDate);

        if (Exp) {

            Cursor cursor11 = dbhelper.Expensealldata();
            tvtitle.setText("Expense");
            tvView.setText("Total Data: "+ cursor11.getCount());



            while(cursor11.moveToNext()){
                int id = cursor11.getInt(0);
                String amount = cursor11.getString(1);
                String reason = cursor11.getString(2);
                //String date = cursor11.getString(3);
                long timestamp = Long.parseLong(cursor11.getString(3));

                String formattedDate = formatDate(timestamp);



              //  tvView.append("\nID: "+id+"\nAmount: "+amount+"\nReason : "+reason+""+date);
                tvView.append("\n\nID: "+id+"\nAmount: "+amount+"\nReason : "+reason+"\nDate: "+formattedDate);

            }


        } else {
            Cursor cursor12 = dbhelper.Incomealldata();
            tvtitle.setText("Income");
            tvView.setText("Total Data: "+ cursor12.getCount());


            while(cursor12.moveToNext()){
                int id = cursor12.getInt(0);
                String amount = cursor12.getString(1);
                String reason = cursor12.getString(2);
                //String date = System.currentTimeMillis();
                long timestamp = Long.parseLong(cursor12.getString(3));

                String formattedDate = formatDate(timestamp);
               // String date2 = sdf.format(date);


                tvView.append("\n\nID: "+id+"\nAmount: "+amount+"\nReason : "+reason+"\nDate: "+formattedDate);

            }
        }

    }
    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy \n     HH:mm:ss");
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    private class xAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}