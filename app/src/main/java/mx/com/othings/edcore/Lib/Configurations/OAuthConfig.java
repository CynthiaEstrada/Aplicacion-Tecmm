package mx.com.othings.edcore.Lib.Configurations;

public enum OAuthConfig {

    CLIENT_ID(3),
    CLIENT_SECRET("wzexpXTncjut8ziING9Mp6K0xYS9Qb7kwFRbEngI"),
    GRANT_TYPE("password");

    private String value;
    private int int_val;

    OAuthConfig(String value){
        this.value = value;
    }
    OAuthConfig(int int_val ){
        this.int_val = int_val;
    }

    public int getClientId(){
        return CLIENT_ID.int_val;
    }
    public String getClientSecret(){
        return CLIENT_SECRET.value;
    }
    public String getGrantType(){
        return GRANT_TYPE.value;
    }

}
