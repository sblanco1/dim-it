package com.att.a56withrice.dimit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<LightFixture> lightList;
    ListView listView;
    private static ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.list);
        lightList = generateSampleLights();

        // adapter= new ListAdapter(lightList,getApplicationContext());
        // listView.setAdapter(adapter);

        adapter = new ListAdapter(lightList, this);
        listView.setAdapter(adapter);

//        String[] titles = new String[lightList.size()];
//        for(int i=0; i<lightList.size(); i++) {
//            titles[i] = lightList.get(i).getName();
//        }
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
//        listView.setAdapter(adapter);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent mapIntent = new Intent(this, MapActivity.class);
                startActivity(mapIntent);
                return true;

            case R.id.action_metrics:
                // User clicked the metrics button at the top
                Intent intent = new Intent(this, metrics.class);
                startActivity(intent);
                return true;

            case R.id.action_allOff:
                // User clicked the all off button at the top
                // Toast.makeText(getApplicationContext(),"All Off Clicked",Toast.LENGTH_SHORT).show();

                for(LightFixture light : lightList) {
                    light.setLightStatus(false);
                }
                listView.setAdapter(adapter);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    // Add the custom menu to the menu bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    private ArrayList<LightFixture> generateSampleLights() {

        HashMap<Integer, String> map =  buildHouseNames();
        ArrayList<LightFixture> testList = new ArrayList<LightFixture>();

        LightFixture tempFixture;
        for(int i=0; i<map.size(); i++) {
            String name = map.get(i);
            boolean lightStatus = (i%2 == 0);
            boolean smartStatus = (i%2 == 0);
            int lightValue = (int)(Math.random() * 101);

            tempFixture = new LightFixture(name, lightValue, lightStatus, smartStatus);

            if(i==0) {
                tempFixture.setEnabled(true);
            }

            testList.add(tempFixture);
        }

        return testList;
    }

    private HashMap<Integer, String> buildHouseNames() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "Living Room");
        map.put(1, "Family Room");
        map.put(2, "Kitchen");
        map.put(3, "Master Bedroom");
        map.put(4, "Kid Bedroom 1");
        map.put(5, "Kid Bedroom 2");
        map.put(6, "Dining Room");

        return map;
    }
}
