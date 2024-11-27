import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Scanner;
import org.json.*;

public class ConsoleDialog {
    Scanner scanner;
    private RequestParams requestParams;
    private String data;
    private double resultTemp;

    public ConsoleDialog() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Hello!");
        while (true) {
            try {
                this.readRequestParameters();
                this.callWeatherservice();
                System.out.println(data);
                this.parseDataAndCalculateTemp();
                System.out.println(resultTemp);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Try again from the start");
            }
        }
    }

    private void readRequestParameters() {
        System.out.println("Write latiude");
        double latitude = scanner.nextDouble();
        System.out.println("Write longitude");
        double longitude = scanner.nextDouble();
        System.out.println("Write date (format yyyy-mm-dd)");
        String dateStr = scanner.next();
        LocalDate date = LocalDate.parse(dateStr);
        System.out.println("Write period in days");
        int period = scanner.nextInt();
        if (period < 1) { throw new IllegalArgumentException("Period must be greater than 1");}
        LocalDate endDate = null;
        if (period > 1) {
            endDate = date.plusDays(period-1);
        }
        String endDateStr = endDate != null ? endDate.toString() : null;
        requestParams = new RequestParams(dateStr, endDateStr, latitude, longitude);
    }

    private void callWeatherservice() throws URISyntaxException, IOException, InterruptedException {
        WeatherService weatherService = new WeatherService(requestParams);
        data = weatherService.getWeather();
    }

    private void parseDataAndCalculateTemp() {
        JSONObject obj = new JSONObject(data);
        JSONArray arr = obj.getJSONArray("days");
        double temp = 0;
        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject innerObj = arr.getJSONObject(i);
            temp += innerObj.getDouble("temp");
        }
        resultTemp = temp/arr.length();
    }
}
