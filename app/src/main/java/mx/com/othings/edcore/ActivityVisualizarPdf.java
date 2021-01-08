package mx.com.othings.edcore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ActivityVisualizarPdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pdf);


        WebView myWebView = (WebView) findViewById(R.id.webviewPDF);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/pdf.html");
        //http://187.188.199.175:8080/alumnos/docs/getKardex?user_IdStudent=11567&cursadas=S
        //http://187.188.199.175:8080/alumnos/docs/getKardex?user_IdStudent=11472&cursadas=S
    }
}