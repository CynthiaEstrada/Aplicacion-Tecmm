package mx.com.othings.edcore.Lib.Firebase.Mensajeria;

import com.google.firebase.database.ServerValue;

public class Mensaje {


    private String mensaje;
    private String urlFoto;
    private boolean contieneFoto;
    private String keyEmisor;
    private Object createTimestamp;



    public Mensaje() {
        createTimestamp = ServerValue.TIMESTAMP;
    }

    public String getKeyEmisor() {
        return keyEmisor;
    }

    public void setKeyEmisor(String keyEmisor) {
        this.keyEmisor = keyEmisor;
    }

    public boolean isContieneFoto() {
        return contieneFoto;
    }

    public void setContieneFoto(boolean contieneFoto) {
        this.contieneFoto = contieneFoto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Object getCreateTimestamp() {
        return createTimestamp;
    }
}
