package vn.vnpay.oraclejdbc.service;


import vn.vnpay.oraclejdbc.dto.PageDTO;
import vn.vnpay.oraclejdbc.dto.QrTerminalDTO;

import java.util.List;

public interface QrTerminalService {

    PageDTO searchQrTerminal(Long pageNo, Long pageSize);

    Object searchQrTerminalOnRedis(String key);
}
