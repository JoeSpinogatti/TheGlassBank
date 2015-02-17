package theglassbank.gotocue;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Joe on 2/17/15.
 */
public class NextCue {

    public static void NextCuePost() {

        Future<HttpResponse<JsonNode>> future = Unirest.post("http://httpbin.org/post")
                .header("accept", "application/json")
                .field("cue", "next")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        JsonNode body = response.getBody();
                        InputStream rawBody = response.getRawBody();
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });
    }
}
