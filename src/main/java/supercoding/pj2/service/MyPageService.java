package supercoding.pj2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supercoding.pj2.dto.request.UserRequestDto;
import supercoding.pj2.dto.response.RecentOrderDto;
import supercoding.pj2.dto.response.UserResponseDto;
import supercoding.pj2.entity.Order;
import supercoding.pj2.entity.OrderItem;
import supercoding.pj2.entity.User;
import supercoding.pj2.exception.NotFoundException;
import supercoding.pj2.repository.OrderItemRepository;
import supercoding.pj2.repository.OrderRepository;
import supercoding.pj2.repository.ProductRepository;
import supercoding.pj2.repository.UserRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    //내 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        //가장 최근 주문 1건 조회
        Order recentOrder = orderRepository.findTopByUserIdOrderByCreatedAtDesc(userId).orElse(null);

        RecentOrderDto recentOrderDto = null;

        if (recentOrder != null) {
            List<OrderItem> orderItemList = orderItemRepository.findByOrderId(recentOrder.getId());

            if (!orderItemList.isEmpty()) {
                OrderItem orderItem = orderItemList.get(0); //첫 상품만 표시

                recentOrderDto = RecentOrderDto.builder()
                        .productName(orderItem.getName())
                        .price(orderItem.getPrice())
                        .color(orderItem.getColor())
                        .imageUrl(orderItem.getImageUrl())
                        .size(orderItem.getSize())
                        .quantity(orderItem.getQuantity())
                        .build();
            }
        }
        return UserResponseDto.builder()
                .email(user.getEmail())
                .shippingAddress(user.getAddress())
                .recentOrderDto(recentOrderDto)
                .build();
    }

    //내 정보 수정
    @Transactional
    public void updateMyInfo(Long userId, UserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        user.update(dto);
    }

    //페이 조회
    @Transactional(readOnly = true)
    public Long getPayBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다"));
        return user.getBalance();
    }

    //페이 충전
    @Transactional
    public void chargePay(Long userId, Long amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다"));
            user.chargeBalance(amount);
    }

}
