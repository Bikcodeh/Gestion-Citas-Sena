package com.kriverdevice.gestioncitas;

public class Constants {
    private static final Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    private Constants() {
    }

    public static final String MODULE_MEDICOS = "MEDICOS";
    public static final String MODULE_PACIENTES = "PACIENTES";
    public static final String MODULE_CITAS = "CITAS";
    public static final String MODULE_CONSULTORIOS = "CONSULTORIOS";

    public static final String MODULE_OPERATION_NEW = "NEW";
    public static final String MODULE_OPERATION_UPDATE = "UPDATE";
    public static final String MODULE_OPERATION_DELETE = "DELETE";
    public static final String MODULE_OPERATION_SAVE = "SAVE";

    public static final String KEY_BUNDLE_FORM_DATA = "DATA";
}
