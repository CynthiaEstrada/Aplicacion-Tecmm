package mx.com.othings.edcore.Activities.Auth;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CalificationRequest extends StringRequest {

    private static final String ruta = "https://compact-retina-296219.wn.r.appspot.com/calificaciones";

    private Map<String, String> parametros;
    public CalificationRequest(String ncontrol, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("IdAlumno", ncontrol+"");
    }

    @Override
    protected Map<String, String> getParams(){
        return parametros;
    }
}
