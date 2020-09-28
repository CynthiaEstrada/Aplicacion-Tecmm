package mx.com.othings.edcore.Lib.Auth;


import mx.com.othings.jwtreader2.JWT.JWT;

public interface OAuthListener {

    void onAuth(JWT jwt);
    void onAuthenticationFailed( int error_code , String message );

}
