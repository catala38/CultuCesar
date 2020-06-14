package com.example.cultucesar.Interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cultucesar.Data.ConexionSQLiteActividadHelper;
import com.example.cultucesar.Data.ConexionSQLiteDetalleMunicipioHelper;
import com.example.cultucesar.Data.ConexionSQLiteEventoHelper;
import com.example.cultucesar.Data.ConexionSQLiteSitiosInteresHelper;
import com.example.cultucesar.Data.ConexionSQLiteSitiosRecreativoHelper;
import com.example.cultucesar.Data.CultuCesarContract;
import com.example.cultucesar.Entidades.DestinosVo;
import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Fragments.DetalleDestinoFragment;
import com.example.cultucesar.Fragments.EventosCulturales.DetalleEventoFragment;
import com.example.cultucesar.Fragments.EventosCulturales.EventosFragment;
import com.example.cultucesar.Fragments.MainFragment;
import com.example.cultucesar.Fragments.DestinosFragment;
import com.example.cultucesar.Fragments.SitioInteres.DetalleSitioInteresFragment;
import com.example.cultucesar.Fragments.SitioInteres.SitioInteresFragment;
import com.example.cultucesar.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ConexionSQLiteEventoHelper GuardarEvento;
    ConexionSQLiteSitiosInteresHelper GuardarSitioInteres;
    ConexionSQLiteSitiosRecreativoHelper GuardarSitioRecreativo;
    ConexionSQLiteActividadHelper GuardarActividades;
    ConexionSQLiteDetalleMunicipioHelper GuardarDetalleM;


    //variable del fragment detalle
    DetalleDestinoFragment detalleDestinoFragment;
    DetalleEventoFragment detalleEventoFragment;
    DetalleSitioInteresFragment detalleSitioInteresFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ///Comando para evitar la rotacion del telefono//////////////
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        ///////////////////////////////////////////////////////////

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);


        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //cargar fragment principal en la actividad
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new MainFragment());
        fragmentTransaction.commit();

        //PINTAR LOS ICONOS
        navigationView.setItemIconTintList(null);

        getApplicationContext().deleteDatabase("eventos");
        getApplicationContext().deleteDatabase("sitiosInteres");

        GuardarEvento = new ConexionSQLiteEventoHelper(this,"eventos",null,1);
        CargarEventos();

        GuardarSitioInteres = new ConexionSQLiteSitiosInteresHelper(this,"sitiosInteres",null,1);
        CargarSitioInteres();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //para cerrar automaticamente el menu
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new MainFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.destinos){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new DestinosFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.eventos_culturales) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new EventosFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.sitios_interes) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new SitioInteresFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.sitios_recreativoss) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new EventosFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.actividades) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new EventosFragment());
            fragmentTransaction.commit();
        }
        return false;
    }


   //----ENVIO A INTERFACES DETALLE--------------
    @Override
    public void enviarDestinos(DestinosVo destinosVo) {

        detalleDestinoFragment = new DetalleDestinoFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", destinosVo);
        detalleDestinoFragment.setArguments(bundleEnvio);

        //CArgar fragment en el activity
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleDestinoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarEventoCultural(EventoVo eventoVo) {
        detalleEventoFragment = new DetalleEventoFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", eventoVo);
        detalleEventoFragment.setArguments(bundleEnvio);

        //CArgar fragment en el activity
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleEventoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarSitioInteres(SitioInteresVo sitioInteresVo) {
        detalleSitioInteresFragment = new DetalleSitioInteresFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", sitioInteresVo);
        detalleSitioInteresFragment.setArguments(bundleEnvio);

        //CArgar fragment en el activity
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleSitioInteresFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    //---- Cargar EventosCulturales ------

    public void CargarEventos(){
        festivalVallenato();
        festivalDeLaQuinta();
    }

    public void festivalVallenato(){
        SQLiteDatabase db = GuardarEvento.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_EVENTO,1);
        values.put(CultuCesarContract.MUNICIPIO_EVENTO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_EVENTO,"53 Festival de la Leyenda Vallenata");
        values.put(CultuCesarContract.INFO_EVENTO,"En 1968, tres personas pensaron que era hora de hacer algo para que todo ese acervo cultural y musical no desapareciera en las nebulosas del tiempo, y decidieron crear el Festival de La Leyenda Vallenata para recrear toda la magia de una tierra donde los mitos, las costumbres, las propias vivencias y una riqueza lingüística y oral nutren día por día la literatura y el pentagrama donde se tejen las letras y las melodías del vallenato.");
        values.put(CultuCesarContract.FECHA_EVENTO,"Del 28/04/2020 al 01/05/2020");
        values.put(CultuCesarContract.VALOR_ESTIMADO,"Desde $0 hasta $0");
        values.put(CultuCesarContract.TELEFONO,"+57 3157463143");
        values.put(CultuCesarContract.WEB,"https://festivalvallenato.com");
        values.put(CultuCesarContract.FOTO_EVENTO,R.drawable.valledupar);

        db.insert(CultuCesarContract.TABLA_EVENTO,null,values);
    }

    public void festivalDeLaQuinta(){
        SQLiteDatabase db = GuardarEvento.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_EVENTO,2);
        values.put(CultuCesarContract.MUNICIPIO_EVENTO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_EVENTO,"FESTIVAL DE LA QUINTA");
        values.put(CultuCesarContract.INFO_EVENTO,"EL FESTIVAL DE LA QUINTA es una iniciativa de un grupo de empresarios y gestores culturales del centro histórico de la ciudad de Valledupar. Quienes buscamos generar espacios culturales independientes, Incentivar el talento local y las nuevas generaciones de talentos en otras áreas y expresiones culturales que permitan aumentar la oferta de estas en la Ciudad.");
        values.put(CultuCesarContract.FECHA_EVENTO,"Del 17/08/2020 al 18/08/2020");
        values.put(CultuCesarContract.VALOR_ESTIMADO,"Desde $0 hasta $50.000");
        values.put(CultuCesarContract.TELEFONO,"No disponible");
        values.put(CultuCesarContract.WEB,"https://www.festivaldelaquinta.com/");
        values.put(CultuCesarContract.FOTO_EVENTO,R.drawable.valledupar);

        db.insert(CultuCesarContract.TABLA_EVENTO,null,values);
    }



    //------- Cargar Sitios de interes ----------

    public void CargarSitioInteres(){
        //HOTELES
        GuardarHotelBoutique();
        //RESTAURANTES
        GuardarRestauranteCH();
        //BARES
        GuardarTierraCantores();
    }

    public void GuardarHotelBoutique(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,1);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"CASA DE LOS SANTOS REYES");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"se jodio por la cuarentena");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $0 hasta $0");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 3157463143");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"Clic para ir al sitio web");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.gohan_cara1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.valledupar1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.valledupar2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.valledupar3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarRestauranteCH(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,2);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Compa chipuco");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"se jodio por la cuarentena");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $0 hasta $0");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 3157463143");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"Clic para ir al sitio web");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.goku_cara2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.valledupar2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.valledupar1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.valledupar3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarTierraCantores(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,3);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"BARES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Tierra de cantores");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"se jodio por la cuarentena");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $0 hasta $0");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 3157463143");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"Clic para ir al sitio web");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.gohan_cara1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES,R.drawable.valledupar3);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES  ,R.drawable.valledupar2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES  ,R.drawable.valledupar1);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }










}
