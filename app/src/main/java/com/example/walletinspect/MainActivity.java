package com.example.walletinspect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvshowallin, tvaddin, tvtotalin, tvfinal, tvtotalex, tvaddex, tvshoeallex;
    Databasehelper dbhelper;

    public static boolean Expense = true;
    public static boolean Exp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setTitle("Digital Wallet");

        tvshowallin = findViewById(R.id.tvshowallin);
        tvaddin = findViewById(R.id.tvaddin);
        tvtotalin = findViewById(R.id.tvtotalin);
        tvfinal = findViewById(R.id.tvfinal);
        tvtotalex = findViewById(R.id.tvamounte);
        tvaddex = findViewById(R.id.tvr);
        tvshoeallex = findViewById(R.id.tvshoeallex);
        dbhelper = new Databasehelper(this);

        tvaddex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expense = true;
                startActivity(new Intent(MainActivity.this, AddData.class).putExtra("Expense", Expense));
            }
        });

        tvaddin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expense = false;
                startActivity(new Intent(MainActivity.this, AddData.class).putExtra("Expense", Expense));
            }
        });

        tvshoeallex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exp = true;
                //startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtra("Exp", Exp));
                startActivity(new Intent(MainActivity.this, ShowData.class).putExtra("Exp", Exp));
            }
        });


        tvshowallin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exp = false;
                //startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtra("Exp", Exp));
                startActivity(new Intent(MainActivity.this, ShowData.class).putExtra("Exp", Exp));
            }
        });

        updateUI();
    }

    private void updateUI() {
        tvtotalex.setText("BDT " + dbhelper.calculateExpense());
        tvtotalin.setText("BDT " + dbhelper.calculateIncome());
       // tvfinal.setText(""+(dbhelper.calculateIncome() - dbhelper.calculateExpense()));
        tvfinal.setText(""+dbhelper.balence());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
    }
}
