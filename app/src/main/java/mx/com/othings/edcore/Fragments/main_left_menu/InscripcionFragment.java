package mx.com.othings.edcore.Fragments.main_left_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import mx.com.othings.edcore.Activities.BlocDeNotas.ListaDeNotas;
import mx.com.othings.edcore.Activities.ChatGeneral.ChatGeneral;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.R;


public class InscripcionFragment extends Fragment {

    CardView agendarMateriasCard, DescargarKardexCard;
    Student student;


    public InscripcionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscripcion, container, false);


        String texto = getArguments().getString("a");
        Gson gson = new Gson();
        student = gson.fromJson(texto, Student.class);

        agendarMateriasCard = view.findViewById(R.id.cardAgendarMaterias);
        agendarMateriasCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle bundle = new Bundle();
                String texto = getArguments().getString("a");
                Intent intent = new Intent(getActivity(), ChatGeneral.class); //Cambiar a clase donde esta la activity de agendar materias
                bundle.putString("a", texto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        DescargarKardexCard = view.findViewById(R.id.ReferenciaBancariaCard);
        DescargarKardexCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bundle bundle = new Bundle();
                String texto = getArguments().getString("a");
                Intent intent = new Intent(getActivity(), ListaDeNotas.class);//Cambiar a clase donde esta la activity de agendar materias
                bundle.putString("a", texto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }
}