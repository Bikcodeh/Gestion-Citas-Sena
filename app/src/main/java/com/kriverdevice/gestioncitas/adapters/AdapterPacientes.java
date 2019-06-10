package com.kriverdevice.gestioncitas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Pacientes;

import java.util.List;

public class AdapterPacientes extends RecyclerView.Adapter<AdapterPacientes.ViewHolder> {

    List<Pacientes> sourceDataModel;
    ModulesListListener onItemListClickListener;

    public AdapterPacientes(List<Pacientes> pacientes, ModulesListListener onItemListClickListener) {
        this.sourceDataModel = pacientes;
        this.onItemListClickListener = onItemListClickListener;
    }

    public void setDataSources(List<Pacientes> sourceDataModel) {
        this.sourceDataModel = sourceDataModel;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_list_pacientes, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.nombre.setText(sourceDataModel.get(i).getNombre() + " " + sourceDataModel.get(i).getApellido());
        viewHolder.identificacion.setText(sourceDataModel.get(i).getIdentificacion());
        viewHolder.dataModel = sourceDataModel.get(i);
    }


    @Override
    public int getItemCount() {
        return this.sourceDataModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nombre, identificacion;
        Pacientes dataModel;

        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.template_list_paciente_name);
            identificacion = itemView.findViewById(R.id.template_list_paciente_identification);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClickListener.OnItemListSelected(dataModel);
        }
    }
}
