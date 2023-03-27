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
    private List<Report> mData; // notre liste de donnée
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener; //un listener pour gérer le clic

    public ReportListAdapter(Context context, List<Report> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data; // on lie les données reçu en parametre à notre attribut
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // on crée notre viewHolder en liant celui ci à notre layout item : recycler_view_item
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // on récupére notre objet Contact correspondant à la position
        Report lecontact = mData.get(position);
        // puis on lie chaque élément de la vue
        // (dont les identifiants sont définis dans la classe viewholder ci-dessous)
        // a chacun des attributs de contacts correspondants
        holder.thisSerialNumber.setText(lecontact.getSerialNumber());
        holder.thisDateIntervention.setText(lecontact.getDateIntervention());
        holder.thisDateEntryService.setText(lecontact.getDateEntryService());
        holder.thisName.setText(lecontact.getName());
        holder.thisSurname.setText(lecontact.getSurname());
    }

    // la méthode qui retourne le nombre total d'éléments dans la RV
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public Report getItem(int id) {
        return mData.get(id);
    }

    public void setItem(int id, Report c) { mData.set(id,c); }

    // Lie l'interface d'écoute d'un item à notre écouteur
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // le parent (notre DataActivity devra implémenter cette interface pour pouvoir gerer les clicks sur un élément)
    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void uploadInterventions();
    }

    // Définition du ViewHolder de notre Adapter (implémentant le OnClickListener)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // On déclare les différents éléments de vue de l'item
        TextView thisSerialNumber;
        TextView thisDateIntervention;

        TextView thisDateEntryService;
        TextView thisName;

        TextView thisSurname;

        ViewHolder(View itemView) {
            super(itemView);
            //on lie les identifiants déclaré dans la vue à nos éléments
            thisSerialNumber = itemView.findViewById(R.id.tvValueSerialNumber);
            thisDateIntervention = itemView.findViewById(R.id.tvValueDateIntervention);
            thisDateEntryService = itemView.findViewById(R.id.tvValueDateEntryService);
            thisName = itemView.findViewById(R.id.tvValueName);
            thisSurname = itemView.findViewById(R.id.tvValueSurname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //sur un clic, on renvoie (par le biais de l'interface), la vue et surtout la position dans l'adapter
            // necessaire pour savoir quel item est concerné par le clic
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}