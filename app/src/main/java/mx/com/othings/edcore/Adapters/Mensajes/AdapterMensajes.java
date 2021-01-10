package mx.com.othings.edcore.Adapters.Mensajes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mx.com.othings.edcore.Adapters.Mensajes.MensajeRecibir;
import mx.com.othings.edcore.Holder.HolderMensajes;
import mx.com.othings.edcore.R;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensajes> {

    private List<MensajeRecibir> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes( Context c) {
        this.c = c;
    }

    public void addMensajes(MensajeRecibir m){

        listMensaje.add(m);

        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HolderMensajes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes, parent, false);

        return new HolderMensajes(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensajes holder, int position) {

        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        //holder.getHora().setText(listMensaje.get(position).getHora());
        if(listMensaje.get(position).getType_mensaje().equals("2")){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);

            Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        }else if(listMensaje.get(position).getType_mensaje().equals("1")){
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }
        if(listMensaje.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        }else{

            Glide.with(c).load(listMensaje.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        }

        Long codigoHora = listMensaje.get(position).getHora();
        Date d = new Date(codigoHora);
        PrettyTime prettyTime = new PrettyTime(new Date(), Locale.getDefault());
        holder.getHora().setText(prettyTime.format(d));

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}