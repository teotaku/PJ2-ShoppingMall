package supercoding.pj2.exception;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends CustomException {
    public OutOfStockException(String productName) {
        super("상품 재고가 부족합니다. " + productName, HttpStatus.CONFLICT);
    }
}
