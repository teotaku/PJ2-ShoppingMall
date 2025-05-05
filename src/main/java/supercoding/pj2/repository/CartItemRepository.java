package supercoding.pj2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import supercoding.pj2.entity.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    Page<CartItem> findByCartId(Long cartId, Pageable pageable);

    List<CartItem> findByCartId(Long cartId);
}
