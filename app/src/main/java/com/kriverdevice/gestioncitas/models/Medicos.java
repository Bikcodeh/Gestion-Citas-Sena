package com.kriverdevice.gestioncitas.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kriverdevice.gestioncitas.database.DatabaseHelper;

import java.util.ArrayList;

public class Medicos extends DatabaseHelper implements Parcelable {

    private String nombre;
    private String apellido;
    private String identificacion;
    private int profesion;
    private int id;
    private Context context;

    //Nombre de la tabla MySQL
    private String tabla = "medicos";

    public Medicos Medicos(String nombre, String apellido, String identificacion, int profesion) {
        return new Medicos(null, nombre, apellido, identificacion, profesion);
    }

    public Medicos(@Nullable Integer id, String nombre, String apellido, String identificacion, int profesion) {
        super(null);
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.profesion = profesion;
        this.id = id;
        this.apellido = apellido;
    }

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
        valores.put("identificacion", this.identificacion);
        valores.put("nombres", this.nombre);
        valores.put("apellidos", this.apellido);
        valores.put("especialidad_id", this.profesion);
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
    public Medicos getById(int id) {
         /* Instrucción SQL para buscar medicos por id, tenga en cuenta el orden
         de las columnas, asi mismo tendra que acceder a sus valores */
        String selectQuery = "";
        selectQuery += "SELECT m.id, m.identificacion, m.nombres, m.apellidos, e.id, e.descripcion ";
        selectQuery += "FROM medicos m, especialidads e WHERE m.especialidad_id = e.id ";
        selectQuery += "AND m.id= " + id;
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
            this.identificacion = c.getString(1);
            this.nombre = c.getString(2);
            this.apellido = c.getString(3);
            this.profesion = c.getInt(4);

        }
        db.close(); // Cierre de la conexión
        return this; // Retorno del medico obtenido en la consulta
    }

    /*
     * Función obtenerMedicoPorIdentificacion para obtener un medico especifico a través de una identificacion,
     * el cual es pasado como argumento al momento de llamar la función
     * getMedico
     */
    public Medicos getByIdentificacion(String identificacion) {

        String selectQuery = "";
        selectQuery += "SELECT m.id, m.identificacion, m.nombres, m.apellidos, e.id, e.descripcion ";
        selectQuery += "FROM medicos m, especialidads e WHERE m.especialidad_id = e.id ";
        selectQuery += "AND m.identificacion= " + identificacion;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;

        c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            this.id = c.getInt(0);
            this.identificacion =  c.getString(1);
            this.nombre =  c.getString(2);
            this.apellido =  c.getString(3);
            this.profesion =  c.getInt(4);
        }
        db.close(); // Cierre de la conexión
        return this; // Retorno del medico obtenido en la consulta
    }

    public ArrayList<Medicos> getAllMedicos() {
        /* lstMedicos ArrayList para almacenar todos los medicos obtenidos en la busqueda*/
        ArrayList<Medicos> lstMedicos = new ArrayList<Medicos>();
        /* Instrucción SQL para obtener todos los medicos*/
        String selectQuery = "";
        selectQuery += "SELECT m.id, m.identificacion, m.nombres, m.apellidos, e.id, e.descripcion ";
        selectQuery += "FROM medicos m, especialidads e WHERE m.especialidad_id = e.id ";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = null;

        c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) { /* moveToFirst() devuelve TRUE en caso de haber realizado el movimiento */
            do {
                /* Creación de un objeto de la clase Medico */
                Medicos medico = new Medicos(this.context);

                medico.id = c.getInt(0);
                medico.identificacion =  c.getString(1);
                medico.nombre =  c.getString(2);
                medico.apellido =  c.getString(3);
                medico.profesion =  c.getInt(4);
                lstMedicos.add(medico);
            } while (c.moveToNext()); //Mover el cursor a la siguiente posición
        }
        db.close(); //Cierre de la conexión
        return lstMedicos; //Retorno del listado de medicos
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
        valores.put("identificacion", this.identificacion);
        valores.put("nombres", this.nombre);
        valores.put("apellidos", this.apellido);
        valores.put("especialidad_id", this.profesion);
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

    /*
     *       CONSTRUCTORES REQUERIDOS
     * */

    public Medicos(Context context) {
        super(context);
        this.context = context;
    }

    public Medicos(Parcel in) {
        super(null);
        readFromParcel(in);
    }

    /**
     * FUNCIONES DE LOS PARCELABLES
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.identificacion);
        dest.writeInt(this.profesion);
        dest.writeString(this.apellido);
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.identificacion = in.readString();
        this.profesion = in.readInt();
    }

    public static final Creator<Medicos> CREATOR = new Creator<Medicos>() {
        @Override
        public Medicos createFromParcel(Parcel in) {
            return new Medicos(in);
        }

        @Override
        public Medicos[] newArray(int size) {
            return new Medicos[size];
        }
    };

    /*
     *       GETTERS AND SETTERS
     * */

    public void setProfesion(String profesion) {
        this.profesion = new Especialidades(this.context).getId(profesion);
        Log.d("***->", "Profesion Id: " + this.profesion);
    }

    public String getProfesion() {
        return new Especialidades(this.context).getEspecialidad(this.profesion);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return this.apellido;
    }

}
