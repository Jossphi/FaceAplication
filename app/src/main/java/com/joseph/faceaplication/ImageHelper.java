package com.joseph.faceaplication;

import android.graphics.Bitmap;

import com.microsoft.projectoxford.face.contract.FaceRectangle;

public class ImageHelper {

    public static FaceRectangle calculateFaceRectangle(Bitmap bitmap,FaceRectangle faceRectangle,double faceRecEnlargeRation)
    {
        double sideLenght = faceRectangle.width*faceRecEnlargeRation;
        sideLenght = Math.min(sideLenght,bitmap.getWidth());
        sideLenght = Math.min(sideLenght,bitmap.getHeight());

        double left = faceRectangle.left - faceRectangle.width*(faceRecEnlargeRation-1.0)*0.5;
        left = Math.max(left,0.0);
        left = Math.min(left,bitmap.getWidth()- sideLenght);

        double top = faceRectangle.top - faceRectangle.height*(faceRecEnlargeRation-1.0)*0.5;
        top = Math.max(top,0.0);
        top = Math.min(top,bitmap.getHeight() - sideLenght);

        double shiftTop = faceRecEnlargeRation - 1.0;
        shiftTop = Math.max(shiftTop,0.0);
        shiftTop = Math.min(shiftTop,1.0);
        top = 0.15*shiftTop*faceRectangle.height;
        top = Math.max(top,0.0);

        FaceRectangle result = new FaceRectangle();
        result.left = (int)left;
        result.top = (int)top;
        result.width = (int)sideLenght;
        result.height = (int)sideLenght;
        return result;

    }

    public static Bitmap generateThumbnail(Bitmap original,FaceRectangle faceRectangle)
    {
        FaceRectangle face = calculateFaceRectangle(originalBitmap,faceRectangle,1.3);
        return Bitmap.createBitmap(originalBitmap,faceRectangle.left,faceRectangle.top,faceRectangle.width,faceRectangle.height);
    }
}
