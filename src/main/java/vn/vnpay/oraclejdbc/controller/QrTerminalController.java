package vn.vnpay.oraclejdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.oraclejdbc.config.RedisService;
import vn.vnpay.oraclejdbc.dto.ResponseDTO;
import vn.vnpay.oraclejdbc.service.QrTerminalService;
import vn.vnpay.oraclejdbc.util.Constant;

@RestController
@RequestMapping("/qr-terminal")
public class QrTerminalController {

    @Autowired
    private QrTerminalService qrTerminalService;

    @GetMapping()
    public ResponseEntity<Object> searchQrTerminal(@RequestParam Long pageNo, @RequestParam Long pageSize) {

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .value(qrTerminalService.searchQrTerminal(pageNo, pageSize))
                        .build());
    }

    @GetMapping("/{key}")
    public ResponseEntity<Object> getQrTerminalOnRedisByKey(@PathVariable("key") String key) {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .code(Constant.RESPONSE.CODE.OK)
                        .message(Constant.RESPONSE.MESSAGE.OK)
                        .value(qrTerminalService.searchQrTerminalOnRedis(key))
                        .build());

    }
}
