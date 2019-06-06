package com.kriverdevice.gestioncitas.interfaces;

import android.os.Parcelable;

public interface ModulesListListener {
    void OnItemListSelected(Parcelable data);

    void onSearch(String value);

    void onReloadList();
}
