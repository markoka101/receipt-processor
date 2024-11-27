package mark.demo.service;

import mark.demo.entity.Receipt;
import mark.demo.pojo.PointsResponse;
import mark.demo.pojo.ReceiptResponse;

public interface ReceiptService {
    //save receipt into db
    ReceiptResponse saveReceipt(Receipt receipt);

    //get points from the receipt
    PointsResponse getPoints(String id);
}
