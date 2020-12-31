package mx.com.othings.edcore.Activities.BlocDeNotas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import mx.com.othings.edcore.R;

/**
 * Pantalla de Ajustes
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Se carga la interfaz del activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Ajustes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.fragment_settings) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getFragmentManager().beginTransaction().add(R.id.fragment_settings, new SettingsFragment()).commit();
            SplashActivity.nameActivity = "SettingsActivity";
        }
    }
}
