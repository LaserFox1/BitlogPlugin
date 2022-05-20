package main.Exception;

import java.io.IOException;
import java.net.URISyntaxException;

public class PluginException extends RuntimeException{

    private static final String CANNOT_POST_IO = "Cannot send HTTP-Post request - httpclient not found";
    private static final String CANNOT_POST_URI = "Cannot send HTTP-Post request - URI Syntax not valid";

    public PluginException(String message, Throwable rootCause){
        super(message,rootCause);
    }

    public static PluginException cannotPostIO(IOException ioE){
        return new PluginException(CANNOT_POST_IO, ioE);
    }
    public static PluginException cannotPostURI(URISyntaxException uE) {
        return new PluginException(CANNOT_POST_URI, uE);
    }


}
