package com.ghomebyrw.gworker.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.activities.PriceUpdateActivity;
import com.ghomebyrw.gworker.clients.GoogleStaticMapAPI;
import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.JobStatus;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wewang on 11/19/15.
 */
public class JobsAdapter extends ArrayAdapter<Job> {
    private static class ViewHolder {
        TextView tvStartTimeHour;
        TextView tvStartTimeAmPm;
        TextView tvEndTimeHour;
        TextView tvEndTimeAmPm;
        Spinner spnJobStatus;
        TextView tvAddress;
        TextView tvPrice;
        TextView tvContact;
        ImageView ivMap;
        TextView tvPriceUpdate;
        ImageView ivPhone;
        ImageView ivMessage;
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

            viewHolder.tvStartTimeHour = (TextView) convertView.findViewById(R.id.tvStartTimeHour);
            viewHolder.tvStartTimeAmPm = (TextView) convertView.findViewById(R.id.tvStartTimeAMPM);
            viewHolder.tvEndTimeHour = (TextView) convertView.findViewById(R.id.tvEndTimeHour);
            viewHolder.tvEndTimeAmPm = (TextView) convertView.findViewById(R.id.tvEndTimeAMPM);
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
            viewHolder.ivMap = (ImageView) convertView.findViewById(R.id.ivMap);
            viewHolder.tvPriceUpdate = (TextView) convertView.findViewById(R.id.tvUpdatePriceLabel);
            viewHolder.tvPriceUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PriceUpdateActivity.class);
                    intent.putExtra("price", job.getAcceptedPrice());
                    getContext().startActivity(intent);
                }
            });
            viewHolder.ivPhone = (ImageView) convertView.findViewById(R.id.ivPhone);
            viewHolder.ivPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + job.getCustomerPhoneNumber()));
                    getContext().startActivity(callIntent);
                }
            });

            viewHolder.ivMessage = (ImageView) convertView.findViewById(R.id.ivMessage);
            viewHolder.ivMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri smsUri = Uri.parse("tel:" + job.getCustomerPhoneNumber());
                    Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                    intent.putExtra("address", job.getCustomerPhoneNumber());
                    intent.setType("vnd.android-dir/mms-sms");
                    getContext().startActivity(intent);
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvStartTimeHour.setText(job.getScheduledDateAndTime().getStartTimeHour());
        viewHolder.tvStartTimeAmPm.setText(job.getScheduledDateAndTime().getStartTimeAMPM());
        viewHolder.tvEndTimeHour.setText(getContext().getString(R.string.sample_end_time_hour)
                .replace("{:h}", job.getScheduledDateAndTime().getEndTimeHour()));
        viewHolder.tvEndTimeAmPm.setText(job.getScheduledDateAndTime().getEndTimeAMPM());
        viewHolder.tvAddress.setText(job.getLocation().getAddress());
        viewHolder.tvPrice.setText(getContext().getString(R.string.price_label)
                .replace("{:formattedAmount}", job.getAcceptedPrice().getFormattedAmount()));
        viewHolder.tvContact.setText(getContext().getString(R.string.sample_contact_label)
                .replace("{:name}", job.getFieldworker()));

        Picasso.with(getContext()).load(GoogleStaticMapAPI.getStaticMapURL(job.getLocation())).into(viewHolder.ivMap);

        return convertView;
    }

}
