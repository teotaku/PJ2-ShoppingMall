package supercoding.pj2.exception;

import org.springframework.http.HttpStatus;

public class InvalidSearchConditionException extends CustomException {
    public InvalidSearchConditionException() {
        super("검색어를 입력하세요.", HttpStatus.BAD_REQUEST);
    }
}
