package com.kriverdevice.gestioncitas.modules.consultorios;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.adapters.AdapterConsultorios;
import com.kriverdevice.gestioncitas.interfaces.Main;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.models.Consultorios;

import java.util.ArrayList;

public class ConsultoriosList extends Fragment implements ModulesListListener {

    Main mainListener;
    RecyclerView mList;
    ArrayList<Consultorios> consultorios = new ArrayList<Consultorios>();

    AdapterConsultorios adapterConsultorios;

    public void setMainListener(Main mainListener) {
        this.mainListener = mainListener;
    }

    ModulesListListener onItemListClickListener;

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
        // TODO: Filtar el valor del array
        Toast.makeText(getContext(), "Filtrar el valor: " + value, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReloadList() {
        consultorios.clear();

        // TODO: Llenar la lista de citas
        for (int i = 0; i <= 20; i++) {
            consultorios.add(new Consultorios(i, "Consultorio", "Tef:  " + i, "Calle " + i + " Carrera " + i));
        }
        adapterConsultorios.notifyDataSetChanged();
    }
}
