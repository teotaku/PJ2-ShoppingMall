package supercoding.pj2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supercoding.pj2.entity.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    Page<CartItem> findByCartId(Long cartId, Pageable pageable);

    List<CartItem> findByCartId(Long cartId);
}
