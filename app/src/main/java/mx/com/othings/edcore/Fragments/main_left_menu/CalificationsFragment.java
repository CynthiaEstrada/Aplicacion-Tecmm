package mx.com.othings.edcore.Fragments.main_left_menu;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import mehdi.sakout.fancybuttons.FancyButton;
import mx.com.othings.edcore.Activities.PDFReader;
import mx.com.othings.edcore.Adapters.Califications.SubjectListAdapter;
import mx.com.othings.edcore.Lib.Models.Califications.StudentNotes;
import mx.com.othings.edcore.Lib.Resources.OnlineResourceListener;
import mx.com.othings.edcore.Lib.Service;
import mx.com.othings.edcore.R;


public class CalificationsFragment extends Fragment {

    private Service service;
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

        this.service = new Service(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_califications, container, false);
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
        open_general_kardex = view.findViewById(R.id.open_kardex);
        open_language_kardex = view.findViewById(R.id.open_idiom_kardex);
        document_loader = view.findViewById(R.id.loader_document);
        slidingUpPanelLayout = view.findViewById(R.id.uppanel);


        service.Online().getStudentNotes(new OnlineResourceListener() {
            @Override
            public void onResponse(Object response) {

                loader.setVisibility(LinearLayout.INVISIBLE);
                StudentNotes studentNotes = (StudentNotes) response;
                CalificationsFragment.this.studentNotes = studentNotes;
                average.setProgress((float) studentNotes.getSemester_average());
                average_title.setText(String.valueOf(studentNotes.getSemester_average()));
                semester_percentage.setProgress((float)studentNotes.getSemester_percentage());
                semester_percetage_title.setText(String.valueOf(studentNotes.getSemester_percentage()));
                subjects_cursed.setProgress((float) studentNotes.getSubjects_cursed() );
                subjects_cursed_title.setText(String.valueOf(studentNotes.getSubjects_cursed()));


                RecyclerView.Adapter adapter = new SubjectListAdapter(studentNotes.getSubjectCalificationList(), R.layout.subject_item, new SubjectListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {



                    }
                });

                recyclerView.setLayoutManager(layoutManager);
                ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
                recyclerView.setAdapter(scaleInAnimationAdapter);


            }

            @Override
            public void onError(int error_code, String error_message) {

                loader.setVisibility(LinearLayout.INVISIBLE);

            }
        });

        open_general_kardex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                document_loader.setAnimation("circular_progress_anim.json");
                document_loader.setRepeatMode(ValueAnimator.INFINITE);
                document_loader.playAnimation();

                service.Online().getStudentKardex(new OnlineResourceListener() {
                    @Override
                    public void onResponse(final Object response) {

                        document_loader.setAnimation("file_animation.json");
                        document_loader.setRepeatMode(ValueAnimator.INFINITE);
                        document_loader.playAnimation();

                        Toasty.success(getContext(),"Kardex descargado correctamente", Toast.LENGTH_LONG,true).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Gson gson = new Gson();

                                Intent intent = new Intent(getContext(), PDFReader.class);
                                intent.putExtra("file",gson.toJson(response));
                                startActivity(intent);

                            }
                        },2000);

                    }

                    @Override
                    public void onError(int error_code, String error_message) {

                        document_loader.setAnimation("file_animation.json");
                        document_loader.setRepeatMode(ValueAnimator.INFINITE);
                        document_loader.playAnimation();

                        Toasty.error(getContext(),error_message, Toast.LENGTH_LONG,true).show();

                    }
                });

            }
        });

        open_language_kardex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                document_loader.setAnimation("circular_progress_anim.json");
                document_loader.setRepeatMode(ValueAnimator.INFINITE);
                document_loader.playAnimation();

                service.Online().getStudentLanguageKardex(new OnlineResourceListener() {
                    @Override
                    public void onResponse(final Object response) {

                        document_loader.setAnimation("file_animation.json");
                        document_loader.setRepeatMode(ValueAnimator.INFINITE);
                        document_loader.playAnimation();

                        Toasty.success(getContext(),"Kardex descargado correctamente", Toast.LENGTH_LONG,true).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Gson gson = new Gson();

                                Intent intent = new Intent(getContext(), PDFReader.class);
                                intent.putExtra("file",gson.toJson(response));
                                startActivity(intent);

                            }
                        },2000);
                    }

                    @Override
                    public void onError(int error_code, String error_message) {

                        document_loader.setAnimation("file_animation.json");
                        document_loader.setRepeatMode(ValueAnimator.INFINITE);
                        document_loader.playAnimation();

                        Toasty.error(getContext(),error_message, Toast.LENGTH_LONG,true).show();
                    }
                });

            }
        });


        return view;
    }

}
