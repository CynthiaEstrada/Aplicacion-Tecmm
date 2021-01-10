package mx.com.othings.edcore.Activities.ChatGeneral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.util.PriorityQueue;

import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.R;

public class ActivityVerGrupos extends AppCompatActivity {

    private CardView General, Transporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_grupos);

        final String texto = getIntent().getExtras().getString("Datos");

        System.out.println("EN menu Grupos: " + texto);


        Transporte = findViewById(R.id.cardTransporte);
        General = findViewById(R.id.cardCampus);

        General.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Chat Grupal", "Campus");
                bundle.putString("Datos", texto);
                Intent intent = new Intent(ActivityVerGrupos.this, ChatGrupal.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Transporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Chat Grupal", "Transporte");
                bundle.putString("Datos", texto);
                Intent intent = new Intent(ActivityVerGrupos.this, ChatGrupal.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}