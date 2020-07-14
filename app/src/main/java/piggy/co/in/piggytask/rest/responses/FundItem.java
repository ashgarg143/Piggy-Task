package piggy.co.in.piggytask.rest.responses;

public class FundItem {
    String name,category, details_id;
    Double yoy_return, return_3yr, return_5yr;

    public FundItem() {
    }

    public FundItem(String name, String category, String details_id, Double yoy_return, Double return_3yr, Double return_5yr) {
        this.name = name;
        this.category = category;
        this.details_id = details_id;
        this.yoy_return = yoy_return;
        this.return_3yr = return_3yr;
        this.return_5yr = return_5yr;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
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

    public String getDetails_id() {
        return details_id;
    }
}
