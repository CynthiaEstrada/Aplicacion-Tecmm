package mx.com.othings.edcore.Fragments.main_left_menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import org.w3c.dom.Text;

import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.SDB;
import mx.com.othings.edcore.Lib.Utilities;
import mx.com.othings.edcore.R;

public class StudentInformationFragment extends Fragment {

    private SDB sdb;

    public StudentInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sdb = new SDB(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Student student = sdb.getStudentConfiguration().getStudent();

        View view = inflater.inflate(R.layout.fragment_student_information, container, false);
        TextView username = view.findViewById(R.id.user_name);
        RoundedImageView student_img = view.findViewById(R.id.student_img);
        TextView email = view.findViewById(R.id.email);
        TextView city = view.findViewById(R.id.from_town);
        TextView state = view.findViewById(R.id.from_state);
        TextView gender = view.findViewById(R.id.gender);
        TextView phone_number = view.findViewById(R.id.phone_number);
        TextView blood_type = view.findViewById(R.id.blood_type);
        TextView nss = view.findViewById(R.id.social_security_number);
        TextView curp = view.findViewById(R.id.curp);
        TextView rfc = view.findViewById(R.id.rfc);
        TextView birthday = view.findViewById(R.id.birthday);

        String full_name = Utilities.capitalizeWords((student.getName()+" "+student.getFirst_name()+" "+ student.getLast_name()).toLowerCase());

        username.setText(full_name);
        charge_user_photo(student.getPhoto(),student_img);
        email.setText(student.getEmail().toLowerCase());
        city.setText(Utilities.capitalizeWords(student.getFrom_town().toLowerCase().replace("\n","")));
        state.setText(Utilities.capitalizeWords(student.getFrom_state().toLowerCase().replace("\n","")));
        gender.setText(student.getGender());
        phone_number.setText(student.getCellPhone());
        blood_type.setText(student.getBlood_type());
        nss.setText(student.getSocial_security_number().toUpperCase());
        curp.setText(student.getCurp().toUpperCase());
        rfc.setText(student.getRfc().toUpperCase());
        birthday.setText(student.getBirthday());


        return view;
    }

    private void charge_user_photo(String image_base_64 , RoundedImageView roundedImageView){

        byte [] image_decoded = Utilities.decodeStringToBase64(image_base_64);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_decoded,0,image_decoded.length);
        roundedImageView.setImageBitmap(bitmap);
    }


}
