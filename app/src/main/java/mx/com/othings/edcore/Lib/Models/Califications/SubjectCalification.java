package mx.com.othings.edcore.Lib.Models.Califications;

import java.util.List;

import mx.com.othings.edcore.Lib.Models.Califications.Score;

public class SubjectCalification {

    private String subject;
    private int list_number;
    private List<Score> scoreList;
    private double average;

    public SubjectCalification(String subject, double average) {
        this.subject = subject;
        this.list_number = list_number;
        this.scoreList = scoreList;
        this.average = average;
    }

    public SubjectCalification(){}

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getList_number() {
        return list_number;
    }

    public void setList_number(int list_number) {
        this.list_number = list_number;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
