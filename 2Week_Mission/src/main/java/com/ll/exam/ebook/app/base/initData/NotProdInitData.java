package main.java.com.ll.exam.ebook.app.base.initData;
import main.java.com.ll.exam.eBook.app.cart.service.CartService;
import main.java.com.ll.exam.ebook.app.member.entity.Member;
import main.java.com.ll.exam.ebook.app.member.service.MemberService;
import main.java.com.ll.exam.eBook.app.order.entity.Order;
import main.java.com.ll.exam.eBook.app.order.service.OrderService;
import main.java.com.ll.exam.ebook.app.post.service.PostService;
import main.java.com.ll.exam.ebook.app.product.entity.Product;
import main.java.com.ll.exam.ebook.app.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile({"dev", "test"})
public class NotProdInitData {
    private boolean initDataDone = false;

    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            PostService postService,
            ProductService productService,
            artService cartService,
            OrderService orderService
    ) {
        return args -> {
            if (initDataDone) {
                return;
            }
            class Helper {
                public Order order(Member member, List<Product> products) {
                    for (int i = 0; i < products.size(); i++) {
                        Product product = products.get(i);

                        cartService.addItem(member, product);
                    }

                    return orderService.createFromCart(member);
                }
            }

            Helper helper = new Helper();

            initDataDone = true;

            Member member1 = memberService.join("user1", "1234", "user1@test.com", null);
            Member member2 = memberService.join("user2", "1234", "user2@test.com", "홍길순");

            postService.write(
                    member1,

            );

            postService.write(
                    member1,
                    .stripIndent(),

            );

            postService.write(member2, "제목 3", "내용 3", "내용 3", "#IT# 프론트엔드 #HTML #CSS");
            postService.write(member2, "제목 4", "내용 4", "내용 4", "#IT #스프링부트 #자바");
            postService.write(member1, "제목 5", "내용 5", "내용 5", "#IT #자바 #카프카");
            postService.write(member1, "제목 6", "내용 6", "내용 6", "#IT #프론트엔드 #REACT");
            postService.write(member2, "제목 7", "내용 7", "내용 7", "#IT# 프론트엔드 #HTML #CSS");
            postService.write(member2, "제목 8", "내용 8", "내용 8", "#IT #스프링부트 #자바");

            Product product1 = productService.create(member1, "상품명1 상품명1 상품명1 상품명1 상품명1 상품명1", 30_000, "카프카", "#IT #카프카");
            Product product2 = productService.create(member2, "상품명2", 40_000, "스프링부트", "#IT #REACT");
            Product product3 = productService.create(member1, "상품명3", 50_000, "REACT", "#IT #REACT");
            Product product4 = productService.create(member2, "상품명4", 60_000, "HTML", "#IT #HTML");

            memberService.addCash(member1, 10_000, "충전__무통장입금");
            memberService.addCash(member1, 20_000, "충전__무통장입금");
            memberService.addCash(member1, -5_000, "출금__일반");
            memberService.addCash(member1, 1_000_000, "충전__무통장입금");

            memberService.addCash(member2, 2_000_000, "충전__무통장입금");

            // 결제완료
            Order order1 = helper.order(member1, Arrays.asList(
                            product1,
                            product2
                    )
            );

            int order1PayPrice = order1.calculatePayPrice();
            orderService.payByRestCashOnly(order1);

            // 결제 후 환불
            Order order2 = helper.order(member2, Arrays.asList(
                            product3,
                            product4
                    )
            );

            orderService.payByRestCashOnly(order2);

            orderService.refund(order2);

            // 결제 전
            Order order3 = helper.order(member2, Arrays.asList(
                            product1,
                            product2
                    )
            );

            cartService.addItem(member1, product1);
            cartService.addItem(member1, product2);

            cartService.addItem(member2, product3);
            cartService.addItem(member2, product4);

        };
    }
}
