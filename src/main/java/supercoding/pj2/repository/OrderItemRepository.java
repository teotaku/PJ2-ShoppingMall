package supercoding.pj2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supercoding.pj2.entity.OrderItem;

import java.util.List;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrderIdIn(List<Long> orderIds);
    List<OrderItem> findByOrderId(Long orderId);

}
