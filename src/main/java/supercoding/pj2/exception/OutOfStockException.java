package supercoding.pj2.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String productName) {
        super("상품 재고가 부족합니다. " + productName);
    }
}
