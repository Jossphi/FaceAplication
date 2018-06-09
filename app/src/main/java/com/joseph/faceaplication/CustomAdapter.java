package com.joseph.faceaplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.params.Face;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Face[] face;
    private Context context;
    private LayoutInflater inflater;
    private Bitmap originalBitmap;

    public CustomAdapter(Face[] face, Context context, LayoutInflater inflater, Bitmap originalBitmap) {
        this.face = face;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.originalBitmap = originalBitmap;
    }

    @Override
    public int getCount() {
        return face.length;
    }

    @Override
    public Object getItem(int position) {
        return face[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.listview_layout,null);
        TextView txtAge,txtGender,txtFacialHair,txtHeadPose,txtSmile;
        ImageView imageView;

        txtAge = (TextView)view.findViewById(R.id.txtAge);
        txtFacialHair = (TextView)view.findViewById(R.id.txtFacialHair);
        txtGender = (TextView)view.findViewById(R.id.txtGender);
        txtHeadPose= (TextView)view.findViewById(R.id.txtHeadPose);
        txtSmile = (TextView)view.findViewById(R.id.txtSmile);

        imageView = (ImageView)view.findViewById(R.id.imgThumb);

        txtAge.setText("Age : "+face[position].faceAttributes.age);
        txtGender.setText("Gender : "+face[position].faceAttributes.age);
        txtSmile.setText("Smile : "+face[position].faceAttributes.age);
        txtFacialHair.setText(String.format("Facial Hair : %f %f %f", face[position].faceAttribute.faceHair.moustache),
                face[position].faceAttribute.facialHair.sideburns,
                face[position].faceAttribute.facialHair.beard);
        txtHeadPose.setText(String.format("HeadPose : %f %f %f", face[position].faceAttribute.headPose.pitch),
                face[position].faceAttribute.heaPose.yaw,
                face[position].faceAttribute.headPose.roll);
        Bitmap bitmap = ImageHelper.generatethumbnail(originalBitmap,face[position].faceRectangle);
        imageView.setImageBitmap(bitmap);

        return view;
    }
}
