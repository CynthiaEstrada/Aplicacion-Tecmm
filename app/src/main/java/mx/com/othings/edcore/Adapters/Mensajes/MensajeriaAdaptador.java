package mx.com.othings.edcore.Adapters.Mensajes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.othings.edcore.Holder.MensajeriaHolder;
import mx.com.othings.edcore.Lib.Logica.LMensaje;
import mx.com.othings.edcore.R;

public class MensajeriaAdaptador extends RecyclerView.Adapter<MensajeriaHolder> {

    private List<LMensaje> listMensaje = new ArrayList<>();
    private Context c;

    public MensajeriaAdaptador(Context c) {
        this.c = c;
    }

    public void addMensajes(LMensaje lMensaje){

        listMensaje.add(lMensaje);

        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public MensajeriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes, parent, false);

        return new MensajeriaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeriaHolder holder, int position) {

        LMensaje lMensaje = listMensaje.get(position);

        holder.getNombre().setText(lMensaje.getlStudent().getEstudiante().getName());
        holder.getMensaje().setText(lMensaje.getMensaje().getMensaje());
        if(lMensaje.getMensaje().isContieneFoto()){

            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(lMensaje.getMensaje().getUrlFoto()).into(holder.getFotoMensaje());

        }else{
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);

        }

        Glide.with(c).load(lMensaje.getlStudent().getEstudiante().getPerfil_photo()).into(holder.getFotoMensajePerfil());

        holder.getHora().setText(lMensaje.fechaDeCreacionDelMensaje());
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
