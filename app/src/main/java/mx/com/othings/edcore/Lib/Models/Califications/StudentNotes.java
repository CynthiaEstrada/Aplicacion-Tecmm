package mx.com.othings.edcore.Lib.Models.Califications;

import java.util.List;

public class StudentNotes {

    private List<SubjectCalification> subjectCalificationList;
    private double semester_average;
    private double semester_percentage;
    private int subjects_cursed;

    public StudentNotes(List<SubjectCalification> subjectCalificationList, double semester_avergae, double semester_percentage, int subjects_cursed) {
        this.subjectCalificationList = subjectCalificationList;
        this.semester_average = semester_avergae;
        this.semester_percentage = semester_percentage;
        this.subjects_cursed = subjects_cursed;
    }

    public StudentNotes(){}

    public List<SubjectCalification> getSubjectCalificationList() {
        return subjectCalificationList;
    }

    public void setSubjectCalificationList(List<SubjectCalification> subjectCalificationList) {
        this.subjectCalificationList = subjectCalificationList;
    }

    public double getSemester_average() {
        return semester_average;
    }

    public void setSemester_average(double semester_avergae) {
        this.semester_average = semester_avergae;
    }

    public double getSemester_percentage() {
        return semester_percentage;
    }

    public void setSemester_percentage(double semester_percentage) {
        this.semester_percentage = semester_percentage;
    }

    public int getSubjects_cursed() {
        return subjects_cursed;
    }

    public void setSubjects_cursed(int subjects_cursed) {
        this.subjects_cursed = subjects_cursed;
    }
}
