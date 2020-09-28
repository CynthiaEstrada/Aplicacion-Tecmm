package mx.com.othings.edcore;

import android.app.Application;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.orm.SugarApp;

public class App extends SugarApp {


    private static AsyncHttpClient httpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        httpClient = new AsyncHttpClient();
        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(this);
        httpClient.setCookieStore(persistentCookieStore);

    }

    public static String getSharedPreferencesName(){
        return "edcore_shared_preferences_othings";
    }

    public static AsyncHttpClient getHttpClient(){
        return httpClient;
    }

    public static void setAccessTokenToHTTPClient( String ACCESS_TOKEN ){
        httpClient.addHeader("Authorization","Bearer "+ACCESS_TOKEN);
    }

    public static void newHttpClient(){
        httpClient = new AsyncHttpClient();
    }
}
