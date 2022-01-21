package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aariyan.briefcase.Adapter.CustomerAdapter;
import com.aariyan.briefcase.Interface.CustomerListInterface;
import com.aariyan.briefcase.Model.CustomerModel;
import com.aariyan.briefcase.Network.Networking;
import com.aariyan.briefcase.R;

import java.util.List;

public class CustomerNameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private EditText search;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_name);

        initUI();
    }

    private void initUI() {
        recyclerView = findViewById(R.id.customerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.searchHere);
        progressBar = findViewById(R.id.progressbar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loadData();
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadData() {
        Networking networking = new Networking(CustomerNameActivity.this);
        networking.getCustomerList(new CustomerListInterface() {
            @Override
            public void customerList(List<CustomerModel> list) {
                if (!list.isEmpty()) {
                    adapter = new CustomerAdapter(CustomerNameActivity.this, list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CustomerNameActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void error(String message) {
                Toast.makeText(CustomerNameActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}