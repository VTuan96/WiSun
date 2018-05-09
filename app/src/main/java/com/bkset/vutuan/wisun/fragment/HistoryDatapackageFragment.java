package com.bkset.vutuan.wisun.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryDatapackageFragment extends Fragment {
    private RecyclerView rvHistoryDatapackage;
    private TextView txtHistoryTime;

    private String [] arrLabels=new String[]{"Temp","Humi","Gas", "Dust" };
    //All components of all graphs
    private ArrayList<Entry> entriesTemp=new ArrayList<>();
    private ArrayList labelsTemp = new ArrayList<String>();

    private ArrayList<Entry> entriesGas=new ArrayList<>();
    private ArrayList labelsGas= new ArrayList<String>();

    private ArrayList<Entry> entriesDust=new ArrayList<>();
    private ArrayList labelsDust = new ArrayList<String>();

    private ArrayList<Entry> entriesHumi=new ArrayList<>();
    private ArrayList labelsHumi = new ArrayList<String>();

    private ArrayList<Graph> listGraph=new ArrayList<>();
    private GraphAdapter adapter;

    private static String Date="";
    private String SelectedDate="";
    private Calendar calendar;
    private int year, month, day;

    public HistoryDatapackageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_history_datapackage, container, false);
        rvHistoryDatapackage= (RecyclerView) view.findViewById(R.id.rvHistoryDatapackage);
        txtHistoryTime= (TextView) view.findViewById(R.id.txtHistoryTime);

        setHasOptionsMenu(true);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //create graph
        listGraph=new ArrayList<>();
        rvHistoryDatapackage.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rvHistoryDatapackage.setLayoutManager(manager);

        Graph gTemp=new Graph(arrLabels[0],entriesTemp,labelsTemp);
        Graph gHumi=new Graph(arrLabels[1],entriesHumi,labelsHumi);
        Graph gGas=new Graph(arrLabels[2],entriesGas,labelsGas);
        Graph gDust=new Graph(arrLabels[3],entriesDust,labelsDust);
        Graph [] arrGraph=new Graph[]{gTemp,gHumi,gGas, gDust};
        for (Graph g:arrGraph){
            listGraph.add(g);
        }
        adapter=new GraphAdapter(listGraph);
        rvHistoryDatapackage.setAdapter(adapter);

        getAllDatapackage();

        return view;
    }

    private void getAllDatapackage() {
        final RequestQueue requestQueue= DownloadJSON.getInstance(getContext()).getRequestQue();

        StringRequest requestLocationCategory=new StringRequest(Request.Method.GET, Contants.URL_API_GETALL_DATAPACKAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray root=new JSONArray(response);
                    int length=root.length();
                    if (length>0){
                        for (int i=0;i<length;i++){
                            JSONObject obj=root.getJSONObject(i);
                            int Id=obj.getInt("Id");
                            String CreatedDate=obj.getString("CreatedDate");
                            String Temp=obj.getString("Temp");
                            String Humi=obj.getString("Humi");
                            String Gas=obj.getString("Gas");
                            String Dust=obj.getString("Dust");

                            Datapackage datapackage=new Datapackage(Id,CreatedDate,Temp,Humi,Gas,Dust);
                            String [] arrTime=CreatedDate.split("T");
                            Date=arrTime[0];
                            String time=arrTime[1]; //gia tri thoi gian cua du lieu

                            if (Date.equals(SelectedDate)){
                                //them gia tri thong so, va thoi gian vao bang bieu do
                                addEntryAndLabel(entriesTemp,labelsTemp, Double.parseDouble(Temp),i,time);
                                addEntryAndLabel(entriesHumi,labelsHumi, Double.parseDouble(Humi),i,time);
                                addEntryAndLabel(entriesGas,labelsGas, Double.parseDouble(Gas),i,time);
                                addEntryAndLabel(entriesDust,labelsDust, Double.parseDouble(Dust),i,time);
                                txtHistoryTime.setText(Date);
                            }

                        }

                        adapter=new GraphAdapter(listGraph);
                        adapter.notifyDataSetChanged();
                        rvHistoryDatapackage.setAdapter(adapter);



                    }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mnu_time,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.mnuTime){
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String mm="";
                        if (i1<10){
                            mm="0"+(i1+1);
                        } else {
                            mm=""+(i1+1);
                        }

                        String dd="";
                        if(i2<10){
                            dd="0"+(i2);
                        } else {
                            dd=""+i2;
                        }
                        SelectedDate= i+"-"+mm+"-"+dd;
                        System.out.println(SelectedDate);
                        getAllDatapackage();
                }
            }, year, month, day );
            datePickerDialog.show();
        }
        return true;
    }
}
