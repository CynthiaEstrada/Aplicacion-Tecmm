package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import mx.com.othings.edcore.Activities.Auth.CargaMateriasRequest;
import mx.com.othings.edcore.Activities.Auth.Login;
import mx.com.othings.edcore.Activities.ControlPanel;
import mx.com.othings.edcore.Fragments.main_left_menu.InscripcionFragment;
import mx.com.othings.edcore.Lib.Models.Califications.StudentNotes;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.R;

public class activity_carga_de_materias extends AppCompatActivity {

    private Button btnGuradarMaterias;
    private ArrayList<String> Cadena = new ArrayList<String>();
    private ArrayList<String> Materias = new ArrayList<String>();
    private CheckBox[] checkbox = new CheckBox[3];
    private CheckBox check1, check2, check3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_de_materias);

        check1 = findViewById(R.id.checkBox1);
        check2 = findViewById(R.id.checkBox2);
        check3 = findViewById(R.id.checkBox3);


        String texto = getIntent().getExtras().getString("notas");
        Materias = getIntent().getExtras().getStringArrayList("materias");
        imprimirCadena(Materias);
        Gson gson = new Gson();
        StudentNotes studentNotes = gson.fromJson(texto, StudentNotes.class);



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
            for(int k = 0; k < Materias.size(); k++){
                System.out.println("Materia: " + Materias.get(k));
                System.out.println("Checkbox: " + checkbox[i].getText());

                if(checkbox[i].getText().equals(Materias.get(k))){

                    checkbox[i].setEnabled(false);
                    //checkbox[i].setChecked(true);

                }
            }
        }

        btnGuradarMaterias.setOnClickListener(new View.OnClickListener() {
            //ArrayList<String> list;

            @Override
            public void onClick(View v) {
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                };

                Bundle bundle = getIntent().getExtras();
                String texto = bundle.getString("a");
                Gson gson = new Gson();
                Student student = gson.fromJson(texto, Student.class);

                System.out.println("Lista: " + Cadena.size());
                for(int i=0; i<Cadena.size(); i++) {
                    System.out.println("Cadena: " + Cadena.get(i));
                    if(Cadena.get(i).equals("ESTRUCTURA DE DATOS")){
                        CargaMateriasRequest carga = new CargaMateriasRequest(student.getStudent_id(), 7, 3, respuesta);
                        RequestQueue cola = Volley.newRequestQueue(activity_carga_de_materias.this);
                        cola.add(carga);
                    }
                    else if(Cadena.get(i).equals("CALCULO VECTORIAL")){
                        CargaMateriasRequest carga = new CargaMateriasRequest(student.getStudent_id(), 8, 4, respuesta);
                        RequestQueue cola = Volley.newRequestQueue(activity_carga_de_materias.this);
                        cola.add(carga);
                    }
                    else if(Cadena.get(i).equals("CULTURA EMPRESARIAL")){
                        CargaMateriasRequest carga = new CargaMateriasRequest(student.getStudent_id(), 9, 5, respuesta);
                        RequestQueue cola = Volley.newRequestQueue(activity_carga_de_materias.this);
                        cola.add(carga);
                    }
                }

                Intent i = new Intent(activity_carga_de_materias.this, Login.class);

                Toasty.success(activity_carga_de_materias.this,"Materias agendadas correctamente", Toast.LENGTH_LONG, true).show();

                activity_carga_de_materias.this.startActivity(i);
                activity_carga_de_materias.this.finish();

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