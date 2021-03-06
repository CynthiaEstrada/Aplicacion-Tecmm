package mx.com.othings.edcore.Activities.ChatGeneral;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

//import androidx.appcompat.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codetroopers.betterpickers.radialtimepicker.CircleView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import mx.com.othings.edcore.Adapters.Mensajes.AdapterMensajes;
import mx.com.othings.edcore.Adapters.Mensajes.MensajeEnviar;
import mx.com.othings.edcore.Adapters.Mensajes.MensajeRecibir;
import mx.com.othings.edcore.Adapters.Mensajes.MensajeriaAdaptador;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Utilities;
import mx.com.othings.edcore.R;



public class ChatGrupal extends AppCompatActivity {

    private CircularImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private ImageButton btnEnviarFoto;
    private RoundedImageView perfilfoto1;

    private AdapterMensajes adapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCadena, nombreStudent;



    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_chat_grupal);

        final String nombreGrupo = getIntent().getStringExtra("Chat Grupal");
        String texto = getIntent().getStringExtra("Datos");
        Gson gson = new Gson();
        final Student student = gson.fromJson(texto, Student .class);

        nombreStudent = student.getName();


        fotoPerfil = (CircularImageView) findViewById(R.id.fotoPerfil);
        perfilfoto1 = (RoundedImageView) findViewById(R.id.fotoPerfilMensaje);
        nombre = (TextView) findViewById(R.id.nombre);
        fotoPerfilCadena = "";
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(nombreGrupo); //Sala de Chat(Nombre)
        storage = FirebaseStorage.getInstance();

        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        nombre.setText(nombreGrupo);
//        charge_user_photo(student.getPerfil_photo(), perfilfoto1);

        fotoPerfilCadena = student.getPerfil_photo();


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new MensajeEnviar(txtMensaje.getText().toString(), nombreStudent,fotoPerfilCadena, "1", ServerValue.TIMESTAMP));
                txtMensaje.setText("");
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

        /*fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Selecciona un archivo"), PHOTO_PERFIL);

            }
        });*/

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MensajeRecibir m = snapshot.getValue(MensajeRecibir.class);
                adapter.addMensajes(m);
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
                            MensajeEnviar m = new MensajeEnviar(nombreStudent + "te ha mandado una foto", uri.toString(),nombre.getText().toString(), fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                            databaseReference.push().setValue(m);
                        }
                    });

                }
            });
        }else if(requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
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
                            MensajeEnviar m = new MensajeEnviar("Usuario ha actualizado su foto de perfil", uri.toString(),nombre.getText().toString(), fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                            databaseReference.push().setValue(m);
                            Glide.with(ChatGrupal.this).load(uri.toString()).into(fotoPerfil);
                        }
                    });

                }
            });
        }


    }

    private void charge_user_photo(String image_base_64 , RoundedImageView roundedImageView){

        byte [] image_decoded = Utilities.decodeStringToBase64(image_base_64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_decoded,0,image_decoded.length);
        roundedImageView.setImageBitmap(bitmap);
    }
}