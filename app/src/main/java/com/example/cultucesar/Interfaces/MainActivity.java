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
import com.example.cultucesar.Entidades.ActividadesVo;
import com.example.cultucesar.Entidades.DestinosVo;
import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.Fragments.Actividades.ActiviadadesFragment;
import com.example.cultucesar.Fragments.Actividades.DetalleActividadesFragment;
import com.example.cultucesar.Fragments.DetalleDestinoFragment;
import com.example.cultucesar.Fragments.EventosCulturales.DetalleEventoFragment;
import com.example.cultucesar.Fragments.EventosCulturales.EventosFragment;
import com.example.cultucesar.Fragments.MainFragment;
import com.example.cultucesar.Fragments.DestinosFragment;
import com.example.cultucesar.Fragments.SitioInteres.DetalleSitioInteresFragment;
import com.example.cultucesar.Fragments.SitioInteres.SitioInteresFragment;
import com.example.cultucesar.Fragments.SitioRecreativo.DetalleSitioRecreativoFragment;
import com.example.cultucesar.Fragments.SitioRecreativo.SitioRecreativoFragment;
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
    DetalleSitioRecreativoFragment detalleSitioRecreativoFragment;
    DetalleActividadesFragment detalleActividadesFragment;




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


        GuardarEvento = new ConexionSQLiteEventoHelper(this,"eventos",null,1);
        CargarEventos();

        GuardarSitioInteres = new ConexionSQLiteSitiosInteresHelper(this,"sitiosInteres",null,1);
        CargarSitioInteres();

        GuardarSitioRecreativo = new ConexionSQLiteSitiosRecreativoHelper(this,"sitiosRecreativo",null,1);
        GuardarSitiosRecreativos();

        GuardarActividades = new ConexionSQLiteActividadHelper(this,"actividades",null,1);
        GuardarActividades();


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



    //------- Cargar Sitios de interes ----------

    public void CargarSitioInteres(){
        //HOTELES
        GuardarHotelBoutique();
        GuardarHotelSicarare();
        GuardarHotelSonesta();
        GuardarHotelReyReyes();
        //RESTAURANTES
        GuardarRestauranteCH();
        GuardarRestauranteVaradero();
        GuardarRestauranteMarianamen();
        GuardarRestauranteMontacarga();
        //BARES
        GuardarTierraCantores();
        GuardarPatioBar();
        GuardarTlonBar();
        GuardarPalenke();
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,1);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,1);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,1);
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



    /////////////////// RESTAURANTES ////////////////////////////////////////////
    public void GuardarRestauranteCH(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,2);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,2);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,2);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,2);
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




    /////////////////// BARES ////////////////////////////////////////////
    public void GuardarTierraCantores(){
        SQLiteDatabase db = GuardarSitioInteres.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,3);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,3);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,3);
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
        values.put(CultuCesarContract.CODIGO_SITIO_INTERES,3);
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


    //----------------- CARGAR SITIOS RECREATIVOS --------------------------
    public void GuardarSitiosRecreativos(){
        ////BALNEAREOS////
        GuardarRioGuatapuri();
        ////CENTRO RECREACIONALES/////
        GuardarPedregosa();
        GuardarEscuelaAmbiental();
        ///PARQUES/////
        GuardarProvincia();
        GuardarCristoRey();
        GuardarParqueAlgarrobillos();
        GuardarParqueElViajero();

    }

    /////////////BALNEAREOS/////////////
    public void GuardarRioGuatapuri(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,1);
        values.put(CultuCesarContract.TIPO_SITIO_RECREATIVO,"BALNEAREOS");
        values.put(CultuCesarContract.MUNICIPIO_SITIO_RECREATIVO,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_SITIO_RECREATIVO,"Río Guatapurí");
        values.put(CultuCesarContract.INFO_SITIO_RECREATIVO,"Nace en la laguna Curigua, de la Sierra Nevada de Santa Marta, y en un vertiginoso descenso de 80 kilómetros entrega sus aguas a Valledupar. Su balneario Hurtado, de cristalinas aguas, genera un ambiente refrescante donde los turistas pueden bañarse; sus contorneadas rocas enmarcan paisajes que han contribuido a crear misteriosas leyendas y son fuente de inspiración de melodías del folclor vallenato.");
        values.put(CultuCesarContract.DETALLE_SITIO_RECREATIVO,"https://www.google.com/maps/place/R%C3%ADo+Guatapuri/@10.501246,-73.2710014,20z/data=!4m5!3m4!1s0x8e8ab9658189339b:0x15d36ea4274d074d!8m2!3d10.5607458!4d-73.3560067?hl=es-ES");
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO1,R.drawable.valledupar);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO2,R.drawable.rioguatapuri2);
        values.put(CultuCesarContract.IMG_DETALLE_RECREATIVO3,R.drawable.riogutapuri3);

        db.insert(CultuCesarContract.TABLA_SITIO_RECREATIVO,null,values);
    }
    ///////////////////CENTRO RECREACIONALES/////////////////////
    public void GuardarPedregosa(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,2);
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
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,3);
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

    ///////////PARQUES/////
    public void GuardarProvincia(){
        SQLiteDatabase db = GuardarSitioRecreativo.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,4);
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
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,4);
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
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,4);
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
        values.put(CultuCesarContract.CODIGO_SITIO_RECREATIVO,4);
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


    //---------CARGAR ACTIVIDADES-----

    public void GuardarActividades(){
        GuardarCaminata();
        GuardarCityTour();
        GuardarCalleGrande();
        GuardarElCuartico();
    }
    public void GuardarCaminata(){
        SQLiteDatabase db = GuardarActividades.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(CultuCesarContract.CODIGO_ACTIVIDAD,1);
        values.put(CultuCesarContract.MUNICIPIO_ACTIVIDAD ,"Valledupar");
        values.put(CultuCesarContract.NOMBRE_ACTIVIDAD ,"CAMINATAS / SENDERISMO");
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Este plan es perfecto para todo aquel que disfrute ejercitar su cuerpo, disfrutando de la naturaleza y el clima fresco de la mañana. Si visitas Valledupar puedes realizar caminatas por el parque lineal del rio Guatapurí y conectar con los senderos del cerro del mirador del Santo Ecce Homo. \n"+
                "\n" +
                "Recomedaciones: Ropa y calzado adecuados. Si realizas estas actividades en Valledupar ten en cuenta que el clima es cálido y seco, la hidratación es fundamental. En Manaure ten en cuenta que la mayoría de los senderos son en altura; en el caso de Sabana Rubia llegarás hasta los 3.000 m.s.n.m por lo que debes llevar ropa para clima frio.");
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
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Valledupar es reconocido por sus monumentos y glorietas, en homenaje a su historia y cultura. En la ciudad hay más de 20 monumentos y glorietas, la mayoría dedicados a la música y cultura tradicional vallenata.\n"+
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
        values.put(CultuCesarContract.INFO_ACTIVIDAD ,"Ubicado en la calle 16 # 7 – 18, en una manzana de la Plaza Alfonso López del centro histórico de la ciudad. Allí puede encontrar mochilas arhuacas, artesanías, sombreros, pinturas, entre otros souvenirs para llevar a la familia y amigos y recordar su experiencia en Valledupar.");

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













}
