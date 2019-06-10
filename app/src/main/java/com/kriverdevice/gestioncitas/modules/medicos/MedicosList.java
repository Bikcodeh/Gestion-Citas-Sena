package com.kriverdevice.gestioncitas.modules.medicos;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.adapters.AdapterMedicos;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Medicos;

import java.util.ArrayList;
import java.util.Iterator;

public class MedicosList extends Fragment implements ModulesListListener {

    RecyclerView mList;
    ArrayList<Medicos> medicos = new ArrayList<Medicos>();

    ModulesListListener onItemListClickListener;

    AdapterMedicos adapterMedicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.shared_modules_recycler_view, container, false);
        mList = v.findViewById(R.id.listadoModules);

        adapterMedicos = new AdapterMedicos(medicos, this);

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(adapterMedicos);

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
    public void onSearch(final String value) {

        if (value.isEmpty()) {
            adapterMedicos.setDataSources(medicos);
            return;
        }

        // Clona el origen de datos de la lista y realiza el filtro por el valor pasado
        ArrayList<Medicos> fMedicos = (ArrayList<Medicos>) medicos.clone();

        for (Iterator<Medicos> i = fMedicos.iterator(); i.hasNext(); ) {
            if (!i.next().getNombre().contains(value)) {
                i.remove();
            }
        }

        adapterMedicos.setDataSources(fMedicos);
    }

    @Override
    public void onReloadList() {
        medicos.clear();
        medicos.addAll( new Medicos(getContext()).getAllMedicos()  );
        adapterMedicos.notifyDataSetChanged();
    }
}
