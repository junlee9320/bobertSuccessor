import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class TBA_API {
    private static OkHttpClient client = new OkHttpClient();
    private static String AUTH_NAME = "X-TBA-Auth_Key";
    private static String AUTH_KEY = "9df7WnNC3T2wDdnMiBK0QDTEmoXkB7oN4sZ84vrl6wzRs4TjbpKuCB3uDgNU65Dc";
    private static String base_url = "https://www.thebluealliance.com/api/v3/";

    public static String get(String url) throws IOException
    {
        Request request = new Request.Builder().url(url).addHeader(AUTH_NAME, AUTH_KEY).build();

        try (Response response = client.newCall(request).execute())
        {
            return response.body().string();
        }
        catch (java.net.SocketTimeoutException e)
        {
            String stat = get(base_url + "status");

            if (stat.contains("\"is_datafeed_down\": false"))
            {
                return "Something broke. Try again.";
            }
            else
            {
                return "TBA not responding. Check your internet connection.";
            }
        }
    }

    public static String getObject(String object){
        JSONObject obj = null;

        try {
            obj = new JSONObject(get(base_url + object));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (obj.getJSONObject("score_breakdown").getJSONObject("blue").getInt("cargoPoints")) + "";

    }

}