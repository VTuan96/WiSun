package com.bkset.vutuan.wisun;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bkset.vutuan.wisun.adapter.CustomAdapterViewPager;
import com.bkset.vutuan.wisun.fragment.HistoryDatapackageFragment;
import com.bkset.vutuan.wisun.fragment.RealtimeDatapackageFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private CustomAdapterViewPager adapter;
    private ArrayList<Fragment> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealtimeDatapackageFragment realtimeDatapackageFragment=new RealtimeDatapackageFragment();
        list.add(realtimeDatapackageFragment);
        HistoryDatapackageFragment historyDatapackageFragment=new HistoryDatapackageFragment();
        list.add(historyDatapackageFragment);

        FragmentManager manager=getSupportFragmentManager();
        adapter=new CustomAdapterViewPager(manager,getBaseContext(),list);

        pager= (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }
}
