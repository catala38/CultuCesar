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
import android.content.Intent;
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
import com.example.cultucesar.Entidades.ActividadesVo;
import com.example.cultucesar.Entidades.DetalleMunicipioVo;
import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.Fragments.Actividades.ActiviadadesFragment;
import com.example.cultucesar.Fragments.Actividades.DetalleActividadesFragment;
import com.example.cultucesar.Fragments.DetalleMunicipio.DetalleDetalleMunicipioFragment;
import com.example.cultucesar.Fragments.DetalleMunicipio.DetalleMunicipioFragment;
import com.example.cultucesar.Fragments.EventosCulturales.DetalleEventoFragment;
import com.example.cultucesar.Fragments.EventosCulturales.EventosFragment;
import com.example.cultucesar.Fragments.MainFragment;
import com.example.cultucesar.Fragments.SitioInteres.DetalleSitioInteresFragment;
import com.example.cultucesar.Fragments.SitioInteres.SitioInteresFragment;
import com.example.cultucesar.Fragments.SitioRecreativo.DetalleSitioRecreativoFragment;
import com.example.cultucesar.Fragments.SitioRecreativo.SitioRecreativoFragment;
import com.example.cultucesar.R;
import com.example.cultucesar.seleccionar_destino;
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

    DetalleEventoFragment detalleEventoFragment;
    DetalleSitioInteresFragment detalleSitioInteresFragment;
    DetalleSitioRecreativoFragment detalleSitioRecreativoFragment;
    DetalleActividadesFragment detalleActividadesFragment;
    DetalleDetalleMunicipioFragment detalleDetalleMunicipioFragment;




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
        getApplicationContext().deleteDatabase("sitiosRecreativo");
        getApplicationContext().deleteDatabase("actividades");
        getApplicationContext().deleteDatabase("detalle");


        GuardarEvento = new ConexionSQLiteEventoHelper(this,"eventos",null,1);
        CargarEventos();

        GuardarSitioInteres = new ConexionSQLiteSitiosInteresHelper(this,"sitiosInteres",null,1);
        CargarSitioInteres();

        GuardarSitioRecreativo = new ConexionSQLiteSitiosRecreativoHelper(this,"sitiosRecreativo",null,1);
        GuardarSitiosRecreativos();

        GuardarActividades = new ConexionSQLiteActividadHelper(this,"actividades",null,1);
        GuardarActividades();

        GuardarDetalleM = new ConexionSQLiteDetalleMunicipioHelper(this,"detalle",null,1);
        GuardarDetallesMunicipio();


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
        if(menuItem.getItemId() == R.id.sitios_recreativos) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new SitioRecreativoFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.actividades) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new ActiviadadesFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.acerca_departamento) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new DetalleMunicipioFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.municipio) {
            Intent intent = new Intent(getApplicationContext(), seleccionar_destino.class);
            startActivity(intent);
        }
        return false;
    }



    //----ENVIO A INTERFACES DETALLE--------------


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

    @Override
    public void enviarSitioRecreativo(SitioRecreativoVo sitioRecreativoVo) {
        detalleSitioRecreativoFragment = new  DetalleSitioRecreativoFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", sitioRecreativoVo);
        detalleSitioRecreativoFragment.setArguments(bundleEnvio);

        //CArgar fragment en el activity
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleSitioRecreativoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarActividades(ActividadesVo actividadesVo) {
        detalleActividadesFragment = new DetalleActividadesFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", actividadesVo);
        detalleActividadesFragment.setArguments(bundleEnvio);

        //CArgar fragment en el activity
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleActividadesFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarDetalleMunicipio(DetalleMunicipioVo detalleMunicipioVo) {
        detalleDetalleMunicipioFragment = new DetalleDetalleMunicipioFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", detalleMunicipioVo);
        detalleDetalleMunicipioFragment.setArguments(bundleEnvio);

        //CArgar fragment en el activity
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleDetalleMunicipioFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    //---- Cargar EventosCulturales ------

    public void CargarEventos(){
        festivalVallenato();
        festivalDeLaQuinta();
        festivalDeLaVirgen();
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
        values.put(CultuCesarContract.FOTO_EVENTO,R.drawable.festivalvallenato);

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
        values.put(CultuCesarContract.FOTO_EVENTO,R.drawable.festivalquinta);

        db.insert(CultuCesarContract.TABLA_EVENTO,null,values);
    }

    public void festivalDeLaVirgen(){
        SQLiteDatabase db = GuardarEvento.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_EVENTO,3);
        values.put(CultuCesarContract.MUNICIPIO_EVENTO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_EVENTO,"Fiestas patronales de la Virgen del Carmen");
        values.put(CultuCesarContract.INFO_EVENTO,"Disfruta la excelente programación de las fiestas patronales de la Virgen del Carmen y el festival del agua, el café y las artesanías arhuacas desde13 de julio al 16 de julio en el Municipio de Pueblo Bello, un lugar de espiritualidad, cultura y naturaleza.");
        values.put(CultuCesarContract.FECHA_EVENTO,"Del 13/07/2020 al 16/07/2020");
        values.put(CultuCesarContract.VALOR_ESTIMADO,"Desde $0 hasta $50.000");
        values.put(CultuCesarContract.TELEFONO,"No disponible");
        values.put(CultuCesarContract.WEB,"https://www.agendavallenata.com/eventer/fiestas-patronales-de-la-virgen-del-carmen-y-el-festival-del-agua-el-cafe-y-las-artesanias-arhuacas/");
        values.put(CultuCesarContract.FOTO_EVENTO,R.drawable.pueblobelloevento1);

        db.insert(CultuCesarContract.TABLA_EVENTO,null,values);
    }

    //------- Cargar Sitios de interes ----------

    public void CargarSitioInteres(){
        //HOTELES
        GuardarHotelBoutique();
        GuardarHotelSicarare();
        GuardarHotelSonesta();
        GuardarHotelReyReyes();
        GuardarHotelMares();
        GuardarHotelHelenita();
        GuardarHotelHeliconias();
        //RESTAURANTES
        GuardarRestauranteCH();
        GuardarRestauranteVaradero();
        GuardarRestauranteMarianamen();
        GuardarRestauranteMontacarga();

        GuardarRestaurantePalmas();
        GuardarRestauranteMiPuebloBello();
        //BARES
        GuardarTierraCantores();
        GuardarPatioBar();
        GuardarTlonBar();
        GuardarPalenke();
        GuardarESTACOHELADERIA();
    }
      /////////////////////// HOTELES ///////////////////////////////////////////////////
    public void GuardarHotelBoutique(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,1);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"CASA DE LOS SANTOS REYES HOTEL BOUTIQUE");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Casa de Los Santos Reyes Hotel Boutique, Una joya colonial patrimonial del siglo XVIII ubicado en el centro histórico de Valledupar, Colombia.\n" +
                "Cuenta con 8 habitaciones privadas cuidadosamente restauradas, diseñadas, confortablemente amobladas y decoradas; cada una con un ambiente exclusivo y acogedor.\n" +
                "Una conexión con la historia, la riqueza cultural, la naturaleza, las tradiciones, donde se crean espacios que realzan el placer del descanso y el silencio.\n" +
                "Desayunos típicos y generosos, donde los colores y sabores se conjugan para darle sentido a las mañanas.\n" +
                "Un pequeño hotel boutique para vivirlo, sentirlo y disfrutarlo.");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $150,000 hasta $250,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"(+57)(5) 5801782");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.hotelboutiquevalledupar.com/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.casareyesdetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.hotelboutique1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.hotelboutique2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.hotelboutique3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarHotelSicarare(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,2);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"HOTEL SICARARE");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"El Hotel Sicarare se encuentra a solo 500 metros del ayuntamiento y cuenta con una piscina cubierta. Se proporciona conexión WiFi gratuita y el desayuno. La avenida principal está a 200 metros.Las habitaciones del Sicarare Hotel son amplias y cuentan con aire acondicionado, TV por cable y minibar. Las instalaciones de las habitaciones incluyen hamacas.\n" +
                "Algunas cuentan con zona de estar independiente y TV de plasma. Todos los días se sirve un desayuno bufé en el bar. También hay un restaurante que prepara platos locales e internacionales. Los huéspedes pueden relajarse en la tumbona junto a la piscina. También hay un salón de belleza.\n" +
                "Se ofrece aparcamiento gratuito y se puede asegurar el servicio de transporte al aeropuerto. El aeropuerto Alfonso López Pumarejo se encuentra a 4,9 km.");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $160,000 hasta $240,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"5849283 – 3205329242");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://hotelsicarare.mas57.co/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.sicararedetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.sicarare1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.sicarare2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.sicarare3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarHotelSonesta(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,3);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"SONESTA HOTEL VALLEDUPAR");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Sonesta Hotel Valledupar ofrece a sus huéspedes una experiencia única. Para mejorar su estancia, le ofrecemos una gran variedad de servicios:\n" +
                "\n" +
                "Centro de negocios o E Corner con acceso a Internet\n" +
                "Internet inalámbrico en zonas públicas\n" +
                "Servicio de habitaciones 6:00 am - 10:00 pm\n" +
                "Piscina al aire libre\n" +
                "Salas VIP\n" +
                "Servicio de lavandería\n" +
                "Almacenamiento de equipaje");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $140,000 hasta $250,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"5748686");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.sonesta.com/es");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.sonestadetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.sonesta1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.sonesta2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.sonesta3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarHotelReyReyes(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,4);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"HOTEL REY DE REYES");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Diag. 21 No 28-67 Av. Fundacion Valledupar");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $50,000 hasta $150,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"313 5544055");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.facebook.com/Hotel-Rey-De-Reyes-170516773156458/?ref=page_internal&path=%2FHotel-Rey-De-Reyes-170516773156458%2F");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.reydereyesdetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.reydereyes1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.reydereyes2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.reydereyes3 );

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }


    public void GuardarHotelMares(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,5);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Mares Hotel");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"El un hotel sencillo en el centro del poblado, con habitaciones pequeñas y grandes.En la parte trasera tienen espacio techado para realizar eventos o reuniones, con mesas y sillas del propio hotel. Además de una cocina.\n" +
                "Algo especial con las hamacas en la parte de atrás del segundo piso, con una vista libre de las montañas de la Sierra Nevada.");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $50,000 hasta $120,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+573184587756");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.facebook.com/people/Mares-Hotel-Pueblo-Bello/100009079136045");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.pueblobellomareshotel1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.pueblobellomareshotel2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.pueblobellomareshotel3);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.pueblobellomareshotel1);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarHotelHelenita(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,6);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"La Helenita");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Excelente lugar, propicio para descansar y disfrutar de la naturaleza, adicional dentro de la Finca cuentan con un recorrido por un río que te lleva hacia una cascada y subir hasta un peñon donde tienes una panorámica de Pueblo Bello y si está despejado hasta puedes apreciar el nevado de Santa Marta.");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $50,000 hasta $150,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+573154091279");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.facebook.com/fincahostal.lahelenita");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.pueblobellohelenita1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.pueblobellohelenita2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.pueblobellohelenita3);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.pueblobellohelenita1);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }


    public void GuardarHotelHeliconias(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,7);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"HOTEL");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Hotel Campestre las Heliconias");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Ven a pueblo bello y disfrutar de esta hermosa casa campestre donde se brinda servicios como hospedaje ,restaurante piscinas parqueadero bebidas  todo al alcance de tu presupuesto .Ven para tener el gusto de atenderte");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $40,000 hasta $170,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"313 5634829");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.facebook.com/casacampolasheliconias/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.pueblobelloheliconias);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.pueblobelloheliconias2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.pueblobelloheliconias);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.pueblobelloheliconias3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }




    /////////////////// RESTAURANTES ////////////////////////////////////////////
    public void GuardarRestauranteCH(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,8);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"RESTAURANTE COMPAE CHIPUCO");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Compae chipuco es un acogedor restaurante al estilo de un patio típico del Valle del cacique Upar, decorado al estilo colonial, la tradición vive en cada rincón, donde podrá apreciar la calidad y el toque especial de los sabores de Valledupar y sus alrededores.\n" +
                "\n" +
                "Compae Chipuco, es una fiesta en el paladar, un juego de pupilas que translada a turistas y viajeros a aquel viejo valledupar lleno de costumbres, historias y leyendas a través de los versos cantados en las notas de la caja, guacharaca y acordeón.\n" +
                "\n" +
                "El chef nos ofrece una carta de comida tradicional, donde incluye diferentes ingredientes de la cocina popular, creando de esta manera platos de excelente calidad y mucho sabor como bien lo son EL CHIVO, LA LENGUA Y RABO EN SALSA CHIPUCO y mucho más!\n" +
                "\n" +
                "Dentro de nuestro menú contamos con más de 40 opciones para desgustarte, preparados con esmero y dedicación para permitirte vivir una excelente experiencia en nuestro restobar.\n" +
                "Cra. 6 #No. 16-24");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $25,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"5805635");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"http://www.compaechipucorestobar.com/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.compaechipuco1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.compaechipuco1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.compaechipuco2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.compaechipuco3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarRestauranteVaradero(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,9);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"RESTAURANTE VARADERO");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Disfruta de una excelente comida de mar, con un agradable ambiente caribeño. Ubicado en un exclusivo sector de Valledupar, con parqueadero privado.\n" +
                "Calle 12 # 6 - 56");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $27,000 hasta $80,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"3176678861");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/varaderorestaurante/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.varaderodetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.varadero1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.varadero2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.varadero3 );

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarRestauranteMarianamen(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,10);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"RESTAURANTE MARIA NAMEN");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"MARIA NAMEN es un Restaurante representativo en el Valle de Upar. Su nombre esta ligado al folclor y la buena comida de la región.\n" +
                "Calle 6 #9-64");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $30,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"5838837-3206373534");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/restaurantemarianamen/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.marianamendetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.marianamen1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.marianamen2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.marianamen3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarRestauranteMontacarga(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,11);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"RESTAURANTE MONTACARGA");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Comida típica colombiana, con el sazón más especial y una gran relación precio/sabor.\n" +
                "\n" +
                "Carrera 19 # 5 - 30");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $40,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"5899989 - 3014917950");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/montacarganorte/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.montacargadetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.montacarga1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.montacarga2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.montacarga3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }


    public void GuardarRestaurantePalmas(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,12);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Restaurante Las Palmas");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Excelente sitio para almorzar y disfrutar en familia ubicado en la  CALLE 9 # 12-12 AV PPAL 201008 Pueblo Bello");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $25,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+573135126183");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.facebook.com/pages/category/Restaurant/Restaurante-Las-Palmas-Pueblo-Bello-1649527845149751/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.pueblobellorestaurantelaspalmas1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.pueblobellorestaurantelaspalmas2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.pueblobellorestaurantelaspalmas3);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.pueblobellorestaurantelaspalmas1);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }


    public void GuardarRestauranteMiPuebloBello(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,13);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"RESTAURANTE");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Restaurante Mi Pueblo Bello");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Comidas corrientes y ala carta, platos especiales y refrigerios para eventos, Comodas rápidas y todo lo relacionado para su paladar #PuebloBelloCesar");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $35,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"3135233614");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.facebook.com/pages/category/Chicken-Joint/Restaurante-Mi-Pueblo-Bello-2043287632585231/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.pueblobellorestaurante1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES ,R.drawable.pueblobellorestaurante2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES ,R.drawable.pueblobellorestaurante1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES ,R.drawable.pueblobellorestaurante2);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }




    /////////////////// BARES ////////////////////////////////////////////
    public void GuardarTierraCantores(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,14);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"BARES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"TIERRA DE CANTORES");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"En una de las avenidas más entretenidas y activas de vida nocturna, el Tierra de Cantores, es un Bar que resalta por su calidad y sobretodo por la música en vivo, donde grandes exponentes del vallenato se presentan todos los fines de semana!\n" +
                "\n" +
                "Tiene una terraza al aire libre y un escenario climatizado para disfrutar de todo el espectáculo, con amigos.\n" +
                "\n" +
                "Qué viva la parranda, que viva la Tierra de Cantores!");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $250,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 3205100934");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/tierradecantores/?hl=es");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.tierracantoresdetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES,R.drawable.tierracantores1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES  ,R.drawable.tierracantores2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES  ,R.drawable.tierracantores3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarPatioBar(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,15);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"BARES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"EL PATIO BAR");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Lugar acogedor, un bar de los pocos que son diferentes en valledupar, música variada, pista de baile, atención increíble, una micheladas inolvidable, además de que el lugar está muy bien ubicado en pleno centro de la ciudad, y ofrece una gran variedad de cervezas nacionales e importadas.\n" +
                "\n" +
                "El lugar perfecto pa irse de farra en valledupar.");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $150,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 3005007120");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/elpatiobar_valledupar/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.patiobardetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES,R.drawable.patiobar1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES  ,R.drawable.patiobar2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES  ,R.drawable.patiobar3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarTlonBar(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,16);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"BARES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"Tlön-Bar");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Tlön Bar es un espacio alternativo en donde la imaginación tergiversa la realidad, en donde no buscamos la verosimilitud, sino el asombro.\n" +
                "\n" +
                "Cl. 15 #No.6-54, Valledupar, Cesar");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $180,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 3005237208-5898210");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/tlonbar/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.tlonbardetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES,R.drawable.tlonbar1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES  ,R.drawable.tlonbar2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES  ,R.drawable.tlonbar3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarPalenke(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,17);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"BARES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"PALENKE");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Una experinecia que te conecta con el legado de nuestros ancestros africanos.\n" +
                " Patrimonio,Historia y Cultura en el Centro Historico de Valledupar.\n"+
                "\n" +
                "Carrera 15 N. 6 - 74 Centro Histórico, Valledupar");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $200,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"+57 315 2354378");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://www.instagram.com/palenkeculturabar/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.palenkedetalle);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES,R.drawable.palenke1);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES  ,R.drawable.palenke2);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES  ,R.drawable.palenke3);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }

    public void GuardarESTACOHELADERIA(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,18);
        values.put(CultuCesarContract.TIPO_SITIO_INTERES,"BARES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_INTERES,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_INTERES,"LA OFICINA ESTACO HELADERIA");
        values.put(CultuCesarContract.INFO_SITIO_INTERES,"Excelente lugar para pasar un rato con amigos y familia.");
        values.put(CultuCesarContract.VALOR_RANGO_SITIO_INTERES,"Desde $10,000 hasta $100,000");
        values.put(CultuCesarContract.TELEFONO_SITIO_INTERES,"318 6393829");
        values.put(CultuCesarContract.WEB_SITIO_INTERES ,"https://la-oficina-estaco-heladeria.negocio.site/");
        values.put(CultuCesarContract.FOTO_SITIO_INTERES ,R.drawable.pueblobellooficinaestanco);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO1_INTERES,R.drawable.pueblobellooficinaestanco);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO2_INTERES  ,R.drawable.pueblobellooficinaestanco);
        values.put(CultuCesarContract.IMG_DETALLE_SITIO3_INTERES  ,R.drawable.pueblobellooficinaestanco);

        db.insert(CultuCesarContract.TABLA_SITIO_INTERES,null,values);
    }




    //----------------- CARGAR SITIOS RECREATIVOS --------------------------
    public void GuardarSitiosRecreativos(){
        ////BALNEAREOS////
        GuardarRioGuatapuri();
        GuardarRepresa();
        GuardarTranquilandia();
        ////CENTRO RECREACIONALES/////
        GuardarPedregosa();
        GuardarEscuelaAmbiental();
        GuardarInterpretacionArhuaca();
        GuardarVillaSthefany();
        ///PARQUES/////
        GuardarProvincia();
        GuardarCristoRey();
        GuardarParqueAlgarrobillos();
        GuardarParqueElViajero();
        GuardarParqueCentral();
        GuardarMirador();

    }

    /////////////BALNEAREOS/////////////
    public void GuardarRioGuatapuri(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,1);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"BALNEARIOS");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Río Guatapurí");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Nace en la laguna Curigua, de la Sierra Nevada de Santa Marta, y en un vertiginoso descenso de 80 kilómetros entrega sus aguas a Valledupar. Su balneario Hurtado, de cristalinas aguas, genera un ambiente refrescante donde los turistas pueden bañarse; sus contorneadas rocas enmarcan paisajes que han contribuido a crear misteriosas leyendas y son fuente de inspiración de melodías del folclor vallenato.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/R%C3%ADo+Guatapuri/@10.501246,-73.2710014,20z/data=!4m5!3m4!1s0x8e8ab9658189339b:0x15d36ea4274d074d!8m2!3d10.5607458!4d-73.3560067?hl=es-ES");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.valledupar);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.rioguatapuri2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.riogutapuri3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarRepresa(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,2);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"BALNEARIOS");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"LA REPRESA");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Un excelente lugar para disfrutar en familia y amigos.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/La+represa+Balneario+Pueblo+Bello/@10.4509729,-73.5828283,17.5z/data=!4m5!3m4!1s0x8ef54f9d6810954d:0x3731ab3560fa37b1!8m2!3d10.4509323!4d-73.5814892");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pueblobellorepresa1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pueblobellorepresa2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pueblobellorepresa3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarTranquilandia(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,3);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"BALNEARIOS");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Cascada La Tranquilidad");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Naturaleza virgen, paisajes increíbles.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.es/maps/place/Cascada+La+Tranquilidad/@10.4324974,-73.6158744,15z/data=!4m8!1m2!2m1!1sbalnearios+en+Pueblo+Bello,+Cesar!3m4!1s0x0:0xfa8fc4eeb63ca5f9!8m2!3d10.4324981!4d-73.6158738");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pueblobelloeventotranquilandia1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pueblobelloeventotranquilandia2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pueblobelloeventotranquilandia1);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }






    ///////////////////CENTRO RECREACIONALES/////////////////////
    public void GuardarPedregosa(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,4);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"CENTRO RECREACIONALES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"CENTRO RECREACIONAL LA PEDREGOSA");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Uno de los más tradicionales espacios de Comfacesar, es el Centro Recreacional La Pedregosa, desde donde se ofrecen, juegos infantiles, zona para camping, canchas múltiples, senderos, amplias zonas verdes, piscinas familiares, infantil, semi olímpica, tobogán y un kiosco campestre, dispuesto para la realización de eventos como matrimonios, fiestas infantiles, empresariales con capacidad para alrededor de 200 personas.\n" +
                "\n" +
                "En este espacio se está construyendo un moderno auditorio con capacidad para 800 personas, con un diseño moderno y completamente dotado para todo tipo de reuniones, convenciones o actividades académicas y empresariales. Otro de los servicios que se ofrece desde la Pedregosa, es la recreación dirigida para eventos y suministro de logística.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/La+Pedregosa/@10.4997196,-73.2789035,18z/data=!4m14!1m8!2m7!1sLa+pedregosa+valledupar!3m5!1sLa+pedregosa+valledupar!2s10.501238,+-73.271070!4m2!1d-73.2710704!2d10.5012381!3m4!1s0x0:0xd59c01e25587e1e1!8m2!3d10.4998342!4d-73.2784509?hl=es-ES");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pedregosa1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pedregosa2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pedregosa3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }

    public void GuardarEscuelaAmbiental(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,5);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"CENTRO RECREACIONALES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"ESCUELA AMBIENTAL");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"educación ambiental que trabaja en la búsqueda de educar, concienciar y preservar los valores de los más pequeños, en armonía con el entorno paisajístico y ecológico.\n" +
                "\n" +
                "Cuenta con tres hectáreas aproximadamente, donde se realizan las actividades de convivencia y socialización ecológica, así como talleres de recuperación de materiales desechables, reelaboración de papel, cuenta con un espejo de agua recreativo, cabañas, piscinas de lodo terapéutico, talasoterapia, de rosas, boliche-terapia, baño sauna y el más reciente la Cochinoterapia.\n" +
                "\n" +
                "La Escuela Ambiental pretende que no solo los niños disfruten de ella, sino que además los adultos también vayan a interactuar con el agua, la tierra, que respiren el aire puro.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/Escuela+Ambiental+Valledupar/@10.5010631,-73.2744132,19z/data=!3m1!4b1!4m12!1m6!3m5!1s0x8e8ab9bc270c523b:0x654884d6a441f49d!2sEscuela+Ambiental+Valledupar!8m2!3d10.5010618!4d-73.273866!3m4!1s0x8e8ab9bc270c523b:0x654884d6a441f49d!8m2!3d10.5010618!4d-73.273866?hl=es");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.escuelaambiental1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.escuelaambiental2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.escuelaambiental3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarInterpretacionArhuaca(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,6);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"CENTRO RECREACIONALES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Centro de Interpretacion Arhuaca");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"En el municipio de Pueblo Bello, Comfacesar ofrece el Centro de Interpretación  de la Cultura Arhuaca, una réplica del pueblo ancestral de Nabusimake, un lugar ideal para la reflexión. Sus cabañas trasmiten calidez y comodidad; Su arquitectura, las manifestaciones culturales que hacen de este, un lugar perfecto para la desconexión, trascendencia y la autenticidad, permitiendo un encuentro con la etnia indígena y propiciando un espacio para el reencuentro consigo mismo a 16°C.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.es/maps/place/Centro+de+Interpretacion+Arhuaca/@10.4156883,-73.5938094,16z/data=!4m8!1m2!2m1!1scentro+recreacionales+pueblo+bello!3m4!1s0x8ef54ef3e1722027:0x71bd95a7f2e022d7!8m2!3d10.4196685!4d-73.5886332");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pueblobelloarhuaca);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pueblobelloarhuaca2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pueblobelloarhuaca3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarVillaSthefany(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,7);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"CENTRO RECREACIONALES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Centro Recreacional VillaSthefany");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"El Villasthefany se encuentra en Pueblo Bello y dispone de restaurante, bar y jardín. Se ofrece aparcamiento privado por un suplemento.\n" +
                "\n" +
                "Todos los días se sirve un desayuno a la carta en el hotel.\n" +
                "\n" +
                "El aeropuerto más cercano es el aeropuerto Alfonso López Pumarejo, situado a 55 km del Villasthefany.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.es/maps/place/Centro+Recreacional+VillaSthefany/@10.4116923,-73.5924195,17z/data=!4m18!1m9!3m8!1s0x8ef54e58c12be273:0x5b8ffcedcabeceee!2sCentro+Recreacional+VillaSthefany!5m2!4m1!1i2!8m2!3d10.411687!4d-73.5902308!3m7!1s0x8ef54e58c12be273:0x5b8ffcedcabeceee!5m2!4m1!1i2!8m2!3d10.411687!4d-73.5902308");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pueblobellovillastefany1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pueblobellovillastefany2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pueblobellovillastefany3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }





    ///////////PARQUES/////
    public void GuardarProvincia(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,8);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"PARQUES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"LA PROVINCIA");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Un parque turístico, cultural y ecológico, con una extensión de 3.3 hectáreas, ubicado a pocos metros del río Guatapurí, de la glorieta Los Juglares y del Parque de la Leyenda Vallenata\n"+
                "\n" +
                "Este escenario cuenta con figuras en piedras, alusivas a animales propios de la región; juegos didácticos, senderos en losetas, jardines secos y verdes, sistema de riego, ciclo-ruta, locales comerciales, bancas antivandálicas, canecas de basura, zonas de parqueo, espejo de agua, gimnasio biosaludable, cancha sintética y máquinas de diversión para personas con movilidad reducida. En el Parque de La Provincia también funciona una fuente interactiva y una seca, contemplativa con la frase:\n"+
                "Valledupar la más linda.");

        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/Parque+la+provincia/@10.5005432,-73.267721,19z/data=!4m8!1m2!2m1!1sla+provincia+valledupar!3m4!1s0x8e8ab9a63714a8a7:0xadefee552df60af!8m2!3d10.5004197!4d-73.2674548?hl=es");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.valledupar1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.provincia2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.provincia3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarCristoRey(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,9);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"PARQUES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"CRISTO REY");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"El parque Cristo Rey, ubicado en el barrio Simón Bolívar, Como atractivo, esta obra cuenta con una fuente seca con un piso en arte con trencadi, homenaje a la paz de Valledupar y Colombia, tiene 72 chorros de agua e igual números de luces interactivas.\n"+
                "\n" +
                "Es una fuente que puede estar sincronizada con música, los chorros de agua y las luces pueden estar en coordinación a la música. Hay parque infantil, estación biosaludable, el quiosco incluye cafetería con bodega y baños públicos, las dos cosas en cumplimiento al Código Nacional de Policía, bahía de parqueadero para visitantes y motocicletas, se respetó el atrio de la iglesia por eso existe la posibilidad de que los vehículos que lleguen para actos fúnebres o matrimonios puedan ingresar hasta la puerta de la iglesia.");

        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/Parque+Cristo+Rey/@10.4583767,-73.2468033,15z/data=!4m8!1m2!2m1!1sparques+en+valledupar!3m4!1s0x0:0x49cd17d79a6b9e40!8m2!3d10.4583771!4d-73.2468045");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.cristorey1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.cristorey2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.cristorey3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }

    public void GuardarParqueAlgarrobillos(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,10);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"PARQUES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"PARQUE DE LOS ALGARROBILLOS");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Un lugar totalmente recomendado para hacer cualquier tipo de actividad, sea deporte o simplemente ir a pasear con tu familia o mascota. \n"+
                "\n" +
                "Si quieres tranquilidad hay mucho árboles lo cual lo hace un lugar muy fresco y agradable para charlas.");

        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/Parque+De+Los+Algarrobillos./@10.457023,-73.2384233,17z/data=!4m12!1m6!3m5!1s0x8e8ab998d8e39079:0x15c6b92bc5302a5!2sParque+De+Los+Algarrobillos.!8m2!3d10.4570177!4d-73.2362346!3m4!1s0x8e8ab998d8e39079:0x15c6b92bc5302a5!8m2!3d10.4570177!4d-73.2362346?hl=es");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.algarrobillo1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.algarrobillo2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.algarrobillo3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarParqueElViajero(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,11);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"PARQUES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"PARQUE EL VIAJERO");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"El parque El Viajero, un centro de recreación público que por más de 30 años ha sido testigo de turistas, habitantes y cuanto ciudadano ha tenido la oportunidad de estar ahí.\n"+
                "\n" +
                "Ubicado en la carrera 9 con calle 12 el viajero es uno de los monumentos con mayor significado histórico de la ciudad.En los últimos años, el Parque El Viajero ha perdido una parte de su atractivo debido a la falta de mantenimiento de sus instalaciones, pero sigue siendo un punto destacado para los eventos culturales.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/Parque+El+Viajero/@10.4803523,-73.2536431,17z/data=!4m12!1m6!3m5!1s0x8e8ab9b5134c37cd:0x381bfc59b2e1745c!2sParque+El+Viajero!8m2!3d10.480347!4d-73.2514544!3m4!1s0x8e8ab9b5134c37cd:0x381bfc59b2e1745c!8m2!3d10.480347!4d-73.2514544");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.viajero1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.viajero2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.viajero3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }


    public void GuardarParqueCentral(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,12);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"PARQUES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Parque Central");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Excelente lugar para pasar un rato agradable");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.es/maps/place/Parque+Central/@10.4263557,-73.5843327,14.5z/data=!4m8!1m2!2m1!1sparques+en+Pueblo+Bello,+Cesar!3m4!1s0x0:0xe8c13fd06cb0d64!8m2!3d10.4163829!4d-73.5851651");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pueblobelloparquecentral);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pueblobelloparquecentral2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pueblobelloparquecentral3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }

    public void GuardarMirador(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,13);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"PARQUES");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Mirador");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Muy lindo Mirador en la entrada de Pueblo Bello,  lindas vista de las montañas al aledañas en las tardes,  buena brisa y clima para pasar un buen rato.\n");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.es/maps/place/Mirador/@10.4272659,-73.5870031,15.5z/data=!4m8!1m2!2m1!1sparques+en+Pueblo+Bello,+Cesar!3m4!1s0x0:0x7cae57dd0d485077!8m2!3d10.423574!4d-73.5777703");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.pueblobelloparqueelmirador1);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.pueblobelloparqueelmirador2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.pueblobelloparqueelmirador1);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }




    //---------CARGAR ACTIVIDADES-----

    public void GuardarActividades(){
        GuardarCaminata();
        GuardarCityTour();
        GuardarCalleGrande();
        GuardarElCuartico();
        GuardarJardinBotanico();
        GuardarNabusimake();
    }
    public void GuardarCaminata(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD,1);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"CAMINATAS / SENDERISMO");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Este plan es perfecto para todo aquel que le guste ejercitar su cuerpo, disfrutando de la naturaleza y el clima fresco de la mañana. Si visitas Valledupar puedes realizar caminatas por el parque lineal del rio Guatapurí y conectar con los senderos del cerro del mirador del Santo Ecce Homo. \n"+
                "\n" +
                "Recomedaciones: Ropa y calzado adecuados. Si realizas estas actividades en Valledupar ten en cuenta que el clima es cálido y seco, la hidratación es fundamental.");
        values.put(CultuCesarContract.DETALLE_ACTIVIDAD ,"https://www.google.com/maps/place/Capilla+Santo+EcceHomo/@10.5093017,-73.2632645,17z/data=!4m12!1m6!3m5!1s0x8e8ab816d1d944b3:0xbc64a367cb19b61d!2sCapilla+Santo+EcceHomo!8m2!3d10.5093017!4d-73.2610758!3m4!1s0x8e8ab816d1d944b3:0xbc64a367cb19b61d!8m2!3d10.5093017!4d-73.2610758");
        values.put(CultuCesarContract.FOTO_ACTIVIDAD ,R.drawable.santoeccehomo1);
        values.put(CultuCesarContract.IMG_DETALLE_ACTIVIDAD ,R.drawable.santoeccehomo2);

        db.insert(CultuCesarContract.TABLA_ACTIVIDAD ,null,values);
    }

    public void GuardarCityTour(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD,2);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"CITY TOUR - VALLEDUPAR");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Valledupar es reconocida por sus monumentos y glorietas, en homenaje a su historia y cultura. En la ciudad hay más de 20 monumentos y glorietas, la mayoría dedicados a la música y cultura tradicional vallenata.\n"+
                "\n" +
                "El recorrido lo puede hacer en vehículo propio o para más comodidad, en la ciudad se ofrecen planes y recorridos turísticos que llegan a incluir (dependiendo de la tarifa) transporte, hidratación, ambiente musical, tarjeta de asistencia médica y guía especializado, hasta almuerzo típico, souvenir, que hará más atractivo el tour por la ciudad.");
        values.put(CultuCesarContract.DETALLE_ACTIVIDAD ,"https://www.google.com/maps/place/Paseo+Vallenato+Tour/@10.4714877,-73.2582133,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab99328149105:0x9346ce9451262adf!8m2!3d10.4714877!4d-73.2560246");
        values.put(CultuCesarContract.FOTO_ACTIVIDAD ,R.drawable.citytour1);
        values.put(CultuCesarContract.IMG_DETALLE_ACTIVIDAD ,R.drawable.citytour2);

        db.insert(CultuCesarContract.TABLA_ACTIVIDAD ,null,values);
    }

    public void GuardarCalleGrande(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD,3);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"CENTRO ARTESANAL CALLE GRANDE");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Ubicado en la calle 16 # 7 – 18, ha una cuadra de la Plaza Alfonso López del centro histórico de la ciudad. Allí puede encontrar mochilas arhuacas, artesanías, sombreros, pinturas, entre otros souvenirs para llevar a la familia y amigos y recordar su experiencia en Valledupar.");

        values.put(CultuCesarContract.DETALLE_ACTIVIDAD ,"https://www.google.com/maps/place/Centro+Artesanal+Calle+Grande/@10.4761931,-73.2479444,17z/data=!4m8!1m2!2m1!1scalle+grande!3m4!1s0x0:0x87bb632f5f429c49!8m2!3d10.476188!4d-73.2457557");
        values.put(CultuCesarContract.FOTO_ACTIVIDAD ,R.drawable.callegrande1);
        values.put(CultuCesarContract.IMG_DETALLE_ACTIVIDAD ,R.drawable.callegrande2);

        db.insert(CultuCesarContract.TABLA_ACTIVIDAD ,null,values);
    }

    public void GuardarElCuartico(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD ,4);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"CASA DE BAHAREQUE EL CUARTICO");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Casa ubicada en el barrio San Joaquín de Valledupar, donde puede recrear el modo de vida de una familia vallenata del siglo XX. Allí se guarda la historia del Valledupar de antaño. Es un lugar donde se conserva la esencia, las costumbres y tradiciones del viejo Valledupar. Construido con madera, techo de teja y piso de barro; Centro de Memoria para las futuras generaciones.\n");

        values.put(CultuCesarContract.DETALLE_ACTIVIDAD ,"https://www.google.com/maps/place/El+Cuartico/@10.4707928,-73.2638964,21z/data=!4m8!1m2!2m1!1sCASA+DE+BAHAREQUE+-+EL+CUARTICO!3m4!1s0x0:0xd78fea49c8bd665b!8m2!3d10.4708066!4d-73.2639023");
        values.put(CultuCesarContract.FOTO_ACTIVIDAD ,R.drawable.cuartico1);
        values.put(CultuCesarContract.IMG_DETALLE_ACTIVIDAD ,R.drawable.cuartico2);

        db.insert(CultuCesarContract.TABLA_ACTIVIDAD,null,values);
    }


    public void GuardarJardinBotanico(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD ,5);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"Jardin botanico Y Escuela De Saberes Busintana");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Quienes llegan a Pueblo Bello, dos horas al occidente de Valledupar, encuentran tranquilidad en el jardín botánico Buzintana, un sitio donde la comunidad Arhuaca ha vertido sus conocimientos sobre la flora medicinal, ornamental y alimenticia de la región. Aquí se aprende sobre ecología y se puede conocer cómo se produce miel de abejas pura, sin elementos químicos.");
        values.put(CultuCesarContract.DETALLE_ACTIVIDAD ,"https://www.google.com/maps/place/Jardin+botanico+Y+Escuela+De+Saberes+Busintana/@10.4209984,-73.5893236,17z/data=!3m1!4b1!4m5!3m4!1s0x8ef54ef3b4447b3f:0xcca6e101cbc85e2c!8m2!3d10.4209984!4d-73.5871349");
        values.put(CultuCesarContract.FOTO_ACTIVIDAD ,R.drawable.pueblobellobotanico1);
        values.put(CultuCesarContract.IMG_DETALLE_ACTIVIDAD ,R.drawable.pueblobellobotanico2);

        db.insert(CultuCesarContract.TABLA_ACTIVIDAD,null,values);
    }

    public void GuardarNabusimake(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD ,6);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"VISITAR NABUSIMAKE");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Nabusímake  se encuentra escondido al interior de la Sierra Nevada de Santa Marta y es la capital espiritual del grupo indígena (Arahuacos). En su lengua, Nabusímake significa Tierra donde el sol nace.\n" +
                "\n" +
                "Llegar a Nabusímake es una verdadera  aventura,no se puede acceder  sino en burro o a pie; el camino es espectacular y está decorado con flores  como las  cayennes,  las hydrangeas  y las dalias.\n" +
                "\n" +
                "Podrá bañarse en el agua fría y cristalina del rio San Sebastián; cuando llegue a Nabusímake sentirá la espiritualidad del pueblo, que está protegido por las montañas, podrá renovar su cuerpo  y su alma con energías positivas en aquel paraíso.");
        values.put(CultuCesarContract.DETALLE_ACTIVIDAD ,"https://www.google.com/maps/place/Nabusimake,+Pueblo+Bello+-+Cesar/@10.56026,-73.59398,17z/data=!4m12!1m6!3m5!1s0x8ef5377b0a191cb9:0x209b73a2d668812!2sNabusimake,+Pueblo+Bello+-+Cesar!8m2!3d10.5602888!4d-73.5944202!3m4!1s0x8ef5377b0a191cb9:0x209b73a2d668812!8m2!3d10.5602888!4d-73.5944202");
        values.put(CultuCesarContract.FOTO_ACTIVIDAD ,R.drawable.pueblobellonabusimake);
        values.put(CultuCesarContract.IMG_DETALLE_ACTIVIDAD ,R.drawable.pueblobellonabusimake2);

        db.insert(CultuCesarContract.TABLA_ACTIVIDAD,null,values);
    }







    //---------Guardar detalle municipios------------
    public void GuardarDetallesMunicipio(){
        //--MUSICA--
        GuardarVallenato();
        GuardarVallenatoP();
        //--HISTORIAS--
        GuardarHisotiraFestivalVallenato();
        GuardarHisotiraPiloneras();
        //--LEYENDAS---
        GuardarLeyendaFrancisco();
        GuardarLeyendaSirenaHurtado();
        //--JUGLARES---
        GuardarRafaelEscalona();
        GuardarTobiasEnrique();
        GuardarCalixto();
        GuardarLorenzo();
    }


    public void GuardarVallenato(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,1);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"MUSICA AUTOCTONA");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"El VALLENATO");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Este género músical nace en Valledupar, donde yacían tribus indígenas, incluidas las Chimilas y Tupes, gobernadas por un poderoso jefe conocido como el Cacique Upar. Ahí es donde la ciudad recibe su nombre (Valle de Upar) y vallenato, a su vez, significa “nacido en el valle”.\n" +
                "\n" +
                "Se dice que los agricultores de la región heredaron las tradiciones de juglares españoles y africanos, cantando y tocando sus instrumentos mientras viajaban de ciudad en ciudad con sus vacas, compartiendo noticias y mensajes. Finalmente, los instrumentos africanos e indígenas, como las flautas de gaita , la guacharaca y los tambores, se unieron al acordeón europeo y así es como nace el vallenato..");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://www.colombia.co/cultura-colombiana/musica/valledupar-la-cuna-del-vallenato/");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.festivalvallenato1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.vallenato1);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }



    public void GuardarHisotiraFestivalVallenato(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,2);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"HISTORIA");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"HISTORIA DEL FESTIVAL VALLLENATO");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"El Festival de la Leyenda Vallenata en toda su historia ha ido evolucionando al compás del desarrollo social y económico de Valledupar. Desde sus inicios hasta 1986 este evento era organizado por la oficina de Turismo del Departamento de El Cesar. Luego se creó la Fundación Festival de la Leyenda Vallenata, la cual funciona desde 1987 en las instalaciones de la Tarima Francisco el Hombre de la Plaza Alfonso López. Ese mismo año el Festival sirvió de marco para inaugurar la televisión regional, con el canal costeño Telecaribe, que hasta 1998 estuvo encargado de transmitir en vivo y en directo el desarrollo del evento.\n");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://festivalvallenato.com/festival-vallenato/que-es/");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.festivalvallenato2);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.festival1);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }

    public void GuardarHisotiraPiloneras(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,3);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"HISTORIA");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"HISTORIA DE LAS PILONERAS");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"La danza del Pilón es el desfile que desde 1994 da inicio oficial al Festival de la Leyenda Vallenata, aunque su origen se remonta a muchos años atrás.\n" +
                "El popular desfile de Las Piloneras es el acto cultural más importante del folclor Vallenato que reúne a todas las generaciones en un solo sentir.\n" +
                "\n" +
                "El origen de la danza de la piloneras está inspirado en las tareas domésticas tradicionales de las mujeres que hacían vida en el Cesar, La Guajira, el Magdalena y otras zonas de la costa Caribe.El golpeteo del maíz con el mazo del pilón produce un sonido ritmico que con el paso de las generaciones dio vida al baile las piloneras.\n" +
                "\n" +
                "La Danza del Pilón estuvo a punto de desaparecer, de no ser por Cecilia ‘La Polla’ Monsalvo, quien vio en el folclor Vallenato la oportunidad de seguir con ‘la parranda’ que engalanaba ya un mermado carnaval.Aquellas primeras coreografías que nacieron en los barrios Cerezo, Cañaguate, La Garita, Centro y Altagracia de Valledupar vieron en el evento más reconocido del vallenato un lugar para seguir cautivando a propios y extraños.\n" +
                "\n" +
                "Consuelo Araujo Noguera y 30 amigas protagonizaron el primer desfile de piloneras en 1981 y el desfile creció con los años hasta que la Fundación de la Leyenda Vallenata lo adoptó en 1994 como un concurso oficial en la programación del Festival, siendo La Cacica su principal gestora.\n");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://primeralinea.com.co/danza-el-pilon-todo-sobre-el-baile-mas/");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.pilonera1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.pilonera);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }

    public void GuardarLeyendaFrancisco(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,4);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"LEYENDA");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"LEYENDA FRANCISCO EL HOMBRE");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Narra la leyenda que una noche al regresar Francisco después de una parranda de varios días y al ir hacia su pueblo, para distraerse en la soledad de la noche, abrió el acordeón y, sobre su burro, como era usual en aquella época, empezó a interpretar sus melodías; de pronto, al terminar una pieza, surgió de inmediato el repertorio de otro acordeonero, que desafiante trataba de superarlo; de inmediato Francisco marchó hacia él hasta tenerlo a la vista; su competidor, para sorpresa, era Satanás, quien al instante se sentó sobre las raíces de un árbol, abrió su acordeón, y con las notas que le brotaban hizo apagar la luna y todas las estrellas...");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://festivalvallenato.com/mito-leyenda/");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.franciscoelhombre1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.franciscoelhombre2);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }

    public void GuardarLeyendaSirenaHurtado(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,5);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"LEYENDA");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"LEYENDA SIRENA DE HURTADO");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Cuentan los abuelos que Rosario Arciniegas, era una niña muy linda y caprichosa, nacida en el barrio «Cañaguate» de Valledupar. Acostumbrada a hacer siempre su voluntad, no hizo caso cuando sus padres, fieles a la tradición, le prohibieron que fuera a bañarse a las profundas aguas del pozo de Hurtado en el río Guatapurí, por ser un Jueves Santo, día consagrado a rememorar la Pasión de Nuestro Señor Jesucristo. Orgullosa y resuelta, Rosario se marchó a escondidas y al llegar al pozo, soltó sus largos cabellos, se quitó la ropa y se lanzó al agua desde las más altas rocas. Eran las dos de la tarde y, no obstante, el cielo se oscureció y cuando Rosario trató de salir de las aguas no pudo...");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://festivalvallenato.com/mito-leyenda/");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.sirena1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.sirena2);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }

    public void GuardarRafaelEscalona(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,6);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"JUGLARES");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"RAFAEL ESCALONA");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Rafael Calixto Escalona Martínez conocido como \"El maestro Escalona\", fue un compositor colombiano, considerado uno de los más grandes compositores de la música vallenata.\n" +
                "\n" +
                "Sus canciones fueron grabadas por destacados artistas de la música vallenata como  Diomedes Díaz, El Binomio de Oro Rafael Orozco e Israel Romero, Jorge Oñate, Poncho y Emilianito Zuleta, Iván Villazón, y aunque sus composiciones se hicieron más populares en la voz de Carlos Vives, quien primero protagonizó la serie Escalona, basada en la vida del propio Rafael, y luego las grabará en varios álbumes musicales, impulsando la fama de ambos en Colombia y a nivel internacional.");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://es.wikipedia.org/wiki/Rafael_Escalona");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.escalona1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.escalona2);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }


    public void GuardarTobiasEnrique(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,7);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"JUGLARES");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"TOBIAS ENRIQUE PUMAREJO");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Tobías Enrique Pumarejo Gutiérrez. Compositor e intérprete colombiano de música vallenata. Sin lugar a dudas; es el precursor, el creador del Vallenato romántico- costumbrista, con su estilo poético musical logró concatenar elementos básicos que han servido de referencia para la consagración del Canto Vallenato.\n" +
                "\n" +
                "Fue parte del jurado del primer Festival Vallenato, al lado de Rafael Escalona y Gustavo Gutiérrez. Fue también el primer compositor vallenato desligado de los instrumentos, el primer miembro de la sociedad de Valledupar que cantó y compuso este ritmo y a quien le cupo el honor de abrirle las puertas en el Club de Valledupar a este nuevo aire musical.");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://www.ecured.cu/Tob%C3%ADas_Enrique_Pumarejo_Guti%C3%A9rrez");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.tobiasenriquepumarejo1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.tobiasenriquepumarejo2);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }

    public void GuardarCalixto(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,8);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"JUGLARES");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"CALIXTO OCHOA");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Calixto de Jesús Ochoa Ocampo (Valencia de Jesús, 14 de agosto de 1934 - Sincelejo, 18 de noviembre de 2015) fue un cantante, compositor y acordeonero colombiano de música vallenata.\n" +
                "\n" +
                "A los 19 años se marchó del hogar para recorrer las localidades vecinas haciendo lo que más le agradaba: cantar. De pueblo en pueblo, llegó a Sincelejo en 1956, donde realizó su primera grabación titulada «El lirio rojo» para el desaparecido sello Eco. Esta canción, que tuvo muy buena acogida en toda la Costa, le abrió las puertas de las grandes casas disqueras del país. Discos Fuentes fue la primera en llamarlo y fue artista exclusivo de ésta por más de una década. Tras trece años de trabajar para Discos Fuentes, probó con otras empresas, pero siempre volvió a la compañía con la que tantos triunfos obtuvo.");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://es.wikipedia.org/wiki/Calixto_Ochoa");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.calistoochoa1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.calistoochoa2);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }


    public void GuardarLorenzo(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,9);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"JUGLARES");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"LORENZO MORALES");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Lorenzo Miguel Morales Herrera (n. Guacoche, Magdalena Grande el 19 de junio de 1914 – f. Valledupar, Cesar el 26 de agosto de 2011), conocido como Lorenzo Morales o por su apodo de “Moralito”, fue un músico colombiano, acordeonero y cantautor de música vallenata.\u200B\n" +
                "\n" +
                "Es considerado una de las grandes leyendas vallenatas. El Festival de la Leyenda Vallenata de Valledupar lo homenajeó como «rey vitalicio».\u200B\n" +
                "\n" +
                "Morales fue con Emiliano Zuleta Baquero el protagonista del más famoso duelo musical de Colombia, el cual fue inmortalizado en la canción “La gota fría” e internacionalizado por el cantante colombiano Carlos Vives y el cantante español Julio Iglesias.\n" +
                "\n" +
                "Como respuesta a Zuleta y su canción La gota fría, \"Moralito\" compuso el tema La carta.");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://es.wikipedia.org/wiki/Lorenzo_Morales");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.lorenzomorales1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.lorenzomorales2);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }



    public void GuardarVallenatoP(){
        SQLiteDatabase db = GuardarDetalleM.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_DETALLE_M ,1);
        values.put(CultuCesarContract.TIPO_DETALLE_M,"MUSICA AUTOCTONA");
        values.put(CultuCesarContract.MUNICIPIO_DETALLE_M ,"Pueblo bello");
        values.put(CultuCesarContract.NOMBRE_DETALLE_M ,"El VALLENATO");
        values.put(CultuCesarContract.INFO_DETALLE_M ,"Este género músical nace en Valledupar, donde yacían tribus indígenas, incluidas las Chimilas y Tupes, gobernadas por un poderoso jefe conocido como el Cacique Upar. Ahí es donde la ciudad recibe su nombre (Valle de Upar) y vallenato, a su vez, significa “nacido en el valle”.\n" +
                "\n" +
                "Se dice que los agricultores de la región heredaron las tradiciones de juglares españoles y africanos, cantando y tocando sus instrumentos mientras viajaban de ciudad en ciudad con sus vacas, compartiendo noticias y mensajes. Finalmente, los instrumentos africanos e indígenas, como las flautas de gaita , la guacharaca y los tambores, se unieron al acordeón europeo y así es como nace el vallenato..");

        values.put(CultuCesarContract.DESCRIPCION_DETALLE_M ,"https://www.colombia.co/cultura-colombiana/musica/valledupar-la-cuna-del-vallenato/");
        values.put(CultuCesarContract.FOTO_DETALLE_M ,R.drawable.festivalvallenato1);
        values.put(CultuCesarContract.IMG_DETALLE_M ,R.drawable.vallenato1);

        db.insert(CultuCesarContract.TABLA_DETALLE_M,null,values);
    }
















}
