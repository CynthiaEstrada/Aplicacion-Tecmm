package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.com.othings.edcore.Fragments.main_left_menu.InscripcionFragment;
import mx.com.othings.edcore.R;

public class activity_carga_de_materias extends AppCompatActivity {

    private Button btnGuradarMaterias;
    private ArrayList<String> Cadena = new ArrayList<String>();
    private CheckBox[] checkbox = new CheckBox[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_de_materias);

        btnGuradarMaterias = (Button) findViewById(R.id.btnGuardarHorario);

        CompoundButton.OnCheckedChangeListener checker = new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                System.out.println("compound: " + cb + " Boolean: " + b);
                if(b){
                    Cadena.add(cb.getText().toString());
                }else if(!b) {
                    String sameWord = cb.getText().toString();
                    for (int cnt = 0; cnt < Cadena.size(); cnt++) {
                        if (sameWord.equals(Cadena.get(cnt))) {
                            Cadena.remove(cnt);
                        }
                    }
                }
            }};

        int j;

        for(int i = 0; i < 3; i++) {
            j = i +1;
            int id1 = getResources().getIdentifier("checkBox" + j, "id", getPackageName());
            System.out.println("ID: " + id1);
            checkbox[i] = (CheckBox) findViewById(id1);
            checkbox[i].setOnCheckedChangeListener(checker);
        }

        btnGuradarMaterias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imprimirCadena(Cadena);
                finish();
            }
        });
    }

    public void imprimirCadena(ArrayList<String> list){
        System.out.println("Imprimir cadena");

        for(int i = 0; i<list.size(); i++){
            System.out.println("Cadena: " + list.get(i));
        }


    }
}