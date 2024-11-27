import java.util.Locale;

public class RequestParams {
    private final String startDate;
    private final String endDate;
    private final Double latitude;
    private final Double longitude;

    public RequestParams(String startDate, String endDate, Double latitude, double longitude){
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getLocation() {
        String location = String.format(Locale.US,"%.4f,%.4f/", this.getLatitude(), this.getLongitude());
        return location;
    }

    public String getDates() {
        String dates = this.getEndDate() != null
                ? String.format("%s/%s", this.getStartDate(), this.getEndDate())
                : String.format("%s", this.getStartDate());
        return dates;
    }
}
