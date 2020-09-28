package mx.com.othings.edcore.Lib.Models;

public class Schedule {

    private String class_name;
    private String teacher_name;
    private String teacher_firstname;
    private String teacher_lastname;
    private String day;
    private String room;
    private String start_class_at;
    private String end_class_at;

    public Schedule(){}

    public Schedule(String class_name, String teacher_name, String teacher_firstname, String teacher_lastname, String day, String room, String start_class_at, String end_class_at) {
        this.class_name = class_name;
        this.teacher_name = teacher_name;
        this.teacher_firstname = teacher_firstname;
        this.teacher_lastname = teacher_lastname;
        this.day = day;
        this.room = room;
        this.start_class_at = start_class_at;
        this.end_class_at = end_class_at;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_firstname() {
        return teacher_firstname;
    }

    public void setTeacher_firstname(String teacher_firstname) {
        this.teacher_firstname = teacher_firstname;
    }

    public String getTeacher_lastname() {
        return teacher_lastname;
    }

    public void setTeacher_lastname(String teacher_lastname) {
        this.teacher_lastname = teacher_lastname;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStart_class_at() {
        return start_class_at;
    }

    public void setStart_class_at(String start_class_at) {
        this.start_class_at = start_class_at;
    }

    public String getEnd_class_at() {
        return end_class_at;
    }

    public void setEnd_class_at(String end_class_at) {
        this.end_class_at = end_class_at;
    }
}
