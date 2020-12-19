package mx.com.othings.edcore.Persistencia;

import com.google.firebase.auth.FirebaseAuth;

public class UsuarioDAO {

    private static UsuarioDAO usuarioDAO;

    public static UsuarioDAO getInstance(){
        if(usuarioDAO == null)usuarioDAO = new UsuarioDAO();
            return usuarioDAO;
    }

    public String getKeyUsusario(){
        return FirebaseAuth.getInstance().getUid();
    }

    public long fechaDeCreacionLong(){
        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp();

    }

    public long fechaDeUltimaVezQueSeLogeoLong(){
        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getLastSignInTimestamp();
    }
}
