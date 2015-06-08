package com.neocite.eaiblza.infrastructure.translate;

/**
 * Created by paulo-silva on 6/8/15.
 */
public class dinofauro {

    public static String translate(String message){

        message = message.replace("v", "f");
        message = message.replace("s ", "#@#");
        message = message.replace("#@#", "s ");
        message = message.replace("s", "f");
        message = message.replace("ç", "f");
        message = message.replace("b", "f");
        message = message.replace("mp", "mpf");
        message = message.replace("t", "f");

        message = message.replace("ã", "a");
        message = message.replace("Ã", "a");

        message = message.replace("õ", "o");
        message = message.replace("Õ", "O");

        message = message.replace("í", "o");
        message = message.replace("Í", "O");

        message = message.replace("é", "e");
        message = message.replace("É", "E");

        message = message.replace("V", "F");
        message = message.replace("S", "F");
        message = message.replace("B", "F");
        message = message.replace("T", "F");
        return message;
    }

}
