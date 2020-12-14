package vn.vnpay.oraclejdbc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import vn.vnpay.oraclejdbc.config.RedisService;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;
import vn.vnpay.oraclejdbc.model.QrTerminal;
import vn.vnpay.oraclejdbc.service.QrTerminalService;
import vn.vnpay.oraclejdbc.util.GsonUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QrTerminalServiceImpl implements QrTerminalService {

    @Autowired
    private QrTerminalDAO qrTerminalDAO;

    @Autowired
    private RedisService redisService;


    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Value("${rabbitMQ.topicExchangeName}")
//    private String topicExchangeName;
//
//
//    @Value("${rabbitMQ.routingKeyOne}")
//    private String routingKeyOne;


    @Override
    public List<QrTerminal> listQrTerminal(Map map) {

        try {
            Map<String, Object> result = qrTerminalDAO.getList(map);
            List<Map> list = (List) result.get("RS");

            List<QrTerminal> qrTerminals = new ArrayList<>();
            for (Map mapResult : list) {

                if (mapResult instanceof Map) {
                    QrTerminal qrTerminal = new QrTerminal();
                    qrTerminal.convertQrTerminal(mapResult);
                    qrTerminals.add(qrTerminal);
                    redisService.setKey(qrTerminal.getTerminalId() + qrTerminal.getMerchantId(), qrTerminal);
                    System.out.println(qrTerminal.getTerminalId() + qrTerminal.getMerchantId());

                    int i = 0;
                    while (i < 3 && !redisService.hasKey(qrTerminal.getTerminalId() + qrTerminal.getMerchantId())) {
                        redisService.setKey(qrTerminal.getTerminalId() + qrTerminal.getMerchantId(), qrTerminal);
                        i++;
                    }

                    if (redisService.hasKey(qrTerminal.getTerminalId() + qrTerminal.getMerchantId())) {

                        //su dung cho exchange type: direct, fanout, topicExchange
                        rabbitTemplate.convertAndSend("exchange-one", "routing.one", GsonUtil.toJson(qrTerminal));

                        // su dung cho exchange type: header
//                        MessageProperties messageProperties = new MessageProperties();
//                        messageProperties.setHeader("name-control","yourself");
//                        MessageConverter messageConverter = new SimpleMessageConverter();
//                        Message message = messageConverter.toMessage(qrTerminal, messageProperties);
//                        rabbitTemplate.send(message);
                    }
                }
            }
            return qrTerminals;
        } catch (CloneNotSupportedException | SQLException exq) {
            return null;
        }
    }
}

