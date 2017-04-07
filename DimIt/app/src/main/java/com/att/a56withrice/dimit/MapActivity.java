package com.att.a56withrice.dimit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnTouchListener, OnDragListener {

    MapView map;
    HashMap<String, String> nameMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_map_view);
        findViewById(R.id.bulb_icon).setOnTouchListener(this);
        findViewById(R.id.map).setOnDragListener(this);
        map = (MapView)findViewById(R.id.drawing);
        Intent intent = getIntent();
        nameMap = (HashMap<String, String>)intent.getSerializableExtra("nameMap");
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        // TODO Auto-generated method stub
        System.out.println("Drag: " + String.valueOf(event.getAction()));
        if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
            //we want to make sure it is dropped only to left and right parent view
            Toast.makeText(getApplicationContext(),"Dropped",Toast.LENGTH_SHORT).show();
            View view = (View) event.getLocalState();
            view.buildDrawingCache();
            Bitmap bit = view.getDrawingCache();

               // ViewGroup source = (ViewGroup) view.getParent();
               // source.removeView(view);

            LinearLayout target = (LinearLayout) v;
           // MapView target = (MapView) v;
            BitmapDrawable db = new BitmapDrawable(bit);
                target.getOverlay().add(db);
                //target.addView(view);
            //make view visible as we set visibility to invisible while starting drag
            view.setVisibility(View.VISIBLE);
            //view.bringToFront();
        }
        return true;
    }

    Dialog dialog;
    List<String> usedNames = new ArrayList<>();
    String currentName;
    private void showRadioButtonDialog() {

        // custom dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.map_dialog);
        List<String> stringList= new ArrayList<String>(nameMap.values());

        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("button id->",String.valueOf(checkedId));
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        Log.e("selected RadioButton->",btn.getText().toString());
                        currentName = btn.getText().toString();

                    }
                }
            }
        });

        for(int i=0;i<stringList.size();i++){
            if(usedNames.contains(stringList.get(i))) continue;
            RadioButton rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }

        dialog.show();

    }

    public void selfDestruct(View view) {
        usedNames.add(currentName);
        dialog.dismiss();
    }

    float dX, dY;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // TODO Auto-generated method stub
        System.out.println("Touch: " + String.valueOf(event.getAction()));
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                map.addLight();
                showRadioButtonDialog();
                break;
            /*
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
                */
            default:
                return false;
        }
        return true;
    }
        /*
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }*/

    /*
    private final class BulbTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }



    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.bulb);
        Drawable normalShape = getResources().getDrawable(R.drawable.bulb);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
    */

}
