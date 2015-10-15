
/*
 * Copyright 2015. Daniel Ramírez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.google.wearable.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements WearableListView.ClickListener{

    private List<ListViewItem> viewItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_activity);

        WearableListView wearableListView = (WearableListView) findViewById(R.id.wearable_list_view);

        viewItemList.add(new ListViewItem(R.drawable.ic_basketball, "Nearest Mosque"));
        viewItemList.add(new ListViewItem(R.drawable.ic_baseball, "Prayer Timings"));
        viewItemList.add(new ListViewItem(R.drawable.ic_running, "Qibla Direction"));

        wearableListView.setAdapter(new ListViewAdapter(this, viewItemList));
        wearableListView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        String a= viewItemList.get(viewHolder.getPosition()).text;
        if(a=="Prayer Timings"){
            Context c=getApplicationContext();
            Intent myIntent = new Intent();
            myIntent.setClass(c,prayers.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(myIntent);
        }
        if(a=="Nearest Mosque"){
            Context c=getApplicationContext();
            Intent myIntent = new Intent();
            myIntent.setClass(c,mosque.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(myIntent);
        }
        if(a=="Qibla Direction"){
            Context c=getApplicationContext();
            Intent myIntent = new Intent();
            myIntent.setClass(c,qiblah.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(myIntent);
        }

    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
