package mx.com.othings.edcore.Fragments.main_left_menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import mx.com.othings.edcore.Activities.BlocDeNotas.BlocDeNotas;
//import mx.com.othings.edcore.Activities.BlocDeNotas.ListaDeNotas;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.gson.Gson;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;

import mx.com.othings.edcore.Activities.BlocDeNotas.ListaDeNotas;
import mx.com.othings.edcore.Activities.BlocDeNotas.componentBd.ComponentNotes;
import mx.com.othings.edcore.Activities.BlocDeNotas.pojos.User;
import mx.com.othings.edcore.Activities.ChatGeneral.ChatGeneral;
import mx.com.othings.edcore.Activities.ChatGeneral.MenuChat;
import mx.com.othings.edcore.Activities.ChatGeneral.VerEstudiantes;
import mx.com.othings.edcore.Activities.EventosFacebook.EventosActivity;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Utilities;
import mx.com.othings.edcore.R;

public class PanelControlFragment extends Fragment {

    CardView Chat, Notes, Eventos;
    Activity chatActivity;

    Student student;

    public PanelControlFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel_control, container, false);


        String texto = getArguments().getString("a");
        Gson gson = new Gson();
        student = gson.fromJson(texto, Student.class);

        Chat = view.findViewById(R.id.cardSocial);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("antes del texto");
                final Bundle bundle = new Bundle();
                String texto = getArguments().getString("a");
                Intent intent = new Intent(getActivity(), VerEstudiantes.class);
                System.out.println(texto);
                bundle.putString("a", texto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Notes = view.findViewById(R.id.Notas);
        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bundle bundle = new Bundle();
                String texto = getArguments().getString("a");
                Intent intent = new Intent(getActivity(), ListaDeNotas.class);
                bundle.putString("a", texto);
                intent.putExtras(bundle);
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


    private void charge_user_photo(String image_base_64 , RoundedImageView roundedImageView){

        byte [] image_decoded = Utilities.decodeStringToBase64(image_base_64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_decoded,0,image_decoded.length);
        roundedImageView.setImageBitmap(bitmap);
    }




}
