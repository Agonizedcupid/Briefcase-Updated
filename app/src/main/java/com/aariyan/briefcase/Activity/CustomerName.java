package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aariyan.briefcase.R;

public class CustomerName extends AppCompatActivity {

    private EditText userName;
    private Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_name2);

        userName = findViewById(R.id.userName);
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(userName.getText().toString())) {
                    userName.setError("Please enter name!");
                    userName.requestFocus();
                    return;
                }

                Intent intent = new Intent(CustomerName.this,SalesActivity.class);
                intent.putExtra("name", userName.getText().toString());
                startActivity(intent);
            }
        });
    }
}