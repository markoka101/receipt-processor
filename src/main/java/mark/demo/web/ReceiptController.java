package mark.demo.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mark.demo.entity.Receipt;
import mark.demo.pojo.PointsResponse;
import mark.demo.pojo.ReceiptResponse;
import mark.demo.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private ReceiptService receiptService;

    /*
    Save receipt to repo and return the id
     */
    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(@Valid @RequestBody Receipt receipt, BindingResult bindingResult) {
        //check if the body is valid
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The receipt is invalid");
        }

        //create object with the id
        ReceiptResponse rr = receiptService.saveReceipt(receipt);

        return new ResponseEntity<>(rr, HttpStatus.OK);
    }

    /*
    Get the points from the receipt with the given id
     */
    @GetMapping("{id}/points")
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        PointsResponse pr = receiptService.getPoints(id);
        //if we returned a null value it means that the id does not exist
        if (pr == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No receipt found for that id");
        }

        //return the points of the receipt
        return new ResponseEntity<>(receiptService.getPoints(id),HttpStatus.OK);
    }
}
