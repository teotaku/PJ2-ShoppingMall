package supercoding.pj2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supercoding.pj2.entity.Product;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


   //키워드검색 page
   Page<Product> findByNameContaining(String keyword, Pageable pageable);
   //카테고리 분류
   List<Product> findByCategoryId(Long categoryId);
   //카테고리 분류 page
   Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
   //조회수 분류
   Page<Product> findAllByOrderByViewCountDesc(Pageable pageable);
   //구매수 분류
   Page<Product> findAllByOrderByPurchaseCountDesc(Pageable pageable);
}
