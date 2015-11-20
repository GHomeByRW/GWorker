package com.ghomebyrw.gworker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.models.Job;

import java.util.List;

/**
 * Created by wewang on 11/19/15.
 */
public class JobsAdapter extends ArrayAdapter<Job> {

    private static class ViewHolder {
        TextView tvArrivalTime;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvArrivalTime.setText(job.getScheduledDateAndTime().getTimeRange());
        return convertView;
    }
}
