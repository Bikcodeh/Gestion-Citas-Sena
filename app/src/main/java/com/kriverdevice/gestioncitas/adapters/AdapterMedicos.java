package com.kriverdevice.gestioncitas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Medicos;

import java.util.List;

public class AdapterMedicos extends RecyclerView.Adapter<AdapterMedicos.ViewHolder> {

    List<Medicos> sourceDataModel;
    ModulesListListener onItemListClickListener;

    public AdapterMedicos(List<Medicos> medicos, ModulesListListener onItemListClickListener) {
        this.sourceDataModel = medicos;
        this.onItemListClickListener = onItemListClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_list_medicos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.nombre.setText(sourceDataModel.get(i).getNombre());
        viewHolder.identificacion.setText(sourceDataModel.get(i).getIdentificacion());
        viewHolder.profesion.setText(sourceDataModel.get(i).getProfesion());
        viewHolder.dataModel = sourceDataModel.get(i);
    }


    @Override
    public int getItemCount() {
        return this.sourceDataModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nombre, identificacion, profesion;
        Medicos dataModel;

        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.template_list_medico_name);
            identificacion = itemView.findViewById(R.id.template_list_medico_identification);
            profesion = itemView.findViewById(R.id.template_list_medico_profession);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClickListener.OnItemListSelected(dataModel);
        }
    }
}
