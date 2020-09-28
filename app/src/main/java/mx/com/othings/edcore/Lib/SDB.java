package mx.com.othings.edcore.Lib;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import mx.com.othings.edcore.App;
import mx.com.othings.edcore.Lib.Configurations.StudentConfiguration;
import mx.com.othings.edcore.Lib.Models.User;
import mx.com.othings.jwtreader2.JWT.JWT;

public class SDB {

    private Activity activity;
    private SharedPreferences sh;
    private Gson gson;
    private String token;

    public SDB(Activity activity){

        this.activity = activity;
        this.sh = activity.getSharedPreferences(App.getSharedPreferencesName(), Context.MODE_PRIVATE);
        gson = new Gson();

    }
    public SharedPreferences getSharedPreferences(){
        return sh;
    }
    public void setToken(String token){

        SharedPreferences.Editor editor = sh.edit();
        editor.putString("access_token",token);
        editor.apply();

    }

    public boolean isFirstTimeUse(){

        if(sh.contains("FIRST_TIME_USE")){
            return false;
        }
        return true;
    }

    public void setFirstTimeUse(){

        SharedPreferences.Editor editor = sh.edit();
        editor.putBoolean("FIRST_TIME_USE",false);
        editor.apply();
    }


    public boolean isSet( String value ){
        if(sh.contains(value)){
            return true;
        }
        return false;

    }

    public void saveUser(User user){

        String json_user = gson.toJson(user);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("user",json_user);
        editor.apply();

    }

    public User getUser(){

        User user = null;
        if(sh.contains("user")){
            user = gson.fromJson(sh.getString("user",null),User.class);
        }
        return user;
    }

    public String getToken(){

        if(sh.contains("access_token")){
            this.token = sh.getString("access_token",null);
        }
        return token;

    }
    public JWT getJWTObject(){

        JWT jwt = null;
        if(sh.contains("access_token")){
           jwt = new JWT(sh.getString("access_token",null));
        }
        return jwt;

    }
    public StudentConfiguration getStudentConfiguration(){
        return new StudentConfiguration(activity);
    }

}
