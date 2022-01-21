package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aariyan.briefcase.R;

public class MemoHome extends AppCompatActivity {

    private CardView logVisit,createMemo,viewMemo,viewVisit;
    private TextView customerName,customerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_home);

        initUI();

        if (getIntent() != null) {
            customerName.setText(getIntent().getStringExtra("name"));
            customerCode.setText(getIntent().getStringExtra("code"));
        }
    }

    private void initUI() {

        logVisit = findViewById(R.id.logVisit);
        createMemo = findViewById(R.id.createMemo);
        viewMemo = findViewById(R.id.viewMemo);
        viewVisit = findViewById(R.id.viewVisit);

        customerName = findViewById(R.id.cName);
        customerCode = findViewById(R.id.cCode);

        logVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MemoHome.this,LogVisit.class)
                        .putExtra("name",customerName.getText().toString())
                        .putExtra("code",customerCode.getText().toString())
                );
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        createMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MemoHome.this,CreateMemo.class)
                        .putExtra("name",customerName.getText().toString())
                        .putExtra("code",customerCode.getText().toString())
                );
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        viewMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MemoHome.this,ViewMemoVisit.class)
                        .putExtra("type","Memo")
                        .putExtra("name",customerName.getText().toString())
                        .putExtra("code",customerCode.getText().toString())
                );
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        viewVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MemoHome.this,ViewMemoVisit.class)
                        .putExtra("type","Visit")
                        .putExtra("name",customerName.getText().toString())
                        .putExtra("code",customerCode.getText().toString())
                );
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}