package com.example.android.quakereport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.quakereport.R;

/**
 * Created by indraaguslesmana on 2/6/17.
 */

public class ChatQuickBloxActivity extends AppCompatActivity {

    private RecyclerView mRecycleview;
    private TextView mEmpetyView;

    private ImageButton mBtn_send;
    private EditText mChat_text;
    private ListView mMessageListview;
    private ImageButton mImageSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBtn_send = (ImageButton) findViewById(R.id.send_chat);
        mChat_text = (EditText) findViewById(R.id.ed_chat);
        mMessageListview = (ListView) findViewById(R.id.list_chat);
        mImageSelect = (ImageButton) findViewById(R.id.imageSelect);

        mEmpetyView = (TextView)findViewById(R.id.textEmpety);
        mRecycleview = (RecyclerView) findViewById(R.id.list);

    }
}
