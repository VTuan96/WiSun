package com.bkset.vutuan.wisun.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bkset.vutuan.wisun.R;
import com.bkset.vutuan.wisun.adapter.GraphAdapter;
import com.bkset.vutuan.wisun.model.Datapackage;
import com.bkset.vutuan.wisun.model.Graph;
import com.bkset.vutuan.wisun.ultilities.Contants;
import com.bkset.vutuan.wisun.ultilities.DownloadJSON;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealtimeDatapackageFragment extends Fragment {
//    private RecyclerView rvRealtimeDatapackage;
//    private TextView txtRealtimeTime;
//
//    private String [] arrLabels=new String[]{"Temp","Humi","Gas", "Dust" };
//    //All components of all graphs
//    private ArrayList<Entry> entriesTemp=new ArrayList<>();
//    private ArrayList labelsTemp = new ArrayList<String>();
//
//    private ArrayList<Entry> entriesGas=new ArrayList<>();
//    private ArrayList labelsGas= new ArrayList<String>();
//
//    private ArrayList<Entry> entriesDust=new ArrayList<>();
//    private ArrayList labelsDust = new ArrayList<String>();
//
//    private ArrayList<Entry> entriesHumi=new ArrayList<>();
//    private ArrayList labelsHumi = new ArrayList<String>();
//
//    private ArrayList<Graph> listGraph=new ArrayList<>();
//    private GraphAdapter adapter;
//
//    private static String Date="";
//    private static int counter=0;

    private TextView txtRealtimeTime;
    private TextView txtTemp, txtHumi, txtGas, txtDust;

    public RealtimeDatapackageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_realtime_datapackage, container, false);
//        rvRealtimeDatapackage= (RecyclerView) view.findViewById(R.id.rvRealtimeDatapackage);
        txtRealtimeTime= (TextView) view.findViewById(R.id.txtRealtimeTime);
        txtDust= (TextView) view.findViewById(R.id.txtDust);
        txtGas= (TextView) view.findViewById(R.id.txtGas);
        txtTemp= (TextView) view.findViewById(R.id.txtTemp);
        txtHumi= (TextView) view.findViewById(R.id.txtHumi);
//
//        //create graph
//        listGraph=new ArrayList<>();
//        rvRealtimeDatapackage.setHasFixedSize(true);
//        LinearLayoutManager manager=new LinearLayoutManager(getContext());
//        rvRealtimeDatapackage.setLayoutManager(manager);
//
//        Graph gTemp=new Graph(arrLabels[0],entriesTemp,labelsTemp);
//        Graph gHumi=new Graph(arrLabels[1],entriesHumi,labelsHumi);
//        Graph gGas=new Graph(arrLabels[2],entriesGas,labelsGas);
//        Graph gDust=new Graph(arrLabels[3],entriesDust,labelsDust);
//        Graph [] arrGraph=new Graph[]{gTemp,gHumi,gGas, gDust};
//        for (Graph g:arrGraph){
//            listGraph.add(g);
//        }
//        adapter=new GraphAdapter(listGraph);
//        rvRealtimeDatapackage.setAdapter(adapter);
//
//
        final Handler handler = new Handler();
        Runnable getData=new Runnable() {
            @Override
            public void run() {
                getAllDatapackage();
                handler.postDelayed(this,4000);
            }
        };
        getData.run();



        return view;
    }

    private void getAllDatapackage() {
        final RequestQueue requestQueue= DownloadJSON.getInstance(getContext()).getRequestQue();

        StringRequest requestLocationCategory=new StringRequest(Request.Method.GET, Contants.URL_API_GETNEWEST_DATAPACKAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj=new JSONObject(response);
                    int Id=obj.getInt("Id");
                    String CreatedDate=obj.getString("CreatedDate");
                    String Temp=obj.getString("Temp");
                    String Humi=obj.getString("Humi");
                    String Gas=obj.getString("Gas");
                    String Dust=obj.getString("Dust");

                    Datapackage datapackage=new Datapackage(Id,CreatedDate,Temp,Humi,Gas,Dust);
                    String [] arrTime=CreatedDate.split("T");
                    String date=arrTime[0];
                    String time=arrTime[1]; //gia tri thoi gian cua du lieu
                    String dateTime=date+" "+time;

                    txtDust.setText(Dust);
                    txtGas.setText(Gas);
                    txtTemp.setText(Temp);
                    txtHumi.setText(Humi);
                    txtRealtimeTime.setText(dateTime);
                    

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(requestLocationCategory);
    }

    //add Entry and label into a graph
    private void addEntryAndLabel(ArrayList<Entry> entries, ArrayList<String> labels, double value, int index, String time){
        entries.add(new Entry(index,(float) value));
        labels.add(time);
    }

}
