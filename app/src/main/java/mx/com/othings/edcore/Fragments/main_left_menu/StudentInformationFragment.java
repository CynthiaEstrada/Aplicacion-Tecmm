package mx.com.othings.edcore.Fragments.main_left_menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mx.com.othings.edcore.Activities.Auth.Login;
import mx.com.othings.edcore.Lib.Configurations.StudentConfiguration;
import mx.com.othings.edcore.Lib.Models.Califications.StudentNotes;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.SDB;
import mx.com.othings.edcore.Lib.Utilities;
import mx.com.othings.edcore.R;

public class StudentInformationFragment extends Fragment {
    public StudentInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_information, container, false);

        TextView username = view.findViewById(R.id.user_name);
        RoundedImageView student_img = view.findViewById(R.id.student_img);
        TextView email = view.findViewById(R.id.email);
        TextView address = view.findViewById(R.id.address);
        TextView gender = view.findViewById(R.id.gender);
        TextView career = view.findViewById(R.id.career);
        TextView semester = view.findViewById(R.id.semester);
        TextView cellphone = view.findViewById(R.id.cellphone);
        TextView birthday = view.findViewById(R.id.birthday);

        String texto = getArguments().getString("a");
        Gson gson = new Gson();
        Student student = gson.fromJson(texto, Student.class);

        String full_name = Utilities.capitalizeWords((student.getName()+" "+student.getFirst_name()+" "+ student.getLast_name()).toLowerCase());

        username.setText(full_name);
        charge_user_photo(student.getPerfil_photo(),student_img);
        email.setText(student.getEmail().toLowerCase());
        address.setText(Utilities.capitalizeWords(student.getAddress().toLowerCase()));
        gender.setText(student.getGender());
        cellphone.setText(student.getCellPhone());
        career.setText(student.getCarrera());
        semester.setText(student.getSemestre());
        birthday.setText(student.getBirthday());
        return view;
    }

    private void charge_user_photo(String image_base_64 , RoundedImageView roundedImageView){
        byte [] image_decoded = Utilities.decodeStringToBase64(image_base_64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_decoded,0,image_decoded.length);
        roundedImageView.setImageBitmap(bitmap);
    }

}
