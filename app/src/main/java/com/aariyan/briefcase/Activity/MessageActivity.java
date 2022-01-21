package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.briefcase.Adapter.MessageAdapter;
import com.aariyan.briefcase.Constant.Constant;
import com.aariyan.briefcase.Model.MessageModel;
import com.aariyan.briefcase.R;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private String type = "";
    private RecyclerView recyclerView;
    private TextView toolbarText;
    private ImageView backBtn;
    private MessageAdapter adapter;
    List<MessageModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initUI();

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
            showData(type);
        }
    }

    private void initUI() {
        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbarText = findViewById(R.id.toolbarText);
        backBtn = findViewById(R.id.backButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void showData(String type) {
        toolbarText.setText(type);
        list.clear();
        for (int i = 0; i< Constant.listOfMessage.size(); i++) {
            MessageModel model = Constant.listOfMessage.get(i);

            if (model.getDepartment().equals(type)) {
                list.add(model);
            }
        }
        if (list.isEmpty()) {
            Toast.makeText(MessageActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
        }

        adapter = new MessageAdapter(MessageActivity.this,list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}