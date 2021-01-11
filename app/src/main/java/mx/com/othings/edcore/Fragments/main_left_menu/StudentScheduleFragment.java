package mx.com.othings.edcore.Fragments.main_left_menu;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.JsonObject;

import mx.com.othings.edcore.Activities.PDFReader;
import mx.com.othings.edcore.Lib.Resources.OnlineResourceListener;
import mx.com.othings.edcore.Lib.Service;
import mx.com.othings.edcore.R;

public class StudentScheduleFragment extends Fragment {

    private Service service;

    public StudentScheduleFragment() {



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new Service(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_schedule, container, false);

        Bundle bundle = new Bundle();
        bundle.putString("a", "https://firebasestorage.googleapis.com/v0/b/chatescolar-882c8.appspot.com/o/Documentos%2FimprimirCarga.pdf?alt=media&token=0f3e2fa3-0e0f-41eb-b7d0-fd2691a64fb1");
        Intent intent = new Intent(getActivity(), PDFReader.class);
        intent.putExtras(bundle);
        startActivity(intent);

/*        service.Online().getStudentShedule(new OnlineResourceListener() {
            @Override
            public void onResponse(Object response) {


                JsonObject json = (JsonObject) response;




            }

            @Override
            public void onError(int error_code, String error_message) {

            }
        });*/

        return view;
    }


}
