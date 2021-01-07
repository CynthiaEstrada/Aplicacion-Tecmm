package mx.com.othings.edcore.Adapters.Mensajes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mx.com.othings.edcore.Holder.MensajeriaHolder;
import mx.com.othings.edcore.Lib.Logica.LMensaje;
import mx.com.othings.edcore.Lib.Logica.LStudent;
import mx.com.othings.edcore.Persistencia.UsuarioDAO;
import mx.com.othings.edcore.R;

public class MensajeriaAdaptador extends RecyclerView.Adapter<MensajeriaHolder> {

    private List<LMensaje> listMensaje = new ArrayList<>();
    private Context c;

    public MensajeriaAdaptador(Context c) {
        this.c = c;
    }

    public int addMensajes(LMensaje lMensaje){

        listMensaje.add(lMensaje);
        int posicion = listMensaje.size()-1;
        notifyItemInserted(listMensaje.size());
        return posicion;
    }

    public void actualizarMensaje(int posicion, LMensaje lMensaje){
        listMensaje.set(posicion, lMensaje);
        notifyItemChanged(posicion);
    }

    @NonNull
    @Override
    public MensajeriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1){
            view = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes_emisor, parent, false);
        }else {
            view = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes_receptor, parent, false);
        }
        return new MensajeriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeriaHolder holder, int position) {

        LMensaje lMensaje = listMensaje.get(position);
        LStudent lStudent = lMensaje.getlStudent();

        if(lStudent != null){
            holder.getNombre().setText(lStudent.getEstudiante().getName());
            Glide.with(c).load(lStudent.getEstudiante().getPerfil_photo()).into(holder.getFotoMensajePerfil());

        }
        holder.getMensaje().setText(lMensaje.getMensaje().getMensaje());
        if(lMensaje.getMensaje().isContieneFoto()){

            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(lMensaje.getMensaje().getUrlFoto()).into(holder.getFotoMensaje());

        }else{
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);

        }


        holder.getHora().setText(lMensaje.fechaDeCreacionDelMensaje());
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(listMensaje.get(position).getlStudent() != null){
            if(listMensaje.get(position).getlStudent().getKey().equals(UsuarioDAO.getInstance().getKeyUsusario())){
                return 1;
            }else {
                return -1;
            }
        }else{
            return -1;
        }

        //return super.getItemViewType(position);
    }
}
