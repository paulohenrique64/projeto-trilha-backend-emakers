package exception.product;

public class ProductExceptionHandler extends RuntimeException {
    public ProductExceptionHandler(String message) {
        super(message);
    }
}
