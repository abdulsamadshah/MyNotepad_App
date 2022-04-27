package com.example.mynotepadapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotepadapp.Database.DBhelper;
import com.example.mynotepadapp.Models.Models;
import com.example.mynotepadapp.NoteWriteActivity;
import com.example.mynotepadapp.R;

import java.nio.file.LinkPermission;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Adapters extends RecyclerView.Adapter<Adapters.AdapterViewholder> implements Filterable{


    Context context;
    ArrayList<Models> models;
    ArrayList<Models> backup;

    public Adapters(Context context, ArrayList<Models> models) {
        this.context = context;
        this.models = models;
        backup=new ArrayList<>(models);
    }

    @NonNull
    @Override
    public AdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_layout, parent, false);
        return new AdapterViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterViewholder holder, int position) {

        Models modelss=models.get(position);
        holder.titles.setText(modelss.getName());
        holder.description.setText(modelss.getDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NoteWriteActivity.class);
               intent.putExtra("title",modelss.getName());
                intent.putExtra("description",modelss.getDescription());
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return models.size();
    }



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        // background thread
        protected FilterResults performFiltering(CharSequence keyword)
        {
            ArrayList<Models> filtereddata=new ArrayList<>();

            if(keyword.toString().isEmpty())
                filtereddata.addAll(backup);

            else {
                for(Models obj : backup)
                {
                    if(obj.getName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @Override  // main UI thread
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            models.clear();
            models.addAll((ArrayList<Models>)results.values);
            notifyDataSetChanged();
        }
    };



    public class AdapterViewholder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        DBhelper dBhelper =new DBhelper(context);
        TextView titles,description;
        TextView dateandtime;
        public AdapterViewholder(@NonNull View itemView) {
            super(itemView);


            titles=itemView.findViewById(R.id.titles);
            description=itemView.findViewById(R.id.description);
            dateandtime=itemView.findViewById(R.id.dateandtime);

            itemView.setOnLongClickListener(this);
        }


        //DATA DELETE FOR RECYCLERVIEW
        @Override
        public boolean onLongClick(View view) {
            int postions=getAdapterPosition();
            Toast.makeText(itemView.getContext(),"item deleted:"+models.get(postions).getName().toString(),Toast.LENGTH_SHORT).show();
            models.remove(postions);
            String nameTXT = titles.getText().toString();
                dBhelper.deletedata(String.valueOf(nameTXT));
            notifyItemRemoved(postions);
            return true;
        }
    }
}
