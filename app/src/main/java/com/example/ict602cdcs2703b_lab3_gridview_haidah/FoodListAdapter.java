package com.example.ict602cdcs2703b_lab3_gridview_haidah;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

   private Context context;
   private int layout;
   private ArrayList<Food> foodsList;

   public FoodListAdapter(Context context, int layout, ArrayList<Food> foodsList) {
      this.context = context;
      this.layout = layout;
      this.foodsList = foodsList;
   }

   @Override
   public int getCount() {
      return foodsList.size();
   }

   @Override
   public Object getItem(int position) {
      return foodsList.get(position);
   }

   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup viewGroup) {
      ViewHolder holder;

      if (convertView == null) {
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = inflater.inflate(layout, null);

         holder = new ViewHolder();
         holder.txtName = convertView.findViewById(R.id.txtName);
         holder.txtPrice = convertView.findViewById(R.id.txtPrice);
         holder.imageView = convertView.findViewById(R.id.imgFood);

         convertView.setTag(holder);
      } else {
         holder = (ViewHolder) convertView.getTag();
      }

      Food food = foodsList.get(position);
      holder.txtName.setText(food.getName());
      holder.txtPrice.setText(food.getPrice());

      // Decode byte array to Bitmap and set it to ImageView
      byte[] foodImage = food.getImage();
      Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
      holder.imageView.setImageBitmap(bitmap);

      return convertView;
   }

   private static class ViewHolder {
      ImageView imageView;
      TextView txtName, txtPrice;
   }
}