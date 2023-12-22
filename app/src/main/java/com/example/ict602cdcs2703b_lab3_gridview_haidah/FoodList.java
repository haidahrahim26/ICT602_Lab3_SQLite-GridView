package com.example.ict602cdcs2703b_lab3_gridview_haidah;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FoodList extends Activity {

   GridView gridView;ArrayList<Food> list;FoodListAdapter adapter = null;
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.food_list_activity);
      gridView = (GridView) findViewById(R.id.gridView);
      list = new ArrayList<>();
      adapter = new FoodListAdapter(this, R.layout.food_items, list);
      gridView.setAdapter(adapter);    // get all data from sqlite

      Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM FOOD");
      list.clear();
      while (cursor.moveToNext()) {
         int id = cursor.getInt(0);
         String name = cursor.getString(1);
         String price = cursor.getString(2);
         byte[] image = cursor.getBlob(3);
         list.add(new Food(name, price, image, id));
      }
      adapter.notifyDataSetChanged();}

   ImageView imageViewFood;

   @Override
   public void onRequestPermissionsResult(int requestCode,

                                          @NonNull
                                          String[] permissions, @NonNull int[] grantResults) {
      if(requestCode == 888){
         if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 888);
         }
         else {
            Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
         }
         return;
      }
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);}

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if(requestCode == 888 && resultCode == RESULT_OK && data != null){
         Uri uri = data.getData();
         try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageViewFood.setImageBitmap(bitmap);
         }
         catch
         (FileNotFoundException e) {
            e.printStackTrace();
         }    }
      super.onActivityResult(requestCode, resultCode, data);}
}
