package com.kriverdevice.gestioncitas.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.kriverdevice.gestioncitas.database.DatabaseHelper;

import java.util.ArrayList;

public class Consultorios extends DatabaseHelper implements Parcelable {

    Context context;
    private int id;
    private String descripcion;
    private String direccion;
    private String telefono;

    //Nombre de la tabla MySQL
    private String tabla = "consultorios";


    /*
        PERSISTENCIA EN LA BASE DE DATOS

     */

    /*
     * Función para insertar medicos, al momento de llamar la función se debe
     * pasar como argumento un objeto de tipo Medico
     */
    public long create() {
        /*
         * Valores es un objeto de la clase ContentValues, mediante el método
         * put asignamos un valor a cada campo de la tabla, los valores son
         * obtenidos de un objeto tipo medico el cual es pasado como argumento
         * al momento de llamar la función insertarMedico(Medico medico) desde una Activity"
         */
        ContentValues valores = new ContentValues();
        valores.put("descripcion", this.descripcion);
        valores.put("telefono", this.telefono);
        valores.put("direccion", this.direccion);
        /*
         * invocación de la función insertarRegistro a la cual le pasan
         * dos argumentos, el objeto valores y "medicos" que corresponde al
         * Nombre de la tabla en SQLite
         */
        return this.insertarRegistro(valores, tabla);
    }

    /*
     * Función obtenerMedicoPorId para obtener un medico especifico a través de un id,
     * el cual es pasado como argumento al momento de llamar la función
     * getMedico
     */
    public Consultorios getById(int id) {
         /* Instrucción SQL para buscar medicos por id, tenga en cuenta el orden
         de las columnas, asi mismo tendra que acceder a sus valores */
        String selectQuery = "";
        selectQuery += "SELECT c.id, c.descripcion, c.telefono, C.direccion";
        selectQuery += "FROM " + tabla + " c WHERE p.id ='" + id + "'";

        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        /*
         * El método rawQuery() de la clase SQLiteDatabase, recibe como
         * argumento un comando SQL a ejecutar, retorna un Cursor
         */
        c = db.rawQuery(selectQuery, null);
        /*
         * moveToFirst(): mueve el puntero del cursor al primer registro
         * obtenido.
         */
        if (c.moveToFirst()) {

            this.id = c.getInt(0);
            this.descripcion = c.getString(1);
            this.telefono = c.getString(2);
            this.direccion = c.getString(3);
        }
        db.close(); // Cierre de la conexión
        return this; // Retorno del medico obtenido en la consulta
    }

    /*
     * Función obtenerMedicoPorId para obtener un medico especifico a través de un id,
     * el cual es pasado como argumento al momento de llamar la función
     * getMedico
     */
    public Consultorios getByDescripcion(String descripcion) {
         /* Instrucción SQL para buscar medicos por id, tenga en cuenta el orden
         de las columnas, asi mismo tendra que acceder a sus valores */
        String selectQuery = "";
        selectQuery += "SELECT c.id, c.descripcion, c.telefono, c.direccion";
        selectQuery += "FROM " + tabla + " c WHERE c.descripcion ='" + descripcion + "'";

        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        /*
         * El método rawQuery() de la clase SQLiteDatabase, recibe como
         * argumento un comando SQL a ejecutar, retorna un Cursor
         */
        c = db.rawQuery(selectQuery, null);
        /*
         * moveToFirst(): mueve el puntero del cursor al primer registro
         * obtenido.
         */
        if (c.moveToFirst()) {

            this.id = c.getInt(0);
            this.descripcion = c.getString(1);
            this.telefono = c.getString(2);
            this.direccion = c.getString(3);
        }
        db.close(); // Cierre de la conexión
        return this; // Retorno del medico obtenido en la consulta
    }

    public ArrayList<Consultorios> getAllConsultorios() {
        /* lstPaciente ArrayList para almacenar todos los medicos obtenidos en la busqueda*/
        ArrayList<Consultorios> lstConsultorios = new ArrayList<Consultorios>();
        /* Instrucción SQL para obtener todos los medicos*/
        String selectQuery = "";
        selectQuery += "SELECT c.id, c.descripcion, c.telefono, c.direccion ";
        selectQuery += "FROM consultorios c";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) { /* moveToFirst() devuelve TRUE en caso de haber realizado el movimiento */
            do {
                /* Creación de un objeto de la clase Medico */
                Consultorios consultorios = new Consultorios(this.context);

                consultorios.id = c.getInt(0);
                consultorios.descripcion = c.getString(1);
                consultorios.telefono = c.getString(2);
                consultorios.direccion = c.getString(3);

                lstConsultorios.add(consultorios);
            } while (c.moveToNext()); //Mover el cursor a la siguiente posición
        }
        db.close(); //Cierre de la conexión
        return lstConsultorios; //Retorno del listado de medicos
    }

    /*
     * Función actualizarMedico sirve para actualizar los valores de un medico,
     * tiene un parametro de tipo medico, el cual es pasado como argumento al
     * momento de llamar el la función
     */
    public int update() {
        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        /*
         * valores es un objeto de la clase ContentValues, mediante el método
         * put asignamos un valor a cada campo de la tabla, los valores
         * son obtenidos del parametro tipo Paciente, los
         * valores son pasados como argumentos al momento de invocar la función
         * actualizarPaciente
         */
        ContentValues valores = new ContentValues();
        valores.put("descripcion", this.descripcion);
        valores.put("telefono", this.telefono);
        valores.put("direccion", this.direccion);
        /*
         * La función update sirve para actualizar registros en una tabla,
         * recibe como argumentos el nombre de la tabla, los nuevos valores, una
         * condición de actualización y opcionalmente un arreglo con valores de
         * reemplazo para la condición
         */
        //String[] strArray = { "" + id };
        int res = db.update(this.tabla, valores, "id=" + this.id, null);
        db.close(); // Cierre de la conexión
        return res; // Retorno del número de registros afectados
    }

    public int deleteBy(int id) {
        /*
         * SQLiteDatabase db = this.getWritableDatabase(); Crea o abre una base
         * de datos que se usara para lectura y escritura usando instrucciones
         * SQL.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(this.tabla, "id=" + id, null);
        db.close(); // Cierre de la conexión
        return res; // Retorno del número de registros afectados
    }

    public Consultorios(Context context) {
        super(context);
        this.context = context;
    }


    public Consultorios(Parcel in) {
        super(null);
        readFromParcel(in);
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
