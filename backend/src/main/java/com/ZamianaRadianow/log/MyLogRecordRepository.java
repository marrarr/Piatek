package com.ZamianaRadianow.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLogRecordRepository extends JpaRepository<MyLogRecord, Long> {
}
