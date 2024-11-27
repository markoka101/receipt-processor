package mark.demo.service;

import lombok.AllArgsConstructor;
import mark.demo.entity.Receipt;
import mark.demo.pojo.Item;
import mark.demo.pojo.PointsResponse;
import mark.demo.pojo.ReceiptResponse;
import mark.demo.repo.ReceiptRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ReceiptServiceImpl implements ReceiptService{

    //Bean for the repo
    private ReceiptRepo receiptRepo;

    /*
    Saves the receipt and returns object containing id
     */
    @Override
    public ReceiptResponse saveReceipt(Receipt receipt) {
        receiptRepo.save(receipt);
        return new ReceiptResponse(receipt.getId());
    }

    /*
    Calculates points of the receipt and returns object contains points
     */
    @Override
    public PointsResponse getPoints(String id) {
        //retrieve the receipt from repo
        Optional<Receipt> wrapReceipt= receiptRepo.findById(id);

        //if it does not exist return null
        if (wrapReceipt.isEmpty()) {
            return null;
        }

        //unwrap the receipt
        Receipt receipt = wrapReceipt.get();

        //calculate the points using methods to score the different components
        int s = retailerPoints(receipt.getRetailer()) + itemPoints(receipt.getItems())
                + datePoints(receipt.getPurchaseDate()) + timePoints(receipt.getPurchaseTime())
                + pricePoints(receipt.getTotal());

        //return object containing points
        return new PointsResponse(s);
    }

    /*
    Calculate points from the retailer name
     */
    private int retailerPoints(String retailer)  {
        int s = 0;

        for (int i = 0; i < retailer.length(); i++) {
            //if the character is alphanumeric add to score
            if (Character.isLetterOrDigit(retailer.charAt(i))) {
                s++;
            }
        }

        //return the score
        return s;
    }

    private int itemPoints(Set<Item> items) {
        int s = 0;

        //check the amount of items and score the pairs
        if (items.size() % 2 == 1) {
            s += 5 * ((items.size()-1) /2);
        } else {
            s += 5 * (items.size() /2);
        }

        //iterate through the items
        for (Item item : items) {

            //the size of the string without spaces
            int len = item.getShortDescription().trim().length();

            //if divisible by 3 we calculate the points using x = price * 0.2 then rounding up
            if (len % 3 == 0) {

                //cast into int from double
                s += (int) Math.ceil(item.getPrice() * 0.2);
            }
        }

        //return score
        return s;
    }

    /*
    Calculate points from the day of month
     */
    private int datePoints(LocalDate date) {

        //if the day is odd we return 6
        if (date.getDayOfMonth() % 2 == 1) {
            return 6;
        }
        return 0;
    }

    /*
    Calculate the points from the time
     */
    private int timePoints(LocalTime time) {

        //if it's between 2 and 4 we return 10
        if (time.isAfter(LocalTime.of(13,59)) && time.isBefore(LocalTime.of(16,1))) {
            return 10;
        }
        return 0;
    }

    /*
    Calculate points from the price
     */
    private int pricePoints(double price) {
        int s = 0;

        //check if the price is a full dollar
        if (price % Math.floor(price) == 0){
            s += 50;
        }

        //check if price is divisible by 0.25
        if (price % 0.25 == 0) {
            s += 25;
        }

        //return the score
        return s;
    }
}
