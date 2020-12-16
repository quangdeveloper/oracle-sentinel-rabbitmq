package vn.vnpay.oraclejdbc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.vnpay.oraclejdbc.config.RedisService;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;
import vn.vnpay.oraclejdbc.dto.PageDTO;
import vn.vnpay.oraclejdbc.exception.GeneralException;
import vn.vnpay.oraclejdbc.dto.QrTerminalDTO;
import vn.vnpay.oraclejdbc.service.QrTerminalService;
import vn.vnpay.oraclejdbc.util.Constant;
import vn.vnpay.oraclejdbc.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QrTerminalServiceImpl implements QrTerminalService {

    private final Logger logger = LoggerFactory.getLogger(QrTerminalServiceImpl.class);

    @Value("${rabbitMQ.toppicExchangeName}")
    private String toppicExchangeName;

    @Value("${rabbitMQ.routingKeyOne}")
    private String routingKeyOne;

    @Autowired
    private QrTerminalDAO qrTerminalDAO;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public PageDTO searchQrTerminal(Long pageNo, Long pageSize) {

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("PI_PAGE_NO", pageNo);
        inParams.put("PI_PAGE_SIZE", pageSize);

        Map<String, Object> result = qrTerminalDAO.searchQrTerminalByCondition(inParams);

        List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("RS");
        final long totalResult = Long.parseLong(result.get("PO_TOTAL").toString());

        List<QrTerminalDTO> qrTerminalDTOS = new ArrayList<>();
        for (Map<String, Object> mapResult : list) {

            QrTerminalDTO qrTerminalDTO = new QrTerminalDTO();
            qrTerminalDTO.convertQrTerminal(mapResult);
            qrTerminalDTOS.add(qrTerminalDTO);

            redisService.setKey(qrTerminalDTO.getTerminalId() + qrTerminalDTO.getMerchantId(), qrTerminalDTO);

            int i = 0;
            while (i < 3 && !redisService.hasKey(qrTerminalDTO.getTerminalId() + qrTerminalDTO.getMerchantId())) {
                redisService.setKey(qrTerminalDTO.getTerminalId() + qrTerminalDTO.getMerchantId(), qrTerminalDTO);
                i++;
            }

            if (redisService.hasKey(qrTerminalDTO.getTerminalId() + qrTerminalDTO.getMerchantId())) {
                //su dung cho exchange type: direct, fanout, topicExchange
                rabbitTemplate.convertAndSend(toppicExchangeName, routingKeyOne, GsonUtil.toJson(qrTerminalDTO));
            }
        }
        return PageDTO.builder()
                .total(totalResult)
                .content(qrTerminalDTOS)
                .build();
    }

    @Override
    public Object searchQrTerminalOnRedis(String key) {

        if (!redisService.hasKey(key)) {
            throw new GeneralException(Constant.RESPONSE.CODE.C404, Constant.RESPONSE.MESSAGE.DATA_NOT_EXISTS);
        }

        QrTerminalDTO qrTerminalDTO = GsonUtil.fromJson(redisService.getKey(key).toString(),QrTerminalDTO.class);
        return PageDTO.builder()
                .total(1L)
                .content(qrTerminalDTO)
                .build();
    }
}


// su dung cho exchange type: header
//                        MessageProperties messageProperties = new MessageProperties();
//                        messageProperties.setHeader("name-control","yourself");
//                        MessageConverter messageConverter = new SimpleMessageConverter();
//                        Message message = messageConverter.toMessage(qrTerminal, messageProperties);
//                        rabbitTemplate.send(message);