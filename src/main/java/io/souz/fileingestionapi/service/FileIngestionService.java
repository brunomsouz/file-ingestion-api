package io.souz.fileingestionapi.service;

import io.souz.fileingestionapi.domain.Record;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            List<Record> recordList = reader.lines()
                    .map(Record::new)
                    .toList();

            this.transformData(recordList);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerError("file.ingestion.error");
        }
    }

    private void transformData(List<Record> recordList) {
        Map<Long, Map<Long, Map<Long, List<Record>>>> groupedRecords = recordList.stream()
                .collect(Collectors.groupingBy(Record::getUserId, Collectors.groupingBy(Record::getOrderId, Collectors.groupingBy(Record::getProductId))));

        groupedRecords.forEach((key, ordersMap) -> {
            long userId = key;

            User user = this.userRepository.findUserById(key)
                    .orElseGet(() -> {
                        String username = ordersMap.values().stream()
                                .flatMap(map -> map.values().stream())
                                .flatMap(List::stream)
                                .findFirst()
                                .orElseThrow()
                                .getName();

                        return new User(userId, username);
                    });

            Set<Order> orders = ordersMap.entrySet().stream()
                    .map(orderEntry -> {
                        long orderId = orderEntry.getKey();
                        Map<Long, List<Record>> productRecords = orderEntry.getValue();

                        BigDecimal orderTotal = productRecords.values().stream()
                                .flatMap(List::stream)
                                .map(Record::getProductValue)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        LocalDate date = productRecords.values().stream()
                                .flatMap(List::stream)
                                .findFirst()
                                .orElseThrow()
                                .getDate();

                        Order order = new Order(orderId, date, orderTotal);

                        Set<OrderProduct> orderProducts = productRecords.entrySet().stream()
                                .map(r -> {
                                    long productId = r.getKey();
                                    BigDecimal productTotal = r.getValue().stream().map(Record::getProductValue).reduce(BigDecimal.ZERO, BigDecimal::add);

                                    return this.productRepository.findProductById(productId)
                                            .map((product) -> new OrderProduct(order, product, productTotal))
                                            .orElseGet(() -> {
                                                // todo analyze if this can be improved
                                                Product product = this.productRepository.save(new Product(productId));
                                                return new OrderProduct(order, product, productTotal);
                                            });
                                })
                                .collect(Collectors.toSet());

                        order.addProducts(orderProducts);
                        return order;
                    })
                    .collect(Collectors.toSet());

            user.addOrders(orders);
            this.userRepository.save(user);
        });
    }

}
