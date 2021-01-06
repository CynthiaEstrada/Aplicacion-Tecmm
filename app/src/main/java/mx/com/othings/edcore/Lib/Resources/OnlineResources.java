package mx.com.othings.edcore.Lib.Resources;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpClientConnection;
import mx.com.othings.edcore.App;
import mx.com.othings.edcore.Lib.Models.Califications.Score;
import mx.com.othings.edcore.Lib.Models.Califications.StudentNotes;
import mx.com.othings.edcore.Lib.Models.Califications.SubjectCalification;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Models.User;
import mx.com.othings.edcore.Lib.SDB;
import mx.com.othings.edcore.Paths;

public class OnlineResources {

    private SDB sdb;
    private Gson gson;

    public OnlineResources( SDB sdb ){
        this.sdb = sdb;
        this.gson = new Gson();
    }

    public void getStudentInformation(final OnlineResourceListener orl ){

        int USER_ID = sdb.getJWTObject().getPayload().getSub();

        App.getHttpClient().get(Paths.STUDENT.getStudentInformationPath(String.valueOf(USER_ID)), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){

                    String response = new String(responseBody);
                    Student student = gson.fromJson(response,Student.class);
                    orl.onResponse(student);
                }
                else{
                    orl.onError(statusCode,"error "+statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                orl.onError(statusCode,"error "+statusCode);

            }
        });

    }

    public void getStudentNotes(final OnlineResourceListener onl){

        String REGISTRATION_TAG = sdb.getUser().getRegistration_tag();

        App.getHttpClient().get(Paths.STUDENT_NOTES.getStudentNotesPath(REGISTRATION_TAG), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode == 200){

                    String response = new String(responseBody);
                    JsonParser parser = new JsonParser();
                    JsonArray json =  parser.parse(response).getAsJsonArray();
                    List<SubjectCalification> subjectCalificationList = new ArrayList<>();

                    for( int i = 0 ; i < json.size() - 1 ; i ++){

                        JsonObject j = json.get(i).getAsJsonObject();

                        String class_name = j.get("class_name").getAsString();
                        int list_number = j.get("list_number").getAsInt();
                        JsonObject scores = j.get("scores").getAsJsonObject();
                        List<Score> s = new ArrayList<>();

                        s.add(new Score(1,scores.get("unit_1").getAsJsonObject().get("score").getAsDouble(),scores.getAsJsonObject().get("unit_1").getAsJsonObject().get("score_type").getAsString(),scores.getAsJsonObject().get("unit_1").getAsJsonObject().get("score_status").getAsString()));
                        s.add(new Score(2,scores.get("unit_2").getAsJsonObject().get("score").getAsDouble(),scores.getAsJsonObject().get("unit_2").getAsJsonObject().get("score_type").getAsString(),scores.getAsJsonObject().get("unit_2").getAsJsonObject().get("score_status").getAsString()));
                        s.add(new Score(3,scores.get("unit_3").getAsJsonObject().get("score").getAsDouble(),scores.getAsJsonObject().get("unit_3").getAsJsonObject().get("score_type").getAsString(),scores.getAsJsonObject().get("unit_3").getAsJsonObject().get("score_status").getAsString()));

                        double average = j.get("average").getAsDouble();

                        //SubjectCalification subjectCalification = new SubjectCalification(class_name,list_number,s,average);
                        //subjectCalificationList.add(subjectCalification);
                    }

                    JsonObject stadistics = json.get(json.size()-1).getAsJsonObject();
                    double semester_average = stadistics.get("semester_average").getAsDouble();
                    double semester_percentage = stadistics.get("semester_percentage").getAsDouble();
                    int subjects_cursed = stadistics.get("subjects_cursed").getAsInt();

                    StudentNotes studentNotes = new StudentNotes(subjectCalificationList,semester_average,semester_percentage,subjects_cursed);

                    onl.onResponse(studentNotes);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                onl.onError(statusCode,new String(responseBody));

            }
        });

    }

    public void getStudentShedule( final OnlineResourceListener onl){

        User user = sdb.getUser();

        App.getHttpClient().get(Paths.STUDENT_SCHEDULE.getStudentSchedulePath(user.getRegistration_tag()), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(response).getAsJsonObject();
                onl.onResponse(json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onl.onError(statusCode,"Error "+statusCode);
            }
        });

    }

    public void getStudentKardex(final OnlineResourceListener onl){

        final Student student= sdb.getStudentConfiguration().getStudent();
        User user = sdb.getUser();
        final AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(Paths.WEB_EDCORE_LOGIN_PATH.getWebEdcoreLoginPath(student.getFirst_name(), user.getPassword()), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String html = new String(responseBody);
                if(!html.contains("Error 600")){


                    httpClient.get(Paths.KARDEX_PATH.getStudentKardexPath(student.getStudent_id()), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            String folder ="";
                            String file_name = "kardex.pdf";

                            try {

                                folder = "EDCORE/Kardex";

                                File new_folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),folder);
                                if(!new_folder.exists()){
                                    new_folder.mkdirs();
                                }
                                try {

                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/"+folder),file_name );
                                    FileOutputStream fos = new FileOutputStream(file);
                                    fos.write(responseBody);
                                    fos.close();

                                    onl.onResponse(file);


                                } catch (Exception ex) {

                                    onl.onError(0,"Error guardando el kardex");

                                }


                            } catch (Exception e) {
                                onl.onError(0,"Error guardando el kardex");
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            onl.onError(statusCode,"Error "+statusCode);
                        }
                    });

                }
                else{
                 onl.onError(600,"Acceso denegado");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onl.onError(statusCode,"Error "+statusCode);
            }
        });

    }

    public void getStudentLanguageKardex(final OnlineResourceListener onl){

        final Student student= sdb.getStudentConfiguration().getStudent();
        User user = sdb.getUser();
        final AsyncHttpClient httpClient = new AsyncHttpClient();

        httpClient.get(Paths.WEB_EDCORE_LOGIN_PATH.getWebEdcoreLoginPath(student.getFirst_name(), user.getPassword()), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String html = new String(responseBody);
                if(!html.contains("Error 600")){


                    httpClient.get(Paths.LANGUAGE_KARDEX_PATH.getLanguageKardexPath(student.getStudent_id()), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            String folder ="";
                            String file_name = "Language_Kardex.pdf";

                            try {

                                folder = "EDCORE/Kardex";

                                File new_folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),folder);
                                if(!new_folder.exists()){
                                    new_folder.mkdirs();
                                }
                                try {

                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/"+folder),file_name );
                                    FileOutputStream fos = new FileOutputStream(file);
                                    fos.write(responseBody);
                                    fos.close();

                                    onl.onResponse(file);


                                } catch (Exception ex) {

                                    onl.onError(0,"Error guardando el kardex");

                                }


                            } catch (Exception e) {
                                onl.onError(0,"Error guardando el kardex");
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            onl.onError(statusCode,"Error "+statusCode);
                        }
                    });

                }
                else{
                    onl.onError(600,"Acceso denegado");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onl.onError(statusCode,"Error "+statusCode);
            }
        });

    }
}
