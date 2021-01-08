package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.othings.edcore.R;

public class ActivityMonitoreoDeGrupos extends AppCompatActivity {

    private Button btnAceptarMonitoreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo_de_grupos);

        btnAceptarMonitoreo = (Button)findViewById(R.id.btnAceptarMonitoreo);
        btnAceptarMonitoreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}