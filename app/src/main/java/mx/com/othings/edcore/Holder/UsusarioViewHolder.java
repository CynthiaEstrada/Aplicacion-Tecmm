package mx.com.othings.edcore.Holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.siyamed.shapeimageview.CircularImageView;

import mx.com.othings.edcore.R;

public class UsusarioViewHolder extends RecyclerView.ViewHolder {

    private CircularImageView civFotoPerfil;
    private TextView txtNombreEstudiante;
    private LinearLayout layoutPrincipal;


    public UsusarioViewHolder(@NonNull View itemView) {
        super(itemView);
        civFotoPerfil = itemView.findViewById(R.id.civFotoPerfil);
        txtNombreEstudiante = itemView.findViewById(R.id.txtNombreEstudiante);
        layoutPrincipal = itemView.findViewById(R.id.layoutPrincipal);
    }

    public CircularImageView getCivFotoPerfil() {
        return civFotoPerfil;
    }

    public void setCivFotoPerfil(CircularImageView civFotoPerfil) {
        this.civFotoPerfil = civFotoPerfil;
    }

    public TextView getTxtNombreEstudiante() {
        return txtNombreEstudiante;
    }

    public void setTxtNombreEstudiante(TextView txtNombreEstudiante) {
        this.txtNombreEstudiante = txtNombreEstudiante;
    }

    public LinearLayout getLayoutPrincipal() {
        return layoutPrincipal;
    }

    public void setLayoutPrincipal(LinearLayout layoutPrincipal) {
        this.layoutPrincipal = layoutPrincipal;
    }
}
