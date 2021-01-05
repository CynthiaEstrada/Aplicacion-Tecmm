package mx.com.othings.edcore.Lib.Models;

public class Student {
    private int student_id;
    private String name;
    private String first_name;
    private String last_name;
    private String birthday;
    private String gender;
    private String address;
    private String cellPhone;
    private String email;
    private String password;
    private String carrera;
    private String semestre;
    private String perfil_photo;

    public Student(){}

    public Student(int student_id, String name, String first_name, String last_name, String birthday, String gender, String address, String cellPhone, String email, String password, String carrera, String semestre, String perfil_photo) {
        this.student_id = student_id;
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.cellPhone = cellPhone;
        this.email = email;
        this.password = password;
        this.carrera = carrera;
        this.semestre = semestre;
        this.perfil_photo = perfil_photo;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPerfil_photo() {
        return perfil_photo;
    }

    public void setPerfil_photo(String perfil_photo) {
        this.perfil_photo = perfil_photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
