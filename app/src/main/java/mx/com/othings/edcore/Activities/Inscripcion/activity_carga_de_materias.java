package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.othings.edcore.Fragments.main_left_menu.InscripcionFragment;
import mx.com.othings.edcore.R;

public class activity_carga_de_materias extends AppCompatActivity {

    private Button btnGuradarMAterias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_de_materias);

        btnGuradarMAterias = (Button) findViewById(R.id.btnGuardarHorario);
        btnGuradarMAterias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}