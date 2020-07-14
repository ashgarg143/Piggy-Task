package piggy.co.in.piggytask.rest.responses;

public class FundDetailsItem {
    String fund_name, rating, category, riskometer;
    Double yoy_return, return_3yr, return_5yr;


    public FundDetailsItem(String fund_name, String rating, String category, String riskometer, Double yoy_return, Double return_3yr, Double return_5yr) {
        this.fund_name = fund_name;
        this.rating = rating;
        this.category = category;
        this.riskometer = riskometer;
        this.yoy_return = yoy_return;
        this.return_3yr = return_3yr;
        this.return_5yr = return_5yr;
    }



/*public FundDetailsItem(String fund_name, String rating, String yoy_return, String return_3yr, String return_5yr, String category, String riskometer) {
        this.fund_name = fund_name;
        this.rating = rating;
        this.yoy_return = yoy_return;
        this.return_3yr = return_3yr;
        this.return_5yr = return_5yr;
        this.category = category;
        this.riskometer = riskometer;
    }*/

    public String getFund_name() {
        return fund_name;
    }

    public String getRating() {
        return rating;
    }

    public Double getYoy_return() {
        return yoy_return;
    }

    public Double getReturn_3yr() {
        return return_3yr;
    }

    public Double getReturn_5yr() {
        return return_5yr;
    }

    public String getCategory() {
        return category;
    }

    public String getRiskometer() {
        return riskometer;
    }
}
