package com.kriverdevice.gestioncitas.models;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.database.DatabaseHelper;

import java.util.ArrayList;

public class Especialidades extends DatabaseHelper {
    private int id;
    private String profesion;
    private Context context;

    public Especialidades(Context context) {
        super(context);
        this.context = context;
    }

    public @Nullable
    Integer getId(String profesion) {
        Integer id = null;

        this.db = this.getWritableDatabase();
        Cursor profession = this.db.rawQuery("SELECT id FROM especialidads  WHERE descripcion='" + profesion + "' LIMIT 1", null);
        if (profession.moveToFirst()) {
            id = profession.getInt(0);
        }
        this.db.close();

        return id;
    }

    public String getEspecialidad(int id) {
        String professionDesc = this.context.getString(R.string.oops_records);

        this.db = this.getWritableDatabase();
        Cursor profession = this.db.rawQuery("SELECT descripcion FROM especialidads  WHERE id='" + id + "' LIMIT 1", null);
        if (profession.moveToFirst()) {
            professionDesc = profession.getString(0);
        }
        this.db.close();

        return professionDesc;
    }

    public ArrayList<String> getEspecialidades(){
        ArrayList<String> especialidades = new ArrayList<>();

        this.db = this.getWritableDatabase();
        Cursor profession = this.db.rawQuery("SELECT descripcion FROM especialidads", null);
        while(profession.moveToNext()) {
            especialidades.add( profession.getString(0) );
        }
        this.db.close();

        return  especialidades;
    }
}
