package mx.com.othings.edcore.Activities.Inscripcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.com.othings.edcore.R;

public class ActivityVisualizarPdf extends AsyncTask<String, Void, InputStream> {


    PDFView pdfView;
    ProgressBar progressBar;

    public ActivityVisualizarPdf(PDFView pdfView, ProgressBar progressBar){
        this.pdfView = pdfView;
        this.progressBar = progressBar;
    }

    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode()==200){
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        }catch (IOException e){
            return null;
        }
        return  inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        pdfView.fromStream(inputStream).enableSwipe(true).enableDoubletap(true).load();
        progressBar.setVisibility(View.GONE);
    }
}