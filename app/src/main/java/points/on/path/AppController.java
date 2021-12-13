package points.on.path;

import java.util.ArrayList;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


@RestController
public class AppController {

    public Point origin;
    public Point destination;

    @GetMapping("/origin")
    public Point origin(@RequestParam(value = "latitude") double latitude, @RequestParam(value = "longitude") double longitude){
        this.origin = new Point(latitude, longitude);
        return this.origin;
    }

    @GetMapping("/destination")
    public Point destination(@RequestParam(value = "latitude") double latitude, @RequestParam(value = "longitude") double longitude){
        this.destination = new Point(latitude, longitude);
        return this.destination;
    }

    @GetMapping("/route")
    public Response router() throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                                                .build();
        Request request = new Request.Builder()
                                     .url("https://maps.googleapis.com/maps/api/directions/json?origin=12.93175,%2077.62872&destination=12.92662,%2077.63696&key=<>")
                                     .method("GET", null)
                                     .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        JSONObject jobject = new JSONObject()
    }

    
}
