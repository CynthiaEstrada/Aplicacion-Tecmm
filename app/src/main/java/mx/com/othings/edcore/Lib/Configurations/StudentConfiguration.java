package mx.com.othings.edcore.Lib.Configurations;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import mx.com.othings.edcore.App;
import mx.com.othings.edcore.Lib.Models.Student;

public class StudentConfiguration {

    private Activity activity;
    private SharedPreferences sh;
    private Gson gson;

    public StudentConfiguration( Activity activity ){

        this.activity = activity;
        this.sh = activity.getSharedPreferences(App.getSharedPreferencesName(), Context.MODE_PRIVATE);
        this.gson = new Gson();

    }

    public void setConfiguration(StudentConfigurations studentConfigurations , boolean value ){

        SharedPreferences.Editor editor = sh.edit();
        editor.putBoolean(studentConfigurations.getValue(),value);
        editor.apply();

    }
    public void setStudent(StudentConfigurations studentConfigurations , Student student ){

        String json_student = gson.toJson(student);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString(studentConfigurations.getValue(),json_student);
        editor.apply();

    }
    public Student getStudent(){

        String json = sh.getString(StudentConfigurations.STUDENT_INFORMATION.getValue(),null);
        Student student = gson.fromJson(json,Student.class);
        return student;
    }

}
