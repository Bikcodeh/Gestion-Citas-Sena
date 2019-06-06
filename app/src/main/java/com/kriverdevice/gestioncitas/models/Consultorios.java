package com.kriverdevice.gestioncitas.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Consultorios implements Parcelable {

    private String descripcion;
    private String direccion;
    private String telefono;
    private int id;

    public Consultorios() {
    }

    public Consultorios(Parcel in) {
        readFromParcel(in);
    }

    public Consultorios Consultorios(String descripcion, String telefono, String direccion) {
        return new Consultorios(0, descripcion, telefono, direccion);
    }

    public Consultorios(int id, String descripcion, String telefono, String direccion) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getdireccion() {
        return direccion;
    }

    public void settelefono(String telefono) {
        this.telefono = telefono;
    }

    public String gettelefono() {
        return telefono;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.descripcion);
        dest.writeString(this.direccion);
        dest.writeString(this.telefono);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.descripcion = in.readString();
        this.direccion = in.readString();
        this.telefono = in.readString();
    }

    public static final Creator<Consultorios> CREATOR = new Creator<Consultorios>() {
        @Override
        public Consultorios createFromParcel(Parcel in) {
            return new Consultorios(in);
        }

        @Override
        public Consultorios[] newArray(int size) {
            return new Consultorios[size];
        }
    };
}
