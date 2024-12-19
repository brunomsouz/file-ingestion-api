package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.*;
import io.souz.fileingestionapi.exception.InternalServerError;
import io.souz.fileingestionapi.repository.ProductRepository;
import io.souz.fileingestionapi.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class FileIngestionService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public FileIngestionService(UserRepository userRepository,
                                ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void processFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            Map<Long, User> userMap = new HashMap<>();

            reader.lines()
                    .forEach(line -> {
                        FileRecord fileRecord = new FileRecord(line);
                        Long userId = fileRecord.getUserId();
                        Long orderId = fileRecord.getOrderId();
                        Long productId = fileRecord.getProductId();

                        User user = userMap.computeIfAbsent(userId, id -> {
                            String username = fileRecord.getName();
                            return new User(id, username);
                        });

                        Order order = user.getOrders().stream()
                                .filter(o -> o.getId().equals(orderId))
                                .findFirst()
                                .orElseGet(() -> {
                                    Order newOrder = new Order(orderId, fileRecord.getDate(), BigDecimal.ZERO);
                                    user.addOrder(newOrder);
                                    return newOrder;
                                });

                        order.setTotal(order.getTotal().add(fileRecord.getProductValue()));

                        Product product = this.productRepository.findProductById(productId)
                                .orElseGet(() -> this.productRepository.save(new Product(productId)));

                        OrderProduct orderProduct = order.getProducts().stream()
                                .filter(op -> op.getProduct().getId().equals(productId))
                                .findFirst()
                                .orElseGet(() -> {
                                    OrderProduct newOrderProduct = new OrderProduct(order, product, BigDecimal.ZERO);
                                    order.addProduct(newOrderProduct);
                                    return newOrderProduct;
                                });

                        orderProduct.setValue(orderProduct.getValue().add(fileRecord.getProductValue()));
                    });

            this.userRepository.saveAll(userMap.values());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerError("file.ingestion.error");
        }
    }

}
