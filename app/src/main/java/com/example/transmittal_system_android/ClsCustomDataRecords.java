package com.example.transmittal_system_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClsCustomDataRecords extends BaseAdapter {
    Context context;

    ClsDTDataRecords clsDTDataRecords;

    private static LayoutInflater inflater = null;

    public ClsCustomDataRecords(Context context, ClsDTDataRecords clsDTDataRecords) {
        this.context = context;

        this.clsDTDataRecords = clsDTDataRecords;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (clsDTDataRecords.getDataRecordsArrayList() == null) ? 0 : clsDTDataRecords.getDataRecordsArrayList().size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView t_RRNumber;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ClsCustomDataRecords.Holder holder = new ClsCustomDataRecords.Holder();

        View rowView = inflater.inflate(R.layout.custom_datarecords, null);

        holder.t_RRNumber = rowView.findViewById(R.id.t_RRNumber);

        holder.t_RRNumber.setText(clsDTDataRecords.getDataRecordsArrayList().get(i).getRrNo());

        rowView.setTag(holder.t_RRNumber.getText().toString());

        return rowView;
    }
}
