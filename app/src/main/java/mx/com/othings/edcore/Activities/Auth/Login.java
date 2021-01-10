package mx.com.othings.edcore.Activities.Auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import mx.com.othings.edcore.Activities.BlocDeNotas.componentBd.ComponentNotes;
import mx.com.othings.edcore.Activities.BlocDeNotas.pojos.User;
import mx.com.othings.edcore.Activities.Permissions.CheckPermisions;
import mx.com.othings.edcore.Activities.ControlPanel;
import mx.com.othings.edcore.App;
import mx.com.othings.edcore.Lib.Auth.OAuthAuthentication;
import mx.com.othings.edcore.Lib.Auth.OAuthListener;
import mx.com.othings.edcore.Lib.Configurations.StudentConfigurations;
import mx.com.othings.edcore.Lib.Models.Califications.Score;
import mx.com.othings.edcore.Lib.Models.Califications.StudentNotes;
import mx.com.othings.edcore.Lib.Models.Califications.SubjectCalification;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Service;
import mx.com.othings.edcore.Persistencia.UsuarioDAO;
import mx.com.othings.edcore.R;
import mx.com.othings.jwtreader2.JWT.JWT;

public class Login extends AppCompatActivity {

    private TextInputEditText registration_tag_input,password_input;
    private CheckBox keep_me_authneticated_check_box;
    private boolean KEEP_ME_ATHENTICATED = false;
    private Button login_btn;
    private LottieAnimationView loader_animation;
    private OAuthAuthentication oauth;
    private Service service;
    private FirebaseDatabase dataBase;
    private FirebaseAuth mAuth;
    private static String controlNumber;

    private Student student;
    private StudentNotes studentNotes;
    double totalSemester = 0;
    private SubjectCalification subject;
    private Uri fotoDePerfilUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registration_tag_input = findViewById(R.id.registration_tag);
        password_input = findViewById(R.id.password);
        //keep_me_authneticated_check_box = findViewById(R.id.checkBox);
        login_btn = findViewById(R.id.login_btn);
        loader_animation = findViewById(R.id.loader_animation);
        //oauth = new OAuthAuthentication(this);
        service = new Service(this);
        dataBase = FirebaseDatabase.getInstance();

        //Student student;
        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bundle bundle = new Bundle();
                final Gson gson = new Gson();
                final String registration_tag = registration_tag_input.getText().toString();
                final String password = password_input.getText().toString();

                Response.Listener<String> res2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<SubjectCalification> subjectCalificationList = new ArrayList<>();
                            List<Double> parcial = new ArrayList<>();
                            List<String> status = new ArrayList<>();
                            for(int i=0; i<array.length(); i++) {
                                JSONObject j = array.getJSONObject(i);
                                parcial.add(j.getDouble("CalificacionParcial"));
                                status.add(j.getString("Acreditacion"));
                            }

                            int totalSubject = array.length() / 3;
                            int nextSubject = 0;
                            for(int i=0; i<totalSubject; i++){

                                JSONObject cali = array.getJSONObject(nextSubject);

                                String class_name = cali.getString("Nombre");
                                int list_number = 1;
                                List<Score> s = new ArrayList<>();

                                s.add(new Score(1,parcial.get(nextSubject),status.get(nextSubject)));
                                s.add(new Score(2,parcial.get(nextSubject+1),status.get(nextSubject+1)));
                                s.add(new Score(3,parcial.get(nextSubject+2),status.get(nextSubject+2)));

                                double sum = parcial.get(nextSubject) + parcial.get(nextSubject+1) + parcial.get(nextSubject+2);
                                double average = (float) sum / 3;

                                SubjectCalification subjectCalification = new SubjectCalification(class_name,list_number,s,average);
                                subjectCalificationList.add(subjectCalification);

                                nextSubject = nextSubject + 3;
                                totalSemester = (double) totalSemester + (double) average;
                            }
                            double semester_average = (double) totalSemester / totalSubject;
                            float semester_percentage = 70;
                            int subjects_cursed = totalSubject;

                            studentNotes = new StudentNotes(subjectCalificationList,semester_average,semester_percentage,subjects_cursed);

