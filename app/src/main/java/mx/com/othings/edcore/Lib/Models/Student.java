package mx.com.othings.edcore.Lib.Models;

public class Student {

    private int id_user;
    private int student_id;
    private String name;
    private String first_name;
    private String last_name;
    private String social_security_number;
    private String email;
    private String password;
    private String blood_type;
    private String rfc;
    private String curp;
    private String phone;
    private String cellPhone;
    private String photo;
    private String personal_status;
    private String birthday;
    private String gender;
    private String from_town;
    private String from_state;
    private String registration_tag;

    public Student(){}

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public String getSocial_security_number() {
        return social_security_number;
    }

    public void setSocial_security_number(String social_security_number) {
        this.social_security_number = social_security_number;
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

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPersonal_status() {
        return personal_status;
    }

    public void setPersonal_status(String personal_status) {
        this.personal_status = personal_status;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFrom_town() {
        return from_town;
    }

    public void setFrom_town(String from_town) {
        this.from_town = from_town;
    }

    public String getFrom_state() {
        return from_state;
    }

    public void setFrom_state(String from_state) {
        this.from_state = from_state;
    }

    public String getRegistration_tag() {
        return registration_tag;
    }

    public void setRegistration_tag(String registration_tag) {
        this.registration_tag = registration_tag;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void prueba(String nombre, String password, int controlNumber, String email){
        setLast_name(nombre);
        setPassword(password);
        setStudent_id(controlNumber);
        setEmail(email);
    }
}
