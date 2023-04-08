package com.example.boilerconnect.Model.DataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.boilerconnect.Model.Entity.Report;
import com.example.boilerconnect.R;

import java.util.List;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ViewHolder> {
    private List<Report> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public ReportListAdapter(Context context, List<Report> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Report theReport = mData.get(position);
        holder.thisSerialNumber.setText(theReport.getSerialNumber());
        holder.thisDateIntervention.setText("Fait le " + theReport.getDateIntervention());
        holder.thisDateEntryService.setText("En service le " + theReport.getDateEntryService());
        holder.thisName.setText(theReport.getName());
        holder.thisSurname.setText(theReport.getSurname());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Report getItem(int id) {
        return mData.get(id);
    }

    public void setItem(int id, Report c) { mData.set(id,c); }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void uploadInterventions();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView thisSerialNumber;
        TextView thisDateIntervention;

        TextView thisDateEntryService;
        TextView thisName;

        TextView thisSurname;

        ViewHolder(View itemView) {
            super(itemView);
            thisSerialNumber = itemView.findViewById(R.id.tvValueSerialNumber);
            thisDateIntervention = itemView.findViewById(R.id.tvValueDateIntervention);
            thisDateEntryService = itemView.findViewById(R.id.tvValueDateEntryService);
            thisName = itemView.findViewById(R.id.tvValueName);
            thisSurname = itemView.findViewById(R.id.tvValueSurname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}