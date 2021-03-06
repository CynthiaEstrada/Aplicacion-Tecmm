package mx.com.othings.edcore.Holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;

import mx.com.othings.edcore.R;

public class HolderMensajes extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView mensaje;
    private TextView hora;
    private RoundedImageView fotoMensajePerfil;
    private ImageView fotoMensaje;

    public HolderMensajes(View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombreMensaje);
        mensaje = (TextView) itemView.findViewById(R.id.mensajeMensaje);
        hora = (TextView) itemView.findViewById(R.id.horaMensaje);
        fotoMensajePerfil = (RoundedImageView) itemView.findViewById(R.id.fotoPerfilMensaje);
        fotoMensaje = (ImageView) itemView.findViewById(R.id.mensajeFoto);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public RoundedImageView getFotoMensajePerfil() {
        return fotoMensajePerfil;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(ImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }

    public void setFotoMensajePerfil(RoundedImageView fotoMensajePerfil) {
        this.fotoMensajePerfil = fotoMensajePerfil;
    }
}