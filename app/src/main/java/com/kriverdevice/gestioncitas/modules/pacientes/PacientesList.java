package com.kriverdevice.gestioncitas.modules.pacientes;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.adapters.AdapterPacientes;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Pacientes;

import java.util.ArrayList;
import java.util.Iterator;

public class PacientesList extends Fragment implements ModulesListListener {

    RecyclerView mList;
    ArrayList<Pacientes> pacientes = new ArrayList<Pacientes>();

    ModulesListListener onItemListClickListener;

    AdapterPacientes adapterPacientes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.shared_modules_recycler_view, container, false);
        RecyclerView mList = v.findViewById(R.id.listadoModules);

        adapterPacientes = new AdapterPacientes(pacientes, this);

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(adapterPacientes);

        onReloadList();

        return v;
    }

    // Establece el listener del evento cuando se seleccione un item de la lista
    public void setOnItemListClickListener(ModulesListListener onItemListClickListener) {
        this.onItemListClickListener = onItemListClickListener;
    }


    // Informa al DispacherModule para que muestre el formulario.
    @Override
    public void OnItemListSelected(Parcelable data) {
        onItemListClickListener.OnItemListSelected(data);
    }

    // Es informado para que realice el filtro sobre la lista
    @Override
    public void onSearch(String value) {
        if (value.isEmpty()) {
            adapterPacientes.setDataSources(pacientes);
            return;
        }

        // Clona el origen de datos de la lista y realiza el filtro por el valor pasado
        ArrayList<Pacientes> fPaciente = (ArrayList<Pacientes>) pacientes.clone();

        for (Iterator<Pacientes> i = fPaciente.iterator(); i.hasNext(); ) {
            if (!i.next().getIdentificacion().contains(value)) {
                i.remove();
            }
        }

        adapterPacientes.setDataSources(fPaciente);
    }

    @Override
    public void onReloadList() {
        pacientes.clear();
        pacientes.addAll(new Pacientes(getContext()).getAllPacientes());
        adapterPacientes.notifyDataSetChanged();
    }
}
