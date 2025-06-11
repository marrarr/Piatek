package com.ZamianaRadianow.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/logi")
public class LogController {
    @Autowired
    private MyLogRecordRepository logRecordRepository;

    @RequestMapping(value = "/wszystkie", method = RequestMethod.GET)
    public List<MyLogRecord> showLog()
    {
        return logRecordRepository.findAll();
    }
}
