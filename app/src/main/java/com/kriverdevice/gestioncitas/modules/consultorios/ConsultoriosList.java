package com.kriverdevice.gestioncitas.modules.consultorios;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.adapters.AdapterConsultorios;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Consultorios;

import java.util.ArrayList;
import java.util.Iterator;

public class ConsultoriosList extends Fragment implements ModulesListListener {

    RecyclerView mList;
    ArrayList<Consultorios> consultorios = new ArrayList<Consultorios>();

    ModulesListListener onItemListClickListener;

    AdapterConsultorios adapterConsultorios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.shared_modules_recycler_view, container, false);
        RecyclerView mList = v.findViewById(R.id.listadoModules);

        adapterConsultorios = new AdapterConsultorios(consultorios, this);

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(adapterConsultorios);

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
            adapterConsultorios.setDataSources(consultorios);
            return;
        }

        // Clona el origen de datos de la lista y realiza el filtro por el valor pasado
        ArrayList<Consultorios> fconsultorios = (ArrayList<Consultorios>) consultorios.clone();

        for (Iterator<Consultorios> i = fconsultorios.iterator(); i.hasNext(); ) {
            if (!i.next().getdescripcion().contains(value)) {
                i.remove();
            }
        }

        adapterConsultorios.setDataSources(fconsultorios);
    }

    @Override
    public void onReloadList() {
        consultorios.clear();
        consultorios.addAll(new Consultorios(getContext()).getAllConsultorios());
        adapterConsultorios.notifyDataSetChanged();
    }
}
