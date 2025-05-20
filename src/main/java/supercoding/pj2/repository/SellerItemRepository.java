package supercoding.pj2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supercoding.pj2.entity.SellerItem;

import java.util.List;
@Repository
public interface SellerItemRepository extends JpaRepository<SellerItem, Long> {
    List<SellerItem> findByUserId(Long userId);

}
