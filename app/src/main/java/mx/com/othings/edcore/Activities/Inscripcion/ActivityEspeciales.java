package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.othings.edcore.R;

public class ActivityEspeciales extends AppCompatActivity {

    private Button btnAceptarEspeciales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especiales);
        btnAceptarEspeciales = (Button)findViewById(R.id.btnAceotarEspeciales);
        btnAceptarEspeciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}