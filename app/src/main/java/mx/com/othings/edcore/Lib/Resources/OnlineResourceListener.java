package mx.com.othings.edcore.Lib.Resources;

public interface OnlineResourceListener {

    void onResponse(Object response);
    void onError(int error_code , String error_message);

}
