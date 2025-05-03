package bymyself.pj2.repository;

import bymyself.pj2.entity.Category;
import bymyself.pj2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


   Page<Product> findByNameContaining(String keyword, Pageable pageable);


   List<Product> findByCategoryId(Long categoryId);

   Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
