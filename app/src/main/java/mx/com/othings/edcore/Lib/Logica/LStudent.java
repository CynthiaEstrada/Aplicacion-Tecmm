package mx.com.othings.edcore.Lib.Logica;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Persistencia.UsuarioDAO;

public class LStudent {

    private String key;
    private Student estudiante;

    public LStudent(String key, Student estudiante) {
        this.key = key;
        this.estudiante = estudiante;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Student getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Student estudiante) {
        this.estudiante = estudiante;
    }


    public String ObtenerFechaCracion(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(UsuarioDAO.getInstance().fechaDeCreacionLong());
        return simpleDateFormat.format(date);
    }

    public String ObtenerFechaUltimaVezQueSeLogeo(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date(UsuarioDAO.getInstance().fechaDeUltimaVezQueSeLogeoLong());
        return simpleDateFormat.format(date);
    }
}
