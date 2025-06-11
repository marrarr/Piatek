package com.ZamianaRadianow.log;


//Implementacja dziennika w bazie danych

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;

@Component
public class DatabaseLoggingHandler extends Handler {

    private final MyLogRecordRepository logRecordRepository;

    @Autowired
    public DatabaseLoggingHandler(MyLogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }

    @Override
    public void publish(java.util.logging.LogRecord record) {

        if (!isLoggable(record)) {
            return;
        }

        String locLevel = record.getLevel().getName();

        MyLogRecord logRecord = new MyLogRecord();
        logRecord.setMessage(record.getMessage());

        LocalDateTime localData = LocalDateTime.now(); // Utworzenie formatera dla czasu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localData.format(formatter); // Formatowanie czasu
        logRecord.setTimestamp(formattedDateTime);
        logRecord.setLevel(locLevel);
        logRecordRepository.save(logRecord);
    }

    @Override
    public void flush() {
        // Nie wymagane
    }

    @Override
    public void close() throws SecurityException {
        // Nie wymagane
    }
}