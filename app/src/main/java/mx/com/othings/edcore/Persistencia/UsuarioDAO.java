package mx.com.othings.edcore.Persistencia;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import mx.com.othings.edcore.Lib.Firebase.Mensajeria.Mensaje;
import mx.com.othings.edcore.Lib.Logica.LStudent;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Utilidades.Constantes;

public class UsuarioDAO {


    public interface IDevolverUsuario{
        public void devolverUsuario(LStudent lStudent);
        public void devolverError(String error);
    }

    public interface IDevolverURLDeFoto{
        public void devolverUrlString(String url);
    }

    private static UsuarioDAO usuarioDAO;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference referenciaUsuarios;
    private StorageReference referenceFotoPerfil;


    public static UsuarioDAO getInstance() {
        if (usuarioDAO == null) usuarioDAO = new UsuarioDAO();
        return usuarioDAO;
    }

    private UsuarioDAO() {
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        referenciaUsuarios = database.getReference(Constantes.NODO_USUARIOS);
        referenceFotoPerfil = storage.getReference("Foto/FotoPerfil/" + getKeyUsusario());
    }

    public String getKeyUsusario() {
        return FirebaseAuth.getInstance().getUid();
    }

    public long fechaDeCreacionLong() {
        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp();

    }

    public long fechaDeUltimaVezQueSeLogeoLong() {
        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getLastSignInTimestamp();
    }

    public void obtenerInformacionDeUsuarioPorLlave(final String key, final IDevolverUsuario iDevolverUsuario){
        referenciaUsuarios.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                LStudent lStudent = new LStudent(key, student);
                iDevolverUsuario.devolverUsuario(lStudent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iDevolverUsuario.devolverError(error.getMessage());
            }
        });
    }

    public void fotoDePerfilParaUsuariosPorDefecto() {
        referenciaUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<LStudent> lStudentsLista = new ArrayList<>();
                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    Student student = childDataSnapshot.getValue(Student.class);
                    LStudent lStudent = new LStudent(childDataSnapshot.getKey(), student);
                    lStudentsLista.add(lStudent);
                }
                for (LStudent lStudent : lStudentsLista) {
                    if (lStudent.getEstudiante().getPerfil_photo() == null) {
                        referenciaUsuarios.child(lStudent.getKey()).child("perfil_photo").setValue(Constantes.FOTO_POR_DEFECTO);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void subirFotoUri(Uri uri, final IDevolverURLDeFoto iDevolverURLDeFoto) {
        String nombreFoto = "";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS.ss-mm-hh-dd-MM-yyyy", Locale.getDefault());
       nombreFoto = simpleDateFormat.format(date);
        final StorageReference fotoReferencia = referenceFotoPerfil.child(nombreFoto);
        fotoReferencia.putFile(uri).addOnSuccessListener((Executor) this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       iDevolverURLDeFoto.devolverUrlString(uri.toString());
                    }
                });
            }
        });
    }
}

