package mx.com.othings.edcore.Fragments.main_left_menu;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import mehdi.sakout.fancybuttons.FancyButton;
import mx.com.othings.edcore.Activities.PDFReader;
import mx.com.othings.edcore.Adapters.Califications.SubjectListAdapter;
import mx.com.othings.edcore.Lib.Models.Califications.StudentNotes;
import mx.com.othings.edcore.Lib.Models.Califications.SubjectCalification;
import mx.com.othings.edcore.Lib.Models.Student;
import mx.com.othings.edcore.Lib.Resources.OnlineResourceListener;
import mx.com.othings.edcore.Lib.Service;
import mx.com.othings.edcore.R;


public class CalificationsFragment extends Fragment {

    private StudentNotes studentNotes;
    private FancyButton open_general_kardex;
    private FancyButton open_language_kardex;
    private LottieAnimationView document_loader;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    public CalificationsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // this.service = new Service(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_califications, container, false);

        String texto = getArguments().getString("b");
        Gson gson = new Gson();
        StudentNotes studentNotes = gson.fromJson(texto, StudentNotes.class);

        System.out.println("En calificacion: " + texto);

        final RecyclerView recyclerView = view.findViewById(R.id.subject_list);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        final RoundCornerProgressBar average = view.findViewById(R.id.average);
        final RoundCornerProgressBar semester_percentage = view.findViewById(R.id.semester_percentage);
        final RoundCornerProgressBar subjects_cursed = view.findViewById(R.id.subjects_cursed);
        final TextView average_title = view.findViewById(R.id.average_title);
        final TextView semester_percetage_title = view.findViewById(R.id.semester_percentage_title);
        final TextView subjects_cursed_title = view.findViewById(R.id.subjects_cursed_title);
        final LinearLayout loader = view.findViewById(R.id.loader);
        loader.setVisibility(LinearLayout.VISIBLE);
        document_loader = view.findViewById(R.id.loader_document);
        slidingUpPanelLayout = view.findViewById(R.id.uppanel);


        loader.setVisibility(LinearLayout.INVISIBLE);
        CalificationsFragment.this.studentNotes = studentNotes;
        average.setProgress((float) studentNotes.getSemester_average());
        average_title.setText(String.valueOf(studentNotes.getSemester_average()));
        semester_percentage.setProgress((float)100);
        semester_percetage_title.setText("100%");
        subjects_cursed.setProgress((float) studentNotes.getSubjects_cursed());
        subjects_cursed_title.setText(String.valueOf(studentNotes.getSubjects_cursed()));

        RecyclerView.Adapter adapter = new SubjectListAdapter(studentNotes.getSubjectCalificationList(), R.layout.subject_item, new SubjectListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        recyclerView.setAdapter(scaleInAnimationAdapter);
        return view;
    }

}
