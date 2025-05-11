package supercoding.pj2.exception;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException() {
        super("이미 존재하는 카테고리입니다.");
    }
}
