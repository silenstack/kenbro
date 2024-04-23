package com.example.kenbro.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.kenbro.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Messing extends ArrayAdapter<StagedAda> {
    public ArrayList<StagedAda> MainList;
    public ArrayList<StagedAda> SubjectListTemp;
    public MessageDataFilter messageDataFilter;

    public Messing(Context context, int id, ArrayList<StagedAda> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (messageDataFilter == null) {

            messageDataFilter = new MessageDataFilter();
        }
        return messageDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.alien, null);

            holder = new ViewHolder();
            holder.user = convertView.findViewById(R.id.messager);
            holder.date = convertView.findViewById(R.id.messaging);
            holder.message = convertView.findViewById(R.id.messaged);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StagedAda subject = SubjectListTemp.get(position);
        holder.user.setText(subject.getName() + "  " + subject.getPhone());
        String dater = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String meja;
        if (subject.getDate().equals(dater)) {
            meja = subject.getTime();
        } else {
            meja = subject.getDate() + " " + subject.getTime();
        }
        holder.date.setText(meja);
        holder.message.setText(subject.getMessage());
        return convertView;

    }

    public class ViewHolder {
        TextView user, date, message;
    }

    private class MessageDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<StagedAda> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    StagedAda subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<StagedAda>) filterResults.values;
            notifyDataSetChanged();
            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));
            notifyDataSetInvalidated();
        }
    }
}
