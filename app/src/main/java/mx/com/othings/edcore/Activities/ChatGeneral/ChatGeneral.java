package mx.com.othings.edcore.Activities.ChatGeneral;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

//import androidx.appcompat.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import mx.com.othings.edcore.Activities.Auth.Login;
import mx.com.othings.edcore.Adapters.Mensajes.MensajeriaAdaptador;
import mx.com.othings.edcore.Lib.Firebase.Mensajeria.Mensaje;
import mx.com.othings.edcore.Lib.Logica.LMensaje;
import mx.com.othings.edcore.Lib.Logica.LStudent;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Persistencia.MensajeriaDAO;
import mx.com.othings.edcore.Persistencia.UsuarioDAO;
import mx.com.othings.edcore.R;
import mx.com.othings.edcore.Utilidades.Constantes;


public class ChatGeneral extends AppCompatActivity {

    private CircularImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private ImageButton btnEnviarFoto;

    private MensajeriaAdaptador adapter;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCadena;
    private static String controlNumber;

    private FirebaseAuth mAuth;
    private String NOMBRE_USUARIO;

    private String KEY_RECEPTOR;

    Bundle args = new Bundle();



    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat_general);

      /*  Bundle bundle = getIntent().getExtras();
        String texto = bundle.getString("a");

        Gson gson = new Gson();
        Student student = gson.fromJson(texto, Student.class);
*/
        Bundle bundleMensaje = getIntent().getExtras();
        if(bundleMensaje != null){
            KEY_RECEPTOR = bundleMensaje.getString("key_receptor");
        }else{
            finish();
        }
        //controlNumber = student.getStudent_id() + "";
        System.out.println(controlNumber);

        fotoPerfil = (CircularImageView) findViewById(R.id.fotoPerfil);
        nombre = (TextView) findViewById(R.id.nombre);

        fotoPerfilCadena = "";
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        adapter = new MensajeriaAdaptador(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);





        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensajeEnviar= txtMensaje.getText().toString();
                if(!mensajeEnviar.isEmpty()){
                    Mensaje mensaje = new Mensaje();
                    mensaje.setMensaje(mensajeEnviar);
                    mensaje.setContieneFoto(false);
                    mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsusario());
                    MensajeriaDAO.getInstance().nuevoMensaje(UsuarioDAO.getInstance().getKeyUsusario(), KEY_RECEPTOR, mensaje);
                    txtMensaje.setText("");
                }

            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona un archivo"), PHOTO_SEND);
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona un archivo"), PHOTO_PERFIL);

            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        FirebaseDatabase.
                getInstance().
                getReference(Constantes.NODO_MENSAJES).
                child(UsuarioDAO.getInstance().getKeyUsusario()).
                child(KEY_RECEPTOR).addChildEventListener(new ChildEventListener() {

            //Trar informacion de firebase y guardarla en una lista temporal
            Map<String, LStudent> mapUsuariosTemporales = new HashMap<>();
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                final Mensaje mensaje = snapshot.getValue(Mensaje.class);
                final LMensaje lMensaje = new LMensaje(snapshot.getKey(), mensaje);
                final int posicion = adapter.addMensajes(lMensaje);

                if(mapUsuariosTemporales.get(mensaje.getKeyEmisor()) != null){
                    lMensaje.setlStudent(mapUsuariosTemporales.get(mensaje.getKeyEmisor()));
                    adapter.actualizarMensaje(posicion, lMensaje);
                }else{
                    UsuarioDAO.getInstance().obtenerInformacionDeUsuarioPorLlave(mensaje.getKeyEmisor(), new UsuarioDAO.IDevolverUsuario() {
                        @Override
                        public void devolverUsuario(LStudent lStudent) {
                            mapUsuariosTemporales.put(mensaje.getKeyEmisor(), lStudent);
                            lMensaje.setlStudent(lStudent);
                            adapter.actualizarMensaje(posicion, lMensaje);
                        }

                        @Override
                        public void devolverError(String error) {
                            Toast.makeText(ChatGeneral.this, "Error: " + error, Toast.LENGTH_SHORT);

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setScrollBar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Mensaje mensaje = new Mensaje();
                            mensaje.setMensaje(" te ha enviado una foto");
                            mensaje.setUrlFoto(uri.toString());
                            mensaje.setContieneFoto(true);
                            mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsusario());
                            MensajeriaDAO.getInstance().nuevoMensaje(UsuarioDAO.getInstance().getKeyUsusario(), KEY_RECEPTOR, mensaje);
                        }
                    });

                }
            });
        }/*else if(requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("foto_Perfil");
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fotoPerfilCadena = uri.toString();
                            MensajeEnviar m = new MensajeEnviar(NOMBRE_USUARIO +" ha actualizado su foto de perfil", uri.toString(),NOMBRE_USUARIO, fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                            databaseReference.push().setValue(m);
                            Glide.with(ChatGeneral.this).load(uri.toString()).into(fotoPerfil);
                        }
                    });

                }
            });
        }*/


    }

   /* @Override
    protected void onPostResume() {
        super.onPostResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            btnEnviar.setEnabled(false);
            DatabaseReference reference = database.getReference("Usuarios/" + currentUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Student estudiante = snapshot.getValue(Student.class);
                    NOMBRE_USUARIO = estudiante.getName();
                    nombre.setText(NOMBRE_USUARIO);
                    btnEnviar.setEnabled(true);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{

        }
    }*/
}