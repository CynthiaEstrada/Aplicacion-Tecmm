package mx.com.othings.edcore.Activities;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;

import java.io.File;
import java.net.URL;

import mx.com.othings.edcore.Activities.Inscripcion.ActivityVisualizarPdf;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.R;

public class PDFReader extends TemplateActivity {

    private PDFView pdfView;
    private ProgressBar progressBar;
    Uri uri;
    URL url;

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
        setContentView(R.layout.activity_pdfreader);
        File folder = new File("/sdcard/folder", "Kardex.pdf");

        //("http://187.188.199.175:8080/alumnos/docs/getKardex?user_IdStudent=11567&cursadas=S", folder);

        pdfView = findViewById(R.id.pdfViewer);
        progressBar = findViewById(R.id.pogressBarPdf);
        String urlPdf = getIntent().getExtras().getString("a");
        new ActivityVisualizarPdf(pdfView, progressBar).execute(urlPdf);
        //File file = gson.fromJson(getIntent().getStringExtra("file"),File.class);

        //pdfView.fromFile(file).enableSwipe(true).enableDoubletap(true).load();

        //pdfView.fromFile(folder).enableSwipe(true).enableDoubletap(true).load();


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
