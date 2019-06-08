package com.kriverdevice.gestioncitas.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.kriverdevice.gestioncitas.Constants;
import com.kriverdevice.gestioncitas.R;
import com.kriverdevice.gestioncitas.interfaces.Main;
import com.kriverdevice.gestioncitas.interfaces.ModuleFormListener;
import com.kriverdevice.gestioncitas.interfaces.ModulesListListener;
import com.kriverdevice.gestioncitas.modules.citas.CitasForm;
import com.kriverdevice.gestioncitas.modules.citas.CitasList;
import com.kriverdevice.gestioncitas.modules.consultorios.ConsultoriosForm;
import com.kriverdevice.gestioncitas.modules.consultorios.ConsultoriosList;
import com.kriverdevice.gestioncitas.modules.medicos.MedicosForm;
import com.kriverdevice.gestioncitas.modules.medicos.MedicosList;
import com.kriverdevice.gestioncitas.modules.pacientes.PacientesForm;
import com.kriverdevice.gestioncitas.modules.pacientes.PacientesList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class DispatchModule extends Fragment implements TextWatcher, ModulesListListener, ModuleFormListener {

    Fragment mForm;
    Fragment mList;
    int title;
    int icon;

    Main mainListener;
    Runnable syncAnimFabInBottomAppbar;

    BottomAppBar appbar;
    Toolbar toolbar;
    LinearLayout filterBar;
    TextInputEditText filterTextView;

    MenuItem findMenuButton;
    MenuItem cancelMenuButton;
    MenuItem deleteMenuButton;

    FloatingActionButton fab;
    String formCurrentOperation;

    public void setModule(String module) {
        switch (module) {
            case Constants.MODULE_MEDICOS:
                mList = new MedicosList();
                mForm = new MedicosForm();
                title = R.string.medicos;
                icon = R.mipmap.doctor;
                ((MedicosList) mList).setOnItemListClickListener(this);
                ((MedicosForm) mForm).setModuleFormListener(this);
                break;
            case Constants.MODULE_PACIENTES:
                mList = new PacientesList();
                mForm = new PacientesForm();
                title = R.string.pacientes;
                icon = R.mipmap.paciente;
                ((PacientesList) mList).setOnItemListClickListener(this);
                break;
            case Constants.MODULE_CITAS:
                mList = new CitasList();
                mForm = new CitasForm();
                title = R.string.citas;
                icon = R.mipmap.cita;
                ((CitasList) mList).setOnItemListClickListener(this);
                break;
            case Constants.MODULE_CONSULTORIOS:
                mList = new ConsultoriosList();
                mForm = new ConsultoriosForm();
                title = R.string.consultorios;
                icon = R.mipmap.consultorio;
                ((ConsultoriosList) mList).setOnItemListClickListener(this);
                break;
        }
    }

    // Interfaz para disparar los metodos desde el contenedor principal
    public void setMainListener(Main mainListener) {
        this.mainListener = mainListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.shared_modules_container, container, false);

        /**
         * Toolbar
         */

        toolbar = v.findViewById(R.id.toolbarModules);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(icon);

        /**
         *   BottomAppbar
         **/

        appbar = v.findViewById(R.id.appbar);
        appbar.setNavigationOnClickListener(navigationOnClickListener);

        /*
         *   Menu BottomAppBar
         **/

        appbar.replaceMenu(R.menu.menu_appbar);
        findMenuButton = appbar.getMenu().findItem(R.id.menu_item_find);
        cancelMenuButton = appbar.getMenu().findItem(R.id.menu_item_cancel);
        deleteMenuButton = appbar.getMenu().findItem(R.id.menu_item_del);

        findMenuButton.setOnMenuItemClickListener(menuItemClickListener);
        cancelMenuButton.setOnMenuItemClickListener(menuItemClickListener);
        deleteMenuButton.setOnMenuItemClickListener(menuItemClickListener);
        findMenuButton.setVisible(true);
        cancelMenuButton.setVisible(false);
        deleteMenuButton.setVisible(false);

        /*
         *   Filter Bar
         **/

        filterBar = v.findViewById(R.id.filterBarModules);
        ImageButton filterBarCloseButton = v.findViewById(R.id.filterBarHideButtonModules);
        filterBarCloseButton.setOnClickListener(filterBarCloseButtonOnClickListener);
        filterTextView = v.findViewById(R.id.filterInputModules);
        filterTextView.addTextChangedListener(this);

        /**
         *  FloatingActionButton
         */

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(fabOnClickListener);
        changueFragment(mList, false);

        return v;
    }

    // Eventos del menú
    MenuItem.OnMenuItemClickListener menuItemClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_find:
                    filterBar.setVisibility(View.VISIBLE);
                    filterTextView.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.showSoftInput(filterTextView, 0);
                    break;
                case R.id.menu_item_cancel:
                    changueFragment(mList);
                    break;
                case R.id.menu_item_del:
                    ((ModuleFormListener) mForm).onSave(Constants.MODULE_OPERATION_DELETE);
                    break;
            }
            return false;
        }
    };

    // Muestra el dashboard
    View.OnClickListener navigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainListener.showDashboard();
        }
    };

    // Oculta la barra de busqueda
    View.OnClickListener filterBarCloseButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            filterTextView.setText("");
            filterBar.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(filterTextView.getWindowToken(), 0);
        }
    };

    // Muestra el formulario de agregar
    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (formCurrentOperation == Constants.MODULE_OPERATION_NEW) {
                mForm.setArguments(null);
                changueFragment(mForm);
            } else {
                ((ModuleFormListener) mForm).onSave(formCurrentOperation);
            }
        }
    };

    // Cambia el fragment interno al modulo (Muestra la lista o el formulario)
    void changueFragment(Fragment f) {
        changueFragment(f, true);
    }

    // Cambia el conteniodo del fragment interno por la lista o el formulario
    void changueFragment(Fragment f, boolean syncAnim) {
        final int alignFab;
        final int iconFab;
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();
        if (syncAnim){
            if( f == mForm ) {
                fragmentTransaction.setCustomAnimations(
                        R.anim.enter_fragment_anim,
                        R.anim.exit_dashboard_fragment_anim
                );
            }
            else {
                fragmentTransaction.setCustomAnimations(
                        R.anim.enter_dashboard_fragment_anim,
                        R.anim.exit_fragment_anim
                );
            }
        }

        fragmentTransaction
                .replace(R.id.containerInnerModule, f)
                .commit();

        // Configura el comportamiento de los items de menú
        if (f == mList) {

            findMenuButton.setVisible(true);
            cancelMenuButton.setVisible(false);
            deleteMenuButton.setVisible(false);
            appbar.setHideOnScroll(true);
            appbar.setHideOnScroll(true);
            formCurrentOperation = Constants.MODULE_OPERATION_NEW;
            alignFab = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER;
            iconFab = R.drawable.ic_add_black_24dp;

        } else {
            filterBar.setVisibility(View.GONE);
            findMenuButton.setVisible(false);
            cancelMenuButton.setVisible(true);
            appbar.setHideOnScroll(false);
            formCurrentOperation = Constants.MODULE_OPERATION_SAVE;
            alignFab = BottomAppBar.FAB_ALIGNMENT_MODE_END;
            iconFab = R.drawable.ic_save_black_24dp;
        }

        fab.setImageResource(iconFab);

        if (!syncAnim) return;

        // Sincroniza la animación del floating action bottom
        syncAnimFabInBottomAppbar = new Runnable() {
            @Override
            public void run() {
                appbar.setTranslationY(0);
                appbar.setFabAlignmentMode(alignFab);
            }
        };
        getView().postDelayed(syncAnimFabInBottomAppbar, 1);
    }

    /*
     *   OPERACIONES SOBRE LA LISTA
     **/

    // Escucha a la lista cuando un item a sido seleccionado para que muestre el formulario
    @Override
    public void OnItemListSelected(Parcelable data) {
        deleteMenuButton.setVisible(true);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.KEY_BUNDLE_FORM_DATA, data);
        mForm.setArguments(bundle);
        changueFragment(mForm);
    }

    // Notifica a la lista para que realice el filtro
    @Override
    public void afterTextChanged(Editable s) {
        ((ModulesListListener) mList).onSearch(s.toString());
    }

    /*
     *   OPERACIONES SOBRE EL FORMULARIO
     **/

    // Informa que ha realizado la operacion requerida sobre el formulario y se debe mostrar la
    // lista actualizada.
    @Override
    public void onBack() {
        changueFragment(mList);
        ((ModulesListListener) mList).onReloadList();
    }

    @Override
    public void onSearch(String value) {
    }

    @Override
    public void onReloadList() {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void onSave(String operation) {
    }
}
