package main.java.com.ll.exam.ebook.app.cart.repository;


import com.ll.exam.eBook.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByBuyerIdAndProductId(long buyerId, long productId);

    boolean existsByBuyerIdAndProductId(long buyerId, long productId);

    List<CartItem> findAllByBuyerId(long buyerId);
}