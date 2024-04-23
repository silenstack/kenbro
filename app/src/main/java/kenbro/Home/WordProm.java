package com.example.kenbro.Home;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kenbro.R;

import java.util.ArrayList;

public class WordProm extends ArrayAdapter<CartModel> {
    public ArrayList<CartModel> MainList;
    public ArrayList<CartModel> SubjectListTemp;
    public WordProm.SubjectDataFilter subjectDataFilter;

    public WordProm(Context context, int id, ArrayList<CartModel> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.category, null);

            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.theImage);
            holder.textView = convertView.findViewById(R.id.prodUp);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartModel subject = SubjectListTemp.get(position);
        holder.textView.setText(subject.getCategory() + " " + subject.getType() + "\nQTY. " + subject.getQuantity() + " X KES. " + subject.getPrice() + "\nKES" + String.format("%.0f", (Float.parseFloat(subject.getQuantity()) * Float.parseFloat(subject.getPrice()))));
        Glide.with(convertView).load(subject.getImage()).into(holder.imageView);
        return convertView;

    }

    public class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<CartModel> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    CartModel subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<CartModel>) filterResults.values;



            clear

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}