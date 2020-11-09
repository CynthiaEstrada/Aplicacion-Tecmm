
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

import com.github.pavlospt.roundedletterview.RoundedLetterView;

import mx.com.othings.edcore.Activities.ChatGeneral.ChatGeneral;
import mx.com.othings.edcore.Fragments.main_left_menu.CalificationsFragment;
import mx.com.othings.edcore.Fragments.PanelControlFragment;
import mx.com.othings.edcore.Fragments.main_left_menu.StudentInformationFragment;
import mx.com.othings.edcore.Fragments.main_left_menu.StudentScheduleFragment;
import mx.com.othings.edcore.R;

public class ControlPanel extends TemplateActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

       /* service.Online().getStudentInformation(new OnlineResourceListener() {
            @Override
            public void onResponse(Object response) {

                Student student = (Student) response;
                String full_name = Utilities.capitalizeWords((student.getName()+" "+student.getFirst_name()+" "+ student.getLast_name()).toLowerCase());

                service.sdb().getStudentConfiguration().setStudent(StudentConfigurations.STUDENT_INFORMATION,student);
                user_letter_view.setTitleText(String.valueOf(student.getName().toCharArray()[0]));
                username.setText(full_name);
                email.setText(student.getEmail().toLowerCase());

            }

            @Override
            public void onError(int error_code, String error_message) {
                Toasty.error(ControlPanel.this, error_message , Toast.LENGTH_LONG, true).show();
            }
        });*/


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

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new PanelControlFragment()).commit();
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportActionBar().setTitle(navigationView.getMenu().getItem(0).getTitle());

    }

    private void changeFragment(Fragment fragment, String title){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment).commit();
        getSupportActionBar().setTitle(title);

    }


    /*private void charge_user_photo(String image_base_64 ){

        byte [] image_decoded = Utilities.decodeStringToBase64(image_base_64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_decoded,0,image_decoded.length);

    }*/

}
