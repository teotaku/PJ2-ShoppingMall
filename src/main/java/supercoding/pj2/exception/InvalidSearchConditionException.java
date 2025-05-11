// InvalidSearchConditionException.java
package supercoding.pj2.exception;

public class InvalidSearchConditionException extends RuntimeException {
    public InvalidSearchConditionException() {
        super("검색어를 입력하세요.");
    }
}