                            bundle.putString("b", gson.toJson(studentNotes));
                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                };
                CalificationRequest cal = new CalificationRequest(registration_tag, res2);
                RequestQueue cola2 = Volley.newRequestQueue(Login.this);
                cola2.add(cal);


                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if (ok == true) {
                                student = new Student(jsonRespuesta.getInt("IdAlumno"),
                                        jsonRespuesta.getString("Nombre"), jsonRespuesta.getString("ApellidoPaterno"),
                                        jsonRespuesta.getString("ApellidoMaterno"), jsonRespuesta.getString("FechaNacimiento"),
                                        jsonRespuesta.getString("Sexo"), jsonRespuesta.getString("Direccion"),
                                        jsonRespuesta.getString("Telefono"), jsonRespuesta.getString("Email"),
                                        jsonRespuesta.getString("Password"), jsonRespuesta.getString("Carrera"),
                                        jsonRespuesta.getString("Semestre"), jsonRespuesta.getString("Perfil"));
                                bundle.putString("a", gson.toJson(student));
                                FirebaseSingin(student.getEmail(), String.valueOf(student.getStudent_id()));
                                Intent intent = new Intent(Login.this, ControlPanel.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toasty.error(Login.this, "error al intentar acceder", Toast.LENGTH_LONG, true).show();
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                };
                LoginRequest r = new LoginRequest(registration_tag, password, res);
                RequestQueue cola = Volley.newRequestQueue(Login.this);
                cola.add(r);




                if (service.sdb().isFirstTimeUse()) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Nombre de estudiante: " + student.getName());
                            FirebaseCrearUsusario(student);
                            Gson gson = new Gson();
                            bundle.putString("a", gson.toJson(student));
                            Intent intent = new Intent(Login.this, CheckPermisions.class);
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                            //


                        }
                    },2000);

                }
                /*
                mAuth.signInWithEmailAndPassword("za15011331@zapopan.tecmm.edu.mx", "15011331")
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    setControlNumber(registration_tag_input.getText().toString());

                                    Intent intent = new Intent(Login.this, ControlPanel.class);

                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.

                                }

                            }
                        });
*/



/*
                final String registration_tag = registration_tag_input.getText().toString();
                final String password = password_input.getText().toString();


                if( registration_tag.length() == 8 ){

                    if( password.length() > 0 ){

                        login_btn.setVisibility(Button.INVISIBLE);
                        loader_animation.setVisibility(LottieAnimationView.VISIBLE);

                        oauth.authenticate(registration_tag, password, new OAuthListener() {
                            @Override
                            public void onAuth(JWT jwt) {

                                loader_animation.setVisibility(LottieAnimationView.INVISIBLE);
                                service.sdb().setToken(jwt.getToken());
                                App.setAccessTokenToHTTPClient(jwt.getToken());
                                Toasty.success(Login.this,"Usuario autenticado", Toast.LENGTH_LONG, true).show();
                                if(KEEP_ME_ATHENTICATED){
                                    service.sdb().getStudentConfiguration().setConfiguration(
                                            StudentConfigurations.KEEP_ME_AUTHETICATED,
                                            true
                                    );
                                }
                            */
                /*

                // service.sdb().saveUser(new User(registration_tag,password));*/
                                          /*  FirebaseUser currentUser = mAuth.getCurrentUser();
                                            DatabaseReference reference = dataBase.getReference("Usuarios/" + currentUser.getUid());
                                            reference.setValue(student);
                                else{

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Intent intent = new Intent(Login.this,ControlPanel.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            service.sdb().saveUser(new User(registration_tag,password));
                                            startActivity(intent);

                                        }
                                    },2000);

                                }

                            }

                            @Override
                            public void onAuthenticationFailed(int error_code, String message) {
                                login_btn.setVisibility(Button.VISIBLE);
                                loader_animation.setVisibility(LottieAnimationView.INVISIBLE);
                                Toasty.error(Login.this, message, Toast.LENGTH_LONG, true).show();

                            }
                        });

                    }
                    else{
                        Toasty.error(Login.this,"Escribe tu contraseña", Toast.LENGTH_LONG, true).show();
                    }

                }
                else{
                    Toasty.error(Login.this,"La matricula consta de 8 números", Toast.LENGTH_LONG, true).show();
                }*/

            }
        });

        /*keep_me_authneticated_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                KEEP_ME_ATHENTICATED = isChecked;
            }
        });*/

    }


    private void FirebaseCrearUsusario(final Student studentFirebase){

        System.out.println("Studen firebase: " + studentFirebase.getName());

        mAuth.createUserWithEmailAndPassword(student.getEmail(), String.valueOf(student.getStudent_id()))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /*fotoDePerfilUri = Uri.parse(student.getPerfil_photo());
                            UsuarioDAO.getInstance().subirFotoUri(fotoDePerfilUri, new UsuarioDAO.IDevolverURLDeFoto() {
                                @Override
                                public void devolverUrlString(String url) {
                                }
                            });*/
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            DatabaseReference reference = dataBase.getReference("Usuarios/" + currentUser.getUid());
                            reference.setValue(studentFirebase);
                            System.out.println("Se creo con exito el usuario");
                        } else {
                            System.out.println("No se creo con exito el usuario");
                        }

                    }
                });
    }

    public void FirebaseSingin(String correo, String contrasena){
        mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Se logeo correctamente.");
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("Error, credenciales incorrectas.");
                        }

                    }
                });
    }
}
