package com.joseph.faceaplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.params.Face;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String data = getIntent().getStringExtra("list_faces");
        Gson gson = new Gson();
        Face[] faces = gson.fromJson(data,Face[].class);
        ListView myListView = (ListView)findViewById(R.id.listView);

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.group);
        CustomAdapter customAdapter = new CustomAdapter(faces,this.originalBitmap);
        myListView.setAdapter(customAdapter);
    }
}
