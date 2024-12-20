package io.souz.fileingestionapi.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class FileRecord {

    private final Long userId;
    private final String name;
    private final Long orderId;
    private final Long productId;
    private final BigDecimal productValue;
    private final LocalDate date;

    public FileRecord(String data) {
        String[] fields = data.split("\\s{2,}");

        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid data format");
        }

        this.userId = parseLong(fields[0]);

        // second field should be at least 21 digits long: 10 for productId, 10 for orderId and the remaining for username
        String secondField = fields[1];
        int len = secondField.length();
        if (len < 21) {
            throw new IllegalArgumentException("Invalid second field format");
        }

        this.productId = parseLong(secondField.substring(len - 10));
        this.orderId = parseLong(secondField.substring(len - 20, len - 10));
        this.name = secondField.substring(0, len - 20).trim();

        // third field should be at least 12 digits long: 8 for the date and at least 4 for the value 0.00
        String thirdField = fields[2];
        len = thirdField.length();
        if (len < 12) {
            throw new IllegalArgumentException("Invalid third field format");
        }

        this.productValue = parseBigDecimal(thirdField.substring(0, len - 8));
        this.date = parseDate(thirdField.substring(len - 8));
    }

    private Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid long string: " + value);
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid BigDecimal string: " + value);
        }
    }

    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid date string: " + value);
        }
    }
}
