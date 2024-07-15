package com.example.walletinspect;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ShowData extends AppCompatActivity {
    TextView tvtitle1, tvtime1;
    ListView listView;
    Databasehelper dbhelper;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList;
    boolean Exp;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        tvtitle1 = findViewById(R.id.tvtitle1);
        listView = findViewById(R.id.listview);
        tvtime1 = findViewById(R.id.tvtime1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String todayDate = sdf.format(new Date());
        tvtime1.setText("Current Time: " + todayDate);

        dbhelper = new Databasehelper(this);
        arrayList = new ArrayList<>();

        Intent intent = getIntent();
        Exp = intent.getBooleanExtra("Exp", false);

        loadAllData();
    }

    public void loadAllData() {
        if (Exp) {
            cursor = dbhelper.Expensealldata();
            tvtitle1.setText("Expense");
        } else {
            cursor = dbhelper.Incomealldata();
            tvtitle1.setText("Income");
        }
        if (cursor != null && cursor.getCount() > 0) {
            arrayList.clear();
            while (cursor.moveToNext()) {
                long timestamp = Long.parseLong(cursor.getString(3));
                String formattedDate = formatDate(timestamp);

                hashMap = new HashMap<>();
                hashMap.put("id", cursor.getString(0));
                hashMap.put("amount", cursor.getString(1));
                hashMap.put("reason", cursor.getString(2));
                hashMap.put("datetime", formattedDate);
                hashMap.put("type", Exp ? "Expense" : "Income");
                arrayList.add(hashMap);
            }
        }

        listView.setAdapter(new MyAdapter());
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                myView = inflater.inflate(R.layout.item_card, parent, false);
            } else {
                myView = convertView;
            }

            TextView tvamount = myView.findViewById(R.id.tvamounte);
            TextView tvt = myView.findViewById(R.id.tvt);
            TextView tvdate = myView.findViewById(R.id.tvdate);
            TextView tvreason = myView.findViewById(R.id.tvreason);
            TextView tvdelete = myView.findViewById(R.id.tvdelete);
            ImageView imageView = myView.findViewById(R.id.imageView);

            hashMap = arrayList.get(position);

            String id = hashMap.get("id");
            String type = hashMap.get("type");
            String amount = hashMap.get("amount");
            String reason = hashMap.get("reason");
            String datetime = hashMap.get("datetime");

            if (Exp) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.expense, null));
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.income, null));
            }

            tvt.setText(reason);
            tvamount.setText(amount);
            tvreason.setText(type);
            tvdate.setText(datetime);

            tvdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = hashMap.get("id");

                    if (Exp) {
                        dbhelper.deleteDataExpense(id);
                        loadAllData();
                    } else {
                        dbhelper.deleteDataIncome(id);
                        loadAllData();
                    }
                }
            });

            return myView;
        }
    }
}
