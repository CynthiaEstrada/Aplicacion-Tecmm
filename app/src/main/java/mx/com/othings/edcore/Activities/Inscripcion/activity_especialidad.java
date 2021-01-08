package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.othings.edcore.R;

public class activity_especialidad extends AppCompatActivity {

    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad);

        btnAceptar = (Button)findViewById(R.id.btnGuardarEspecialidad);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}