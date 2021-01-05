package mx.com.othings.edcore.Lib.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import mx.com.othings.edcore.App;
import mx.com.othings.edcore.Lib.Configurations.StudentConfiguration;
import mx.com.othings.edcore.Lib.Configurations.StudentConfigurations;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Configurations.OAuthConfig;
import mx.com.othings.edcore.Lib.SDB;
import mx.com.othings.edcore.Paths;
import mx.com.othings.jwtreader2.JWT.JWT;

public class OAuthAuthentication {

    private SharedPreferences sp;
    private Activity activity;
    private Gson gson;
    private SDB sdb;

    public OAuthAuthentication( Activity activity ){

        this.activity = activity;
        this.sp = this.activity.getSharedPreferences(App.getSharedPreferencesName(), Context.MODE_PRIVATE);
        this.gson = new Gson();
        this.sdb = new SDB(activity);

    }

    public boolean isAuthenticated(){
        return sdb.isSet(StudentConfigurations.KEEP_ME_AUTHETICATED.getValue());
    }

    public void authenticate( OAuthListener oAuthListener){

        Student student = gson.fromJson( sp.getString("user",null) , Student.class );
        authenticate(student.getFirst_name(), student.getPassword(), oAuthListener );

    }

    public void authenticate(String registration_tag , String password , final OAuthListener oAuthListener){

        RequestParams requestParams = new RequestParams();
        requestParams.put("grant_type", OAuthConfig.GRANT_TYPE.getGrantType());
        requestParams.put("client_id",OAuthConfig.CLIENT_ID.getClientId());
        requestParams.put("client_secret",OAuthConfig.CLIENT_SECRET.getClientSecret());
        requestParams.put("registration_tag",registration_tag);
        requestParams.put("password",password);

        App.getHttpClient().post(Paths.AUTHENTICATION.getAuthenticationPath(), requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if( statusCode == 200 ){

                    JsonParser parser = new JsonParser();
                    JsonObject json = parser.parse(new String(responseBody)).getAsJsonObject();
                    JWT jwt = new JWT(json.get("access_token").getAsString());
                    oAuthListener.onAuth(jwt);
                }
                else{
                    oAuthListener.onAuthenticationFailed(statusCode,new String(responseBody));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                switch ( statusCode ){

                    case 0:{
                        oAuthListener.onAuthenticationFailed(statusCode,"No hay conexión a internet");
                        break;
                    }
                    case 400:{
                        oAuthListener.onAuthenticationFailed(statusCode,"Hay datos faltantes");
                        break;
                    }
                    case 401:{
                        oAuthListener.onAuthenticationFailed(statusCode,"Usuario o contraseña invalido");
                        break;
                    }
                    default:{
                        oAuthListener.onAuthenticationFailed(statusCode,"Error desconocido");
                    }

                }

            }
        });

    }



}
