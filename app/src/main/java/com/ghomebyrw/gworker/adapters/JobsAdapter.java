package com.ghomebyrw.gworker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.JobStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wewang on 11/19/15.
 */
public class JobsAdapter extends ArrayAdapter<Job> {

    private static class ViewHolder {
        TextView tvArrivalTime;
        Spinner spnJobStatus;
        TextView tvAddress;
        TextView tvPrice;
        TextView tvContact;
    }

    public JobsAdapter(Context context, List<Job> jobs) {
        super(context, android.R.layout.simple_list_item_1, jobs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Job job = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.job_summary, parent, false);

            viewHolder.tvArrivalTime = (TextView) convertView.findViewById(R.id.tvArrivalTime);
            viewHolder.spnJobStatus = (Spinner)convertView.findViewById(R.id.spnStatus);


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    Arrays.toString(JobStatus.values()).replaceAll("^.|.$", "").split(", "));
            viewHolder.spnJobStatus.setAdapter(spinnerArrayAdapter);
            viewHolder.spnJobStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            viewHolder.tvContact = (TextView) convertView.findViewById(R.id.tvContact);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvArrivalTime.setText(job.getScheduledDateAndTime().getTimeRange());
        viewHolder.tvAddress.setText(job.getLocation());
        viewHolder.tvPrice.setText(getContext().getString(R.string.price_label)
                .replace("{:formattedAmount}", job.getAcceptedPrice().getFormattedAmount()));
        viewHolder.tvContact.setText(getContext().getString(R.string.sample_contact_label)
                .replace("{:name}", job.getFieldworker()));
        return convertView;
    }
}
