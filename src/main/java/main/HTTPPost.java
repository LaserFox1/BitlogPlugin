package main;

import main.Exception.PluginException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HTTPPost {
    public static void post(JSONObject obj, String uri){
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest httppost = RequestBuilder.post()
                    .setUri(new URI(uri))
                    .addParameter("content", obj.toJSONString())
                    .build();

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        }
        catch (IOException ioE){
            throw PluginException.cannotPostIO(ioE);
        }
        catch (URISyntaxException uE){
            throw PluginException.cannotPostURI(uE);
        }
    }
}
