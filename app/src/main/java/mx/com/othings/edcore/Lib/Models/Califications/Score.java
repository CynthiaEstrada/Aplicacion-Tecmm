package mx.com.othings.edcore.Lib.Models.Califications;

public class Score {

    private int unit_number;
    private double score;
    private String score_type;
    private String score_status;

    public Score(int unit_number, double score, String score_type, String score_status) {
        this.unit_number = unit_number;
        this.score = score;
        this.score_type = score_type;
        this.score_status = score_status;
    }

    public int getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(int unit_number) {
        this.unit_number = unit_number;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getScore_type() {
        return score_type;
    }

    public void setScore_type(String score_type) {
        this.score_type = score_type;
    }

    public String getScore_status() {
        return score_status;
    }

    public void setScore_status(String score_status) {
        this.score_status = score_status;
    }
}
