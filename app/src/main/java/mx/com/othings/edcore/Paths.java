package mx.com.othings.edcore;

public enum Paths {
//192.168.43.73 mi phone
    ROOT("http://172.16.142.192:8100/EdcoreAPI/public/api/"),
    AUTHENTICATION("http://172.16.142.192:8100/EdcoreAPI/public/oauth/token"),
    STUDENT("student/"),
    STUDENT_SCHEDULE("studentSchedule/"),
    STUDENT_NOTES("studentNotes/"),
    WEB_EDCORE_LOGIN_PATH("http://escolar.itszapopan.edu.mx:8080/alumnos/login"),
    KARDEX_PATH("http://escolar.itszapopan.edu.mx:8080/alumnos/docs/getKardex"),
    LANGUAGE_KARDEX_PATH("http://escolar.itszapopan.edu.mx:8080/alumnos/docs/getIdiomKardex");

    private String value;

    Paths( String v ){
        this.value = v;
    }

    public String getRootPath(){
        return ROOT.value;
    }
    public String getAuthenticationPath(){
        return AUTHENTICATION.value;
    }
    public String getStudentInformationPath( String registration_tag){
        return ROOT.value+STUDENT.value+registration_tag;
    }
    public String getStudentSchedulePath( String registration_tag ){
        return ROOT.value+STUDENT_SCHEDULE.value+registration_tag;
    }
    public String getStudentNotesPath( String registration_tag ){
        return ROOT.value+STUDENT_NOTES.value+registration_tag;
    }
    public String getStudentKardexPath(int student_id){
        return KARDEX_PATH.value+"?user_IdStudent="+student_id+"&cursadas=S";
    }
    public String getWebEdcoreLoginPath(String registration_tag , String password){
        return WEB_EDCORE_LOGIN_PATH.value+"?matricula="+registration_tag+"&passwd="+password;
    }
    public String getLanguageKardexPath( int student_id){
        return LANGUAGE_KARDEX_PATH.value+"?user_IdStudent="+student_id+"&cursadas=S";
    }

}
