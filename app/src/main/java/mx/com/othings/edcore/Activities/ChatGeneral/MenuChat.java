package mx.com.othings.edcore.Activities.ChatGeneral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;

import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.R;

public class MenuChat extends AppCompatActivity {

    private CardView btnVerUsuaruios, cardGrupos;

    Bundle bundle = new Bundle();
    String texto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_menu_chat);

        texto = getIntent().getExtras().getString("a");

        btnVerUsuaruios = findViewById(R.id.cardEstudiantesChat);
        cardGrupos = findViewById(R.id.cardGruposChat);

        btnVerUsuaruios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChat.this, VerEstudiantes.class);
                startActivity(intent);
            }
        });

        cardGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("Datos", texto);

                Intent intent = new Intent(MenuChat.this, ActivityVerGrupos.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }
}
