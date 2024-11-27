import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class WeatherService {
    private String urlStr;
    private final RequestParams requestParams;

    public WeatherService(RequestParams requestParams) {
        urlStr = WeatherSettings.BASE_URL;
        this.requestParams = requestParams;
    }

    public String getWeather() throws URISyntaxException, IOException, InterruptedException {
        var request = createRequest();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private HttpRequest createRequest()  {
        this.addLocation();
        this.addDates();
        this.addApiKey();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlStr))
                .headers("Content-Type","application/json")
                .GET()
                .build();
        return request;
    }

    private void addLocation() {
        String location = requestParams.getLocation();
        this.urlStr = this.urlStr.concat(location);
    }

    private void addDates() {
        String dates = requestParams.getDates();
        this.urlStr = this.urlStr.concat(dates);
    }

    private void addApiKey() {
        String key = String.format("?key=%s", WeatherSettings.API_KEY);
        this.urlStr = this.urlStr.concat(key);
    }

}

