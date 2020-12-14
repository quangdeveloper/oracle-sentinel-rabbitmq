package vn.vnpay.oraclejdbc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.oraclejdbc.config.RedisService;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;
import vn.vnpay.oraclejdbc.service.QrTerminalService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class QrTerminalController {

    Logger logger = LoggerFactory.getLogger(QrTerminalController.class);

    @Autowired
    private QrTerminalService qrTerminalService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/qr-terminal")
    public ResponseEntity<Object> findAll(@RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize) {

        Map inParams = new HashMap();
        inParams.put("PI_PAGE_NO", pageNo);
        inParams.put("PI_PAGE_SIZE", pageSize);
        return ResponseEntity.ok(qrTerminalService.listQrTerminal(inParams));
    }

    @GetMapping("/getInRedis/{key}")
    public ResponseEntity<Object> findInRedis(@PathVariable("key") String key){
        return ResponseEntity.ok(redisService.getKey(key));

    }



}
