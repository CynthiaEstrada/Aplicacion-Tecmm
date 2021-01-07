package mx.com.othings.edcore.Activities.ChatGeneral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import mx.com.othings.edcore.Holder.UsusarioViewHolder;
import mx.com.othings.edcore.Lib.Logica.LStudent;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.R;
import mx.com.othings.edcore.Utilidades.Constantes;

public class VerEstudiantes extends AppCompatActivity {

    private RecyclerView rvEstudiantes;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_estudiantes);
        rvEstudiantes = findViewById(R.id.rvUsuarios);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvEstudiantes.setLayoutManager(linearLayoutManager);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(Constantes.NODO_USUARIOS);

        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(query, Student.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Student, UsusarioViewHolder>(options) {
            @Override
            public UsusarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_view_usuario, parent, false);

                return new UsusarioViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(UsusarioViewHolder holder, int position, Student model) {
                System.out.println("Model Student: " + model.getName());
                Glide.with(VerEstudiantes.this).load(model.getPerfil_photo()).into(holder.getCivFotoPerfil());
                holder.getTxtNombreEstudiante().setText(model.getName());

                final LStudent lStudent = new LStudent(getSnapshots().getSnapshot(position).getKey(), model);
                holder.getLayoutPrincipal().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VerEstudiantes.this, ChatGeneral.class);
                        intent.putExtra("key_receptor", lStudent.getKey());
                        startActivity(intent);
                        //Toast.makeText(VerEstudiantes.this, "Key:  " + lStudent.getKey(),  Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        rvEstudiantes.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
