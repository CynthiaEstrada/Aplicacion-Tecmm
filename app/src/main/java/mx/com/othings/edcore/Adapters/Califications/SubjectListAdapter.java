package mx.com.othings.edcore.Adapters.Califications;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import java.util.List;

import mx.com.othings.edcore.Lib.Models.Califications.Score;
import mx.com.othings.edcore.Lib.Models.Califications.SubjectCalification;
import mx.com.othings.edcore.R;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder>{


    private List<SubjectCalification> subjectCalificationList;
    private int layout;
    private OnItemClickListener onItemClickListener;


    public SubjectListAdapter(List<SubjectCalification> subjectCalificationList, int layout, OnItemClickListener onItemClickListener) {
        this.subjectCalificationList = subjectCalificationList;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubjectCalification sc = subjectCalificationList.get(position);
        List<Score> scoreList = sc.getScoreList();

        holder.bindSubjectName(sc.getSubject());
        holder.bindListNumber(sc.getList_number());
        holder.bindAverageProgress((float)sc.getAverage());
        holder.bindSubjectLetter(Character.toUpperCase(sc.getSubject().charAt(0)));
        holder.bindAverage(String.valueOf(sc.getAverage()));
        holder.bindBox(onItemClickListener,position);

        holder.bindUnitNumber1(scoreList.get(0).getUnit_number());
        holder.bindUnitStatus1(scoreList.get(0).getScore_status());
        holder.bindUnitCalification1(scoreList.get(0).getScore());

        holder.bindUnitNumber2(scoreList.get(1).getUnit_number());
        holder.bindUnitStatus2(scoreList.get(1).getScore_status());
        holder.bindUnitCalification2(scoreList.get(1).getScore());

        holder.bindUnitNumber3(scoreList.get(2).getUnit_number());
        holder.bindUnitStatus3(scoreList.get(2).getScore_status());
        holder.bindUnitCalification3(scoreList.get(2).getScore());
    }

    @Override
    public int getItemCount() {
        return subjectCalificationList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{


        public RoundedLetterView subject_letter;
        public RoundCornerProgressBar average_progress;
        public TextView subject_name;
        public TextView list_number;
        public TextView average;
        public LinearLayout box;

        public TextView unit_number_1;
        public TextView unit_number_2;
        public TextView unit_number_3;
        public TextView unit_status_1;
        public TextView unit_status_2;
        public TextView unit_status_3;
        public TextView unit_calification_1;
        public TextView unit_calification_2;
        public TextView unit_calification_3;

        public ViewHolder(View view){
                super(view);

                this.subject_letter = view.findViewById(R.id.subject_letter);
                this.subject_name = view.findViewById(R.id.subject_name);
                this.list_number = view.findViewById(R.id.subject_list_number);
                this.average_progress = view.findViewById(R.id.progress_average);
                this.average = view.findViewById(R.id.average);
                this.box = view.findViewById(R.id.cardView);
                this.unit_number_1 = view.findViewById(R.id.unit_1_number);
                this.unit_number_2 = view.findViewById(R.id.unit_2_number);
                this.unit_number_3 = view.findViewById(R.id.unit_3_number);
                this.unit_status_1 = view.findViewById(R.id.unit_1_status);
                this.unit_status_2 = view.findViewById(R.id.unit_2_status);
                this.unit_status_3 = view.findViewById(R.id.unit_3_status);
                this.unit_calification_1 = view.findViewById(R.id.unit_1_calification);
                this.unit_calification_2 = view.findViewById(R.id.unit_2_calification);
                this.unit_calification_3 = view.findViewById(R.id.unit_3_calification);

        }

        public void bindSubjectLetter(char letter){
            this.subject_letter.setTitleText(String.valueOf(letter));

        }
        public void bindSubjectName(String subject_name ){
            this.subject_name.setText(subject_name);
        }
        public void bindListNumber( int list_number ){
            this.list_number.setText( String.valueOf(list_number) );
        }
        public void bindAverageProgress( float average ){
            this.average_progress.setProgress(average);
        }
        public void bindAverage( String average ){
            this.average.setText(average);
        }
        public void bindBox(final OnItemClickListener onItemClickListener, final int  position){
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }

        public void bindUnitNumber1(int number){
            this.unit_number_1.setText(String.valueOf(number));
        }
        public void bindUnitNumber2(int number){
            this.unit_number_2.setText(String.valueOf(number));
        }
        public void bindUnitNumber3(int number){
            this.unit_number_3.setText(String.valueOf(number));
        }
        public void bindUnitStatus1( String status ){
            this.unit_status_1.setText(status);
        }
        public void bindUnitStatus2( String status ){
            this.unit_status_2.setText(status);
        }
        public void bindUnitStatus3( String status ){
            this.unit_status_3.setText(status);
        }
        public void bindUnitCalification1( double calification ){
            this.unit_calification_1.setText( String.valueOf(calification) );
        }
        public void bindUnitCalification2( double calification ){
            this.unit_calification_2.setText( String.valueOf(calification) );
        }
        public void bindUnitCalification3( double calification ){
            this.unit_calification_3.setText( String.valueOf(calification) );
        }



    }

    public interface OnItemClickListener{
        void onItemClick( int position );
    }

}
