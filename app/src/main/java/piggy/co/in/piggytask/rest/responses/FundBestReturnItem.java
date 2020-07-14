package piggy.co.in.piggytask.rest.responses;

public class FundBestReturnItem {
    String fundName, todate, fromDate;
    Double perChange;

    public FundBestReturnItem(String fundName, String fromDate, String todate, Double perChange) {
        this.fundName = fundName;
        this.todate = todate;
        this.fromDate = fromDate;
        this.perChange = perChange;
    }

    public String getFundName() {
        return fundName;
    }

    public String getTodate() {
        return todate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public Double getPerChange() {
        return perChange;
    }
}
