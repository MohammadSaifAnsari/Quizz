package com.saif.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.myapplication.Activity.TestActivity;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Model.CategoryModel;
import com.saif.myapplication.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryModel>categoryList;
    Context context;

    public CategoryAdapter(ArrayList<CategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.category_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryList.get(position);
        holder.cat_name.setText(categoryModel.getNAME());
        holder.test_No.setText(categoryModel.getNO_OF_TEST());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TestActivity.class);
//                intent.putExtra("CATEGORY_NAME",holder.getAbsoluteAdapterPosition());
                dbQuery.selected_Category_Index = holder.getAbsoluteAdapterPosition();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView cat_name,test_No;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name = itemView.findViewById(R.id.categoryName);
            test_No = itemView.findViewById(R.id.testNo);
        }
    }
}
