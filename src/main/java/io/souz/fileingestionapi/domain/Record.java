package io.souz.fileingestionapi.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class Record {

    private final Long userId;
    private final String name;
    private final Long orderId;
    private final Long productId;
    private final BigDecimal productValue;
    private final LocalDate date;

    public Record(String data) {
        String[] fields = data.split("\\s{2,}");

        this.userId = Long.parseLong(fields[0]);

        String secondField = fields[1];
        int len = secondField.length();
        this.productId = Long.parseLong(secondField.substring(len - 10));
        this.orderId = Long.parseLong(secondField.substring(len - 20, len - 10));
        this.name = secondField.substring(0, len - 20);

        String thirdField = fields[2];
        len = thirdField.length();
        this.productValue = new BigDecimal(thirdField.substring(0, len - 8));
        this.date = LocalDate.parse(thirdField.substring(len - 8), DateTimeFormatter.BASIC_ISO_DATE);
    }

}
