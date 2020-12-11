package vn.vnpay.oraclejdbc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import vn.vnpay.oraclejdbc.config.RedisService;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;
import vn.vnpay.oraclejdbc.model.QrTerminal;
import vn.vnpay.oraclejdbc.service.QrTerminalService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class QrTerminalServiceImpl implements QrTerminalService {

    @Autowired
    private QrTerminalDAO qrTerminalDAO;

    @Autowired
    private RedisService redisService;


    @Override
    public List<QrTerminal> listQrTerminal(Map map) {

        try {
            Map<String, Object> result = qrTerminalDAO.getList(map);
            List<Map>  list = (List) result.get("RS");

            List<QrTerminal> qrTerminals = new ArrayList<>();
            for (Map mapResult: list){

                if (mapResult instanceof Map){
                    QrTerminal qrTerminal = new QrTerminal();
                    qrTerminal.convertQrTerminal(mapResult);
                    qrTerminals.add(qrTerminal);
                    redisService.setKey(qrTerminal.getTerminalId() + qrTerminal.getMerchantId(), qrTerminal);
                    System.out.println(qrTerminal.getTerminalId() + qrTerminal.getMerchantId());
                }
            }
            return qrTerminals;
        } catch (CloneNotSupportedException | SQLException exq) {
            return null;
        }
    }
}

