
package mx.com.othings.edcore.Activities;


import android.os.Bundle;

import 	com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

import es.dmoral.toasty.Toasty;
import mx.com.othings.edcore.Activities.BlocDeNotas.componentBd.ComponentNotes;
import mx.com.othings.edcore.Activities.BlocDeNotas.pojos.User;
import mx.com.othings.edcore.Fragments.main_left_menu.CalificationsFragment;
import mx.com.othings.edcore.Fragments.main_left_menu.InscripcionFragment;
import mx.com.othings.edcore.Fragments.main_left_menu.PanelControlFragment;
import mx.com.othings.edcore.Fragments.main_left_menu.StudentInformationFragment;
import mx.com.othings.edcore.Fragments.main_left_menu.StudentScheduleFragment;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Utilities;
import mx.com.othings.edcore.R;

public class ControlPanel extends TemplateActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    Bundle args = new Bundle();
    private static User user;                          //Creamos un POJO de apoyo
    private ComponentNotes componentNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        componentNotes = new ComponentNotes(this);
//        componentNotes.readUsers();

        Bundle bundle = getIntent().getExtras();
        String texto = bundle.getString("a");
        args.putString("a", texto);

        String texto2 = getIntent().getExtras().getString("b");
        args.putString("b", texto2);

        System.out.println("Desde control panel texto: " + texto);
        System.out.println("Desde control panel texto2: " + texto2);

        ArrayList<String> ma = new ArrayList<>();
        ma = getIntent().getStringArrayListExtra("materias");
        args.putStringArrayList("materias", ma);

        Gson gson = new Gson();
        Student student = gson.fromJson(texto, Student.class);

//        IniciarNotas(student);
        //userLogin();

        setContentView(R.layout.activity_control_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final RoundedLetterView user_letter_view = navigationView.getHeaderView(0).findViewById(R.id.user_letter);
        final TextView username = navigationView.getHeaderView(0).findViewById(R.id.username);
        final TextView email = navigationView.getHeaderView(0).findViewById(R.id.email);


        setDefaultFragment();

        String full_name = Utilities.capitalizeWords((student.getName()+" "+student.getFirst_name()+" "+ student.getLast_name()).toLowerCase());

        user_letter_view.setTitleText(String.valueOf(student.getName().toCharArray()[0]));
        username.setText(full_name);
        email.setText(student.getEmail().toLowerCase());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.control_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.panel_control) {
            changeFragment(new PanelControlFragment(),"Panel de control");
        }
        else if( id == R.id.inscripcion_control ){
            changeFragment(new InscripcionFragment(),"Inscripción");
        }
        else if( id == R.id.califications ){
            changeFragment(new CalificationsFragment(),"Calificaciones");
        }
        else if( id == R.id.schedule){
            changeFragment(new StudentScheduleFragment(),"Horario");
        }
        else if( id == R.id.user){
            changeFragment(new StudentInformationFragment(),"Mi cuenta");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setDefaultFragment(){

        Fragment fragment = new PanelControlFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportActionBar().setTitle(navigationView.getMenu().getItem(0).getTitle());

    }

    private void changeFragment(Fragment fragment, String title){
        fragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment).commit();
        getSupportActionBar().setTitle(title);

    }


    /*private void charge_user_photo(String image_base_64 ){

        byte [] image_decoded = Utilities.decodeStringToBase64(image_base_64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_decoded,0,image_decoded.length);

    }*/

    private void IniciarNotas (Student student1) {
        //Objeto que nos permite realizar las operaciones con la BDD
        User user = new User(student1.getStudent_id());

        if (componentNotes.insertUser(user) != 0) {
            System.out.println("Se guardo con exito");
        } else {
            Toasty.normal(getApplicationContext(), "Fallo al registrar el usuario",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void userLogin () {
        ArrayList<User> users = componentNotes.readUsers();
        if (users != null) {
            Iterator itr = users.iterator();

            while (itr.hasNext()) {
                user = (User) itr.next();
                System.out.println("UserLogin: " + user.getUserId());
            }
        }
    }

}
