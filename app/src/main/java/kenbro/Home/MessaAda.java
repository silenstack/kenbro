package com.example.kenbro.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kenbro.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessaAda extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MessaMode> list;
    private Context ctx;

    public MessaAda(Context context, List<MessaMode> list) {
        this.list = list;
        ctx = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.veve_base, viewGroup, false);
        viewHolder = new OriginalViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hotel, int i) {
        if (hotel instanceof OriginalViewHolder) {
            final OriginalViewHolder viewHolder = (OriginalViewHolder) hotel;
            final MessaMode get = list.get(i);
            String memes = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            String hilly;
            if (get.getDate().equals(memes)) {
                hilly = get.getTime();
            } else {
                hilly = get.getDate() + " " + get.getTime();
            }
            if (get.getStaged().equals("R")) {
                viewHolder.mess.setText("from: " + get.getReference() + "\n" + get.getMessage());
                viewHolder.dat.setText(hilly);
                //viewHolder.two.setBackgroundColor(Color.parseColor("#A6A3A3"));
                viewHolder.cardtwo.setVisibility(View.VISIBLE);
            } else {
                viewHolder.Message.setText("sentTo: " + get.getReference() + "\n" + get.getMessage());
                viewHolder.DateMes.setText(hilly);
                //viewHolder.one.setBackgroundColor(Color.parseColor("#9AD182"));
                viewHolder.cardone.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView Message, DateMes, mess, dat;
        public RelativeLayout one, two;
        public CardView cardone, cardtwo;

        public OriginalViewHolder(@NonNull View view) {
            super(view);
            Message = view.findViewById(R.id.messOne);
            DateMes = view.findViewById(R.id.dateOne);
            cardone = view.findViewById(R.id.cardOne);
            one = view.findViewById(R.id.relaOne);
            mess = view.findViewById(R.id.messTwo);
            dat = view.findViewById(R.id.datTwo);
            cardtwo = view.findViewById(R.id.cardTwo);
            two = view.findViewById(R.id.relaTwo);
        }
    }
}

