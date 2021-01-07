package mx.com.othings.edcore.Persistencia;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import mx.com.othings.edcore.Lib.Firebase.Mensajeria.Mensaje;
import mx.com.othings.edcore.Utilidades.Constantes;

public class MensajeriaDAO {

    private static MensajeriaDAO mensajeriaDAO;

    private FirebaseDatabase database;
    private DatabaseReference referenceDeMensajeria;

    public static MensajeriaDAO getInstance() {
        if (mensajeriaDAO == null) mensajeriaDAO = new MensajeriaDAO();
        return mensajeriaDAO;
    }

    private MensajeriaDAO() {
        database = FirebaseDatabase.getInstance();
        referenceDeMensajeria = database.getReference(Constantes.NODO_MENSAJES);
        //storage = FirebaseStorage.getInstance();
        //referenciaUsuarios = database.getReference(Constantes.NODO_USUARIOS);
        //referenceFotoPerfil = storage.getReference("Foto/FotoPerfil/" + getKeyUsusario());
    }

    public void nuevoMensaje(String key_emisor, String key_receptor, Mensaje mensaje){

        DatabaseReference referenciaEmisor = referenceDeMensajeria.child(key_emisor).child(key_receptor);
        DatabaseReference referenciaReceptor = referenceDeMensajeria.child(key_receptor).child(key_emisor);
        referenciaEmisor.push().setValue(mensaje);
        referenciaReceptor.push().setValue(mensaje);
    }
}
