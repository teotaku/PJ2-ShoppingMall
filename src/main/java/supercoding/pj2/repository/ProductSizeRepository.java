package supercoding.pj2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import supercoding.pj2.entity.ProductSize;

import java.util.List;
import java.util.Optional;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    Optional<ProductSize> findByProductIdAndSize(Long productId, String size);

    List<ProductSize> findByProductId(Long id);
}
