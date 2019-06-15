package com.kriverdevice.gestioncitas.modules.citas;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.adapters.AdapterCitas;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Citas;

import java.util.ArrayList;
import java.util.Iterator;

public class CitasList extends Fragment implements ModulesListListener {

    RecyclerView mList;
    ArrayList<Citas> citas = new ArrayList<Citas>();

    ModulesListListener onItemListClickListener;

    AdapterCitas adapterCitas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.shared_modules_recycler_view, container, false);
        RecyclerView mList = v.findViewById(R.id.listadoModules);

        adapterCitas = new AdapterCitas(citas, this);

        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(adapterCitas);

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
            adapterCitas.setDataSources(citas);
            return;
        }

        // Clona el origen de datos de la lista y realiza el filtro por el valor pasado
        ArrayList<Citas> fcitas = (ArrayList<Citas>) citas.clone();

        for (Iterator<Citas> i = fcitas.iterator(); i.hasNext(); ) {
            if (!i.next().getPaciente().getIdentificacion().contains(value)) {
                i.remove();
            }
        }

        adapterCitas.setDataSources(fcitas);
    }

    @Override
    public void onReloadList() {

        citas.clear();
        citas.addAll(new Citas(getContext()).getAllCitas());
        adapterCitas.notifyDataSetChanged();
    }

}
