package vn.vnpay.oraclejdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.oraclejdbc.config.RedisService;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;
import vn.vnpay.oraclejdbc.service.QrTerminalService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class QrTerminalController {


    @Autowired
    private QrTerminalService qrTerminalService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/qr-terminal")
    public ResponseEntity<Object> findAll() {

        Map inParams = new HashMap();
        inParams.put("pi_pageNo", 1);
        inParams.put("pi_pageSize", 10);

        return ResponseEntity.ok(qrTerminalService.listQrTerminal(inParams));
    }

    @GetMapping("/getInRedis/{key}")
    public ResponseEntity<Object> findInRedis(@PathVariable("key") String key){
        return ResponseEntity.ok(redisService.getKey(key));

    }



}
