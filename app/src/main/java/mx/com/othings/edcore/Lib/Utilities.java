package mx.com.othings.edcore.Lib;

import android.util.Base64;

public class Utilities {

    public static byte[] decodeStringToBase64( String data ){

        byte[] valueDecoded= new byte[0];
        valueDecoded = Base64.decode(data, Base64.DEFAULT);
        return valueDecoded;

    }
    public static String capitalizeWords(String name){

        String [] words = name.split(" ");
        String response = "";
        for(int i = 0 ;i < words.length ; i ++){
            response +=  Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1) + " ";
        }

        return response;
    }

}
