package com.joseph.faceaplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.params.Face;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private FaceServiceClient faceServiceClient = new FaceServiceRestClient("d38da73db2454879be5a7490382c7960");
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.group);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView = setImageBitmap(bitmap);

        Button btnProcess = (Button)findViewById(R.id.btnProcess);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectAndFrame(bitmap);
            }
        });
    }

    private void detectAndFrame(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream((outputStream.toByteArray()));

        AsyncTask<InputStream,String,Face[]>detectTask = new AsyncTask<InputStream, String, Face[]>() {
            private ProgressDialog pd = new ProgressDialog(MainActivity.this);
            @Override
            protected Face[] doInBackground(InputStream... params) {

                    publishProgress("Detectando....");
                    FaceServiceClient.FaceAttributeType[]faceAttr = new FaceServiceClient.FaceAttributeType[]{
                            FaceServiceClient.FaceAttributeType.HeadPose,
                            FaceServiceClient.FaceAttributeType.Age,
                            FaceServiceClient.FaceAttributeType.Gender,
                            FaceServiceClient.FaceAttributeType.Smile,
                            FaceServiceClient.FaceAttributeType.FacialHair,
                    };
                    try {
                        Face[] result = faceServiceClient.detect(params[0],
                                true,
                                false,
                                faceAttr);
                        if (result == null)
                        {
                            publishProgress("Deteccion Finalizada. No se detecto nada");
                            return null;
                        }
                        publishProgress(String.format("Deteccion Finalizada. %d face(s) detectado",result.length));
                        return result;
                    }catch (Exception e){
                        publishProgress("Deteccion Fallida");
                        return null;
                    }
                }
            @Override
            protected void onPreExecute() {
                pd.show();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                pd.setMessage(values[0]);
            }

            @Override
            protected void onPostExecute(Face[] faces) {
                pd.dismiss();
                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                Gson gson = new Gson();
                String data = gson.toJson(faces);
                intent.putExtra("list face",data);
                startActivity(intent);
            }
        };
    }
}
