package mx.com.othings.edcore.Activities.Permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import mx.com.othings.edcore.Activities.ControlPanel;
import mx.com.othings.edcore.Activities.TemplateActivity;
import mx.com.othings.edcore.Adapters.Permissions.PermissionsAdapter;
import mx.com.othings.edcore.Fragments.permissions.CameraPermission;
import mx.com.othings.edcore.Fragments.permissions.LocationPermission;
import mx.com.othings.edcore.Fragments.permissions.ReadAndWriteMemory;
import mx.com.othings.edcore.R;

public class CheckPermisions extends TemplateActivity implements StepperLayout.StepperListener{

    private StepperLayout stepperLayout;
    private List<Fragment> permissionFragments;
    private PermissionsAdapter permissionsAdapter;

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
        setContentView(R.layout.activity_check_permisions);

        stepperLayout = findViewById(R.id.stepperLayout);
        permissionFragments = new ArrayList<>();

        List<Integer> permission_list = verifyPermissions();
        if( permission_list.contains(1)  ){
            permissionFragments.add(new ReadAndWriteMemory());
        }
        if( permission_list.contains(2)){
            permissionFragments.add(new LocationPermission());
        }
        if( permission_list.contains(3)){
            permissionFragments.add(new CameraPermission());
        }

        permissionsAdapter = new PermissionsAdapter(getSupportFragmentManager(),this,permissionFragments);
        stepperLayout.setAdapter(permissionsAdapter);
        stepperLayout.setListener(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 1:{ // STORAGE PERMISSION

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toasty.success(this,"Permiso concedido",Toast.LENGTH_LONG,true).show();

                }
                else{

                    Toasty.error(this,"Se rechazo el permiso de almacenamiento",Toast.LENGTH_LONG,true).show();
                }

                break;
            }
            case 2:{ // LOCATION PERMISSION

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toasty.success(this,"Permiso concedido",Toast.LENGTH_LONG,true).show();

                }
                else{

                    Toasty.error(this,"Se rechazo el permiso de localizacion",Toast.LENGTH_LONG,true).show();
                }

                break;
            }
            case 3:{ // CAMERA PERMISSION

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toasty.success(this,"Permiso concedido",Toast.LENGTH_LONG,true).show();

                }
                else{

                    Toasty.error(this,"Se rechazo el permiso de la camara",Toast.LENGTH_LONG,true).show();

                }

            }

        }

    }

    @Override
    public void onCompleted(View completeButton) {



        if( verifyPermissions().size() == 0 ){

            Toasty.success(this,"Bienvenido a Edcore", Toast.LENGTH_LONG,true).show();

            service.sdb().setFirstTimeUse();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(CheckPermisions.this,ControlPanel.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            },3000);

        }
        else{

            List<Integer> errors = verifyPermissions();

            if( errors.contains(1) ){
                Toasty.error(this,"No podemos continuar porque no diste permisos de almacenamiento", Toast.LENGTH_LONG,true).show();

            }
            else if( errors.contains(2) ){
                Toasty.error(this,"No podemos continuar porque no diste permisos ubicacion", Toast.LENGTH_LONG,true).show();

            }
            else if( errors.contains(3) ){
                Toasty.error(this,"No podemos continuar porque no diste permisos de camara", Toast.LENGTH_LONG,true).show();

            }

        }

    }

    @Override
    public void onError(VerificationError verificationError) {

        String error = verificationError.getErrorMessage();
        Toasty.error(this,error, Toast.LENGTH_LONG,true).show();

    }

    @Override
    public void onStepSelected(int newStepPosition) {



    }

    @Override
    public void onReturn() {

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
