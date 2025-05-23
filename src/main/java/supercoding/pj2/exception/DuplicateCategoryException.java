package supercoding.pj2.exception;

import org.springframework.http.HttpStatus;

public class DuplicateCategoryException extends CustomException {
    public DuplicateCategoryException() {
        super("이미 존재하는 카테고리입니다.", HttpStatus.BAD_REQUEST);
    }
}
