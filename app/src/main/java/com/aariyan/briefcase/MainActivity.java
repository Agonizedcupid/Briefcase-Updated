package com.aariyan.briefcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText customerName,customerCode;
    private Button getHistoryBtn;

    private EditText getSalesEditText;
    private Button getSalesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        customerName = findViewById(R.id.customerNameEdtText);
        customerCode = findViewById(R.id.customerCodeEdtText);
        getHistoryBtn = findViewById(R.id.orderHistoryBtn);

        getSalesEditText = findViewById(R.id.getSalesEdtText);
        getSalesBtn = findViewById(R.id.getSalesBtn);

        getSalesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(getSalesEditText.getText().toString().trim())) {
                    getSalesEditText.setError("Pleas enter the code!");
                    getSalesEditText.requestFocus();
                    return;
                }

                Intent intent = new Intent(MainActivity.this,Sales.class);
                intent.putExtra("code", getSalesEditText.getText().toString().trim());
                startActivity(intent);
            }
        });

        getHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                intent.putExtra("name",customerName.getText().toString());
                intent.putExtra("code",customerCode.getText().toString());
                startActivity(intent);
            }
        });
    }
}