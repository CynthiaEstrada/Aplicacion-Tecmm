package mx.com.othings.edcore.Lib.Configurations;

public enum StudentConfigurations {

    KEEP_ME_AUTHETICATED("KEEP_ME_AUTHENTICATED"),
    STUDENT_INFORMATION("STUDENT_INFORMATION");


    private String value;

    StudentConfigurations( String value ){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }


}
