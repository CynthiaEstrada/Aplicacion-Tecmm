package mx.com.othings.edcore.Activities.Auth;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import mx.com.othings.edcore.Activities.Permissions.CheckPermisions;
import mx.com.othings.edcore.Activities.ControlPanel;
import mx.com.othings.edcore.Activities.TemplateActivity;
import mx.com.othings.edcore.App;
import mx.com.othings.edcore.Lib.Auth.OAuthAuthentication;
import mx.com.othings.edcore.Lib.Auth.OAuthListener;
import mx.com.othings.edcore.R;
import mx.com.othings.jwtreader2.JWT.JWT;

public class Splash extends TemplateActivity {

    private Button retry_connection_btn;
    private OAuthAuthentication oAuthAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.retry_connection_btn = findViewById(R.id.retry_connection_btn);
        this.oAuthAuthentication = new OAuthAuthentication(this);

        if( oAuthAuthentication.isAuthenticated() ){

            oAuthAuthentication.authenticate(new OAuthListener() {
                @Override
                public void onAuth(JWT jwt) {
                    service.sdb().setToken(jwt.getToken());
                    App.setAccessTokenToHTTPClient(jwt.getToken());

                    Toasty.success(Splash.this,"Auto autenticación exitosa", Toast.LENGTH_LONG, true).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(verifyPermissions().size() == 0){

                                Intent intent = new Intent(Splash.this,ControlPanel.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else{

                                Intent intent = new Intent(Splash.this,CheckPermisions.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }

                        }
                    },3000);

                }

                @Override
                public void onAuthenticationFailed(int error_code, String message) {
                    Toasty.error(Splash.this, message, Toast.LENGTH_LONG, true).show();
                }
            });

        }
        else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(Splash.this,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            },3000);

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }
}
