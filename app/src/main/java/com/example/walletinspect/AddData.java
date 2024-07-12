package com.example.walletinspect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddData extends AppCompatActivity {

    EditText etamount, etreason;
    Button btninsert;
    Databasehelper dbhelper;
    TextView textView;
    boolean Expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);

        Intent intent = getIntent();
        Expense = intent.getBooleanExtra("Expense", false);

        etamount = findViewById(R.id.etamount);
        etreason = findViewById(R.id.etreason);
        btninsert = findViewById(R.id.btninsert);
        dbhelper = new Databasehelper(this);
        textView = findViewById(R.id.textView);

        if (Expense) {
            textView.setText("Add Expense");
            setTitle("Expense");
        } else {
            setTitle("Income");
            textView.setText("Add Income");
        }

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAmount = etamount.getText().toString().trim();
                String reason = etreason.getText().toString().trim();

                try {
                    double amount = Double.parseDouble(sAmount);

                    if (Expense) {
                        dbhelper.addExpense(amount, reason);
                    } else {
                        dbhelper.addIncome(amount, reason);
                    }

                    Toast.makeText(AddData.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close this activity after insertion

                } catch (NumberFormatException e) {
                    Toast.makeText(AddData.this, "Invalid amount format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
