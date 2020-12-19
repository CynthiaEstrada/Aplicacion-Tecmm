package mx.com.othings.edcore.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mx.com.othings.edcore.Activities.BlocDeNotas.BlocDeNotas;
import mx.com.othings.edcore.Activities.BlocDeNotas.ListaDeNotas;
import mx.com.othings.edcore.Activities.ChatGeneral.ChatGeneral;
import mx.com.othings.edcore.Activities.EventosFacebook.EventosActivity;
import mx.com.othings.edcore.R;

public class PanelControlFragment extends Fragment {

    CardView Chat, Notes, Eventos;
    Activity chatActivity;



    public PanelControlFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel_control, container, false);

        Chat = view.findViewById(R.id.cardSocial);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatGeneral.class);
                startActivity(intent);
            }
        });

        Notes = view.findViewById(R.id.Notas);
        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListaDeNotas.class);
                startActivity(intent);
            }
        });

        Eventos = view.findViewById(R.id.cardEventos);
        Eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventosActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
