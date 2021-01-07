package mx.com.othings.edcore.Lib.Logica;

import com.fasterxml.jackson.core.PrettyPrinter;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mx.com.othings.edcore.Lib.Firebase.Mensajeria.Mensaje;

public class LMensaje {

    private String key;
    private Mensaje mensaje;
    private LStudent lStudent;

    public LMensaje(String key, Mensaje mensaje) {
        this.key = key;
        this.mensaje = mensaje;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public long getCreateTimestampLong(){
        return (long)mensaje.getCreateTimestamp();
    }

    public LStudent getlStudent() {
        return lStudent;
    }

    public void setlStudent(LStudent lStudent) {
        this.lStudent = lStudent;
    }

    public String fechaDeCreacionDelMensaje(){

        Date date = new Date(getCreateTimestampLong());
        PrettyTime prettyTime = new PrettyTime(new Date(), Locale.getDefault());
        return prettyTime.format(date);
        /*Date date = new Date(getCreateTimestampLong());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        return sdf.format(date);*/

    }
}
