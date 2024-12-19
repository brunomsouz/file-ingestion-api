package io.souz.fileingestionapi.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileRecordTest {

    @Test
    void createRecordWithValidData() {
        String data = "0000000001                              Sammie Baumbach00000000060000000003      479.0420210519";
        FileRecord fileRecord = new FileRecord(data);

        assertEquals(1L, fileRecord.getUserId());
        assertEquals("Sammie Baumbach", fileRecord.getName());
        assertEquals(6L, fileRecord.getOrderId());
        assertEquals(3L, fileRecord.getProductId());
        assertEquals(new BigDecimal("479.04"), fileRecord.getProductValue());
        assertEquals(LocalDate.of(2021, 5, 19), fileRecord.getDate());
    }

}