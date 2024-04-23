package com.example.kenbro.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.kenbro.R;

import java.util.ArrayList;

public class PesaAda extends ArrayAdapter<ModelBid> {
    public ArrayList<ModelBid> bidModels;
    public ArrayList<ModelBid> bidModelArrayList;
    public BidDateFilter bidDateFilter;

    public PesaAda(Context context, int id, ArrayList<ModelBid> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.bidModelArrayList = new ArrayList<>();

        this.bidModelArrayList.addAll(subjectArrayList);

        this.bidModels = new ArrayList<>();

        this.bidModels.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (bidDateFilter == null) {

            bidDateFilter = new BidDateFilter();
        }
        return bidDateFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.see_b, null);

            holder = new ViewHolder();
            holder.SubjectQuantity = convertView.findViewById(R.id.appPName);
            holder.subjecType = convertView.findViewById(R.id.appType);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelBid subject = bidModelArrayList.get(position);
        int pos = position + 1;
        holder.SubjectQuantity.setText(pos + ". " + subject.getCategory());
        pos++;
        holder.subjecType.setText(subject.getType() + "\nRequired: " + subject.getQuantity());
        return convertView;

    }

    public class ViewHolder {
        TextView SubjectQuantity, subjecType;
    }

    private class BidDateFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<ModelBid> arrayList1 = new ArrayList<>();

                for (int i = 0, l = bidModels.size(); i < l; i++) {
                    ModelBid subject = bidModels.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = bidModels;

                    filterResults.count = bidModels.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            bidModelArrayList = (ArrayList<ModelBid>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = bidModelArrayList.size(); i < l; i++)
                add(bidModelArrayList.get(i));
            notifyDataSetInvalidated();
        }
    }
}
