package piggy.co.in.piggytask.rest.responses;

import java.util.List;

public class FundHoldingsItem {
    String fundName;
    String sectorNames;
    String holdingNames;


    public FundHoldingsItem(String fundName, String sectorNames, String holdingNames) {
        this.fundName = fundName;
        this.sectorNames = sectorNames;
        this.holdingNames = holdingNames;
    }

    public String getSectorNames() {
        return sectorNames;
    }

    public String getHoldingNames() {
        return holdingNames;
    }


    public String getFundName() {
        return fundName;
    }

}
