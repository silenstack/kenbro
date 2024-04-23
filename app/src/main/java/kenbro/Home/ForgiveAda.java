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

public class ForgiveAda extends ArrayAdapter<ForgiveMode> {
    public ArrayList<ForgiveMode> MainList;
    public ArrayList<ForgiveMode> SubjectListTemp;
    public SubjectDataFilter subjectDataFilter;

    public ForgiveAda(Context context, int id, ArrayList<ForgiveMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.aba, null);

            holder = new ViewHolder();


            holder.Mpesa = convertView.findViewById(R.id.myText);
            holder.Amount = convertView.findViewById(R.id.numbered);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ForgiveMode subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.Amount.setText(pos + ".");
        holder.Mpesa.setText("iD: "+subject.getSupplier()+"\nname: "+subject.getFullname()+"\namount: KEs"+subject.getAmount()+"\nmpesa: "+subject.getMpesa()+"\ndate: "+subject.getDate());
        pos++;
        return convertView;

    }

    public class ViewHolder {
        TextView Mpesa, Amount;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<ForgiveMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    ForgiveMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<ForgiveMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }
}