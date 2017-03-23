package globalstd.globalaudit.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import javax.inject.Inject;

import globalstd.globalaudit.GlobalAuditException;
import globalstd.globalaudit.R;
import globalstd.globalaudit.dialogs.ConfirmDialog;
import globalstd.globalaudit.dialogs.ConfirmLogOut;
import globalstd.globalaudit.fragments.HomeFragment;
import globalstd.globalaudit.fragments.ListSupplierFragment;
import globalstd.globalaudit.fragments.ListUsersFragment;
import globalstd.globalaudit.services.AuthService;
import globalstd.globalaudit.utils.ICallConfirmLogOut;

import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity implements ICallConfirmLogOut {
    @Inject
    AuthService authService;

    private DrawerLayout drawerLayout; //Instancia del drawer
    private String drawerTitle; //Titulo inicial del drawer 1D1D1D
    private String ACTIONBAR_COLOR="#272727";
    public static CharSequence mTitle;
    public NavigationView navigationView;

    Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;

    // TAGs
    private static final String TAG_HOME = "tag_home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(); // Setear Toolbar como action bar
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.menu_home);
        if (savedInstanceState == null) {
            selectItem(getString(R.string.menu_home));
        }

        /*
        user=getUser();
        if(user!=null){
            updateMenu(true);
        }else{
            updateMenu(false);
            if (AccessToken.getCurrentAccessToken() != null) {
                updateMenu(true);
            }
        }*/
        updateMenu(true);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment;

        Fragment tagLogin = getSupportFragmentManager().findFragmentByTag(TAG_LOGIN);

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (tagLogin!=null || tagProfile!=null || tagTraining!=null || tagCreditable!=null  || tagStock!=null
                        || tagResult!=null || tagHistorical!=null || tagReasons!=null) {
                    fragment = new HomeFragment();
                    back(fragment, TAG_HOME);
                }

                if(tagPdf!=null){
                    if(tagPdf.isVisible()){
                        fragment = new CoursesDetailsFragment();
                        back(fragment, TAG_COURSES_DETAILS);
                    }
                }
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }*/

    private void back(Fragment fragment, String TAG) {
        //zoom de retorno
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        //fragmentManager.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentManager.replace(R.id.main_content, fragment, TAG);
        fragmentManager.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    // btn's actionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void showCars(){}

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();

                        selectItem(title);
                        mTitle = title;
                        return true;
                    }
                }
        );
    }

    /*Pasando la posicion de la opcion en el menu nos mostrara el activity correspondiente*/
    private void selectItem(String opcSelected) {
        getSupportActionBar().setTitle(opcSelected);

        // Enviar título como arguemento del fragmento
        Fragment fragment = null;
        String TAG = "TEST_A";

        if (opcSelected.equals(getString(R.string.menu_home))) {
            mTitle = getString(R.string.menu_home);
            TAG = TAG_HOME;
            fragment = new HomeFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_standards))) {
            mTitle = getString(R.string.menu_standards);
            TAG = TAG_HOME;
            //fragment = new LoginFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_my_checklist))) {
            mTitle = getString(R.string.menu_my_checklist);
            TAG = TAG_HOME;
            //fragment = new NewsFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_suppliers))) {
            mTitle = getString(R.string.menu_suppliers);
            TAG = "tag_suppliers";
            fragment = new ListSupplierFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_users))) {
            mTitle = getString(R.string.menu_users);
            TAG = "tag_users";
            fragment = new ListUsersFragment();
        }


        else if (opcSelected.equals(getString(R.string.menu_settings))) {
            /*
            mTitle = getString(R.string.menu_settings);
            TAG = TAG_HOME;
            //fragment = new SettingsFragment();
            SettingsFragment fragmentS = new SettingsFragment();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if(saveInstanceState==null){
                fragmentTransaction.add(R.id.R), fragment, "settings_fragment");
                fragmentTransaction .commit();
            }else{
                fragment=getFragmentManager().findFragmentByTag("settings_fragment")
            }
            */
        }

        else if (opcSelected.equals(getString(R.string.menu_logout))) {
            confirmLogOut();
        }


        else{
            fragment = new HomeFragment();
            //fragment = new LoginFragment();
        }


        if (fragment != null) {
            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            //fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentManager.replace(R.id.main_content, fragment, TAG);
            //fragmentManager.addToBackStack(TAG);
            fragmentManager.commit();

        } else {
            //Log.e("Error  ", "MostrarFragment" + position);//Si el fragment es nulo mostramos un mensaje de error.
        }
        drawerLayout.closeDrawers(); // Cerrar drawer
        setTitle(mTitle); // Setear título actual

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        //actionBar.setLogo(R.drawable.icon_header);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(ACTIONBAR_COLOR)));//Cambia el color de fondo de ActionBar
    }

    public void setActionBarTitle(String title){

        setTitle(title);
        getSupportActionBar().setTitle(title);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        mTitle=title;
        actionBar.setTitle(title);

    }

    /*
    * 1= Staff
    * 2= Patrocinador
    * 3= Asistente*/
    public void updateMenu(boolean login){
        navigationView.getMenu().clear();
        if(login){
            navigationView.inflateMenu(R.menu.nav_menu_in);
        }else{
           //navigationView.inflateMenu(R.menu.nav_menu_out);
        }
    }

    public void menssage(String menssage) {
        Snackbar.make(navigationView, menssage, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void confirmLogOut(){
        ConfirmDialog objConfirm = new ConfirmDialog(getResources().getString(R.string.confirm), getResources().getString(R.string.message_logout),getResources().getString(R.string.yes), "No");
        FragmentManager fragmentManager = getSupportFragmentManager();
        new ConfirmLogOut(objConfirm).show(fragmentManager, "ConfirmLogOut");
    }

    @Override
    public void onPossitiveBtnClick() {
        this.eventBus.post(new LogoutEvent());
    }

    @Override
    public void onNegativeBtnClick() {

    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLogout(LogoutEvent event) {
        GlobalAuditException error = null;
        try {
            authService.logout();
        } catch(GlobalAuditException e) {
            error = e;
        }
        eventBus.post(new LogoutResponseEvent(error));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutResponseEvent(LogoutResponseEvent event) {
        if (event.error != null) {
            switch (event.error.getCode()) {
                case GlobalAuditException.INVALID_CREDENTIALS:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.invalid_credentials), Snackbar.LENGTH_LONG);
                    snackbar.show();

                    break;

                case GlobalAuditException.INTERNET_ERROR:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.internet_error), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;

                case GlobalAuditException.SERVER_ERROR:
                    snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.server_error), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
            }
        }
        //Sucess
        else{
            showLogin();
        }
    }

    private void showLogin() {
        Intent i = new Intent( getApplicationContext(), LoginActivity.class);
        //Intent i = new Intent( getApplicationContext(), MainEmuledActivity.class);
        //Intent i = new Intent( getApplicationContext(), DirectoryFragment.class);
        startActivity( i );
        finish();
    }

    private static class LogoutEvent {}

    private static class LogoutResponseEvent {
        public GlobalAuditException error;
        public LogoutResponseEvent(GlobalAuditException error) {
            this.error = error;
        }
    }
}

