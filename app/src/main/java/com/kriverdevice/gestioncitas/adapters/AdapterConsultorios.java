package com.kriverdevice.gestioncitas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Consultorios;

import java.util.List;

public class AdapterConsultorios extends RecyclerView.Adapter<AdapterConsultorios.ViewHolder> {

    List<Consultorios> sourceDataModel;
    ModulesListListener onItemListClickListener;

    public AdapterConsultorios(List<Consultorios> consultorios, ModulesListListener onItemListClickListener) {
        this.sourceDataModel = consultorios;
        this.onItemListClickListener = onItemListClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_list_consultorio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.descripcion.setText(sourceDataModel.get(i).getdescripcion());
        viewHolder.direccion.setText(sourceDataModel.get(i).getdireccion());
        viewHolder.telefono.setText(sourceDataModel.get(i).gettelefono());
        viewHolder.dataModel = sourceDataModel.get(i);
    }


    @Override
    public int getItemCount() {
        return this.sourceDataModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView descripcion, direccion, telefono;
        Consultorios dataModel;

        ViewHolder(View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.template_list_consultorio_name);
            direccion = itemView.findViewById(R.id.template_list_consultorio_direccion);
            telefono = itemView.findViewById(R.id.template_list_consultorio_telefono);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClickListener.OnItemListSelected(dataModel);
        }
    }
}
