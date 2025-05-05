package supercoding.pj2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import supercoding.pj2.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

}
