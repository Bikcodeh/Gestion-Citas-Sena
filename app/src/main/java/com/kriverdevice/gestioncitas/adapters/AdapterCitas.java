package com.kriverdevice.gestioncitas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Citas;
import com.kriverdevice.gestioncitas.models.Consultorios;
import com.kriverdevice.gestioncitas.models.Medicos;
import com.kriverdevice.gestioncitas.models.Pacientes;

import java.util.List;

public class AdapterCitas extends RecyclerView.Adapter<AdapterCitas.ViewHolder> {

    List<Citas> sourceDataModel;
    ModulesListListener onItemListClickListener;

    public AdapterCitas(List<Citas> citas, ModulesListListener onItemListClickListener) {
        this.sourceDataModel = citas;
        this.onItemListClickListener = onItemListClickListener;
    }

    public void setDataSources(List<Citas> sourceDataModel) {
        this.sourceDataModel = sourceDataModel;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_list_citas, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Medicos medico = sourceDataModel.get(i).getMedico();
        Pacientes paciente = sourceDataModel.get(i).getPaciente();
        Consultorios consultorio = sourceDataModel.get(i).getConsultorio();

        viewHolder.consultorioAddress.setText(consultorio.getdireccion());
        viewHolder.consultorioName.setText(consultorio.getdescripcion());
        viewHolder.consultorioPhone.setText(consultorio.gettelefono());
        viewHolder.fechaHora.setText(sourceDataModel.get(i).getHoraFecha());
        viewHolder.medicoName.setText(medico.getNombre() + " " + medico.getApellido());
        viewHolder.pacienteName.setText(paciente.getNombre() + " " + paciente.getApellido());
        viewHolder.dataModel = sourceDataModel.get(i);
    }

    @Override
    public int getItemCount() {
        return this.sourceDataModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fechaHora, pacienteName, medicoName, consultorioName, consultorioPhone, consultorioAddress;
        Citas dataModel;

        ViewHolder(View itemView) {
            super(itemView);
            fechaHora = itemView.findViewById(R.id.template_list_cita_fecha_hora);
            pacienteName = itemView.findViewById(R.id.template_list_cita_paciente_name);
            medicoName = itemView.findViewById(R.id.template_list_cita_medico_name);
            consultorioName = itemView.findViewById(R.id.template_list_cita_consultorio_name);
            consultorioPhone = itemView.findViewById(R.id.template_list_cita_consultorio_telefono);
            consultorioAddress = itemView.findViewById(R.id.template_list_cita_consultorio_direccion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClickListener.OnItemListSelected(dataModel);
        }
    }
}
