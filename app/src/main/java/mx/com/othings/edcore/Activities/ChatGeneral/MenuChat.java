package mx.com.othings.edcore.Activities.ChatGeneral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.com.othings.edcore.R;

public class MenuChat extends AppCompatActivity {

    private Button btnVerUsuaruios;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_menu_chat);

        btnVerUsuaruios = findViewById(R.id.btnVerEstudiantes);

        btnVerUsuaruios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuChat.this, VerEstudiantes.class);
                startActivity(intent);
            }
        });
    }
}
