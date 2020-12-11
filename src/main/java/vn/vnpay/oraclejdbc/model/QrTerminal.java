package vn.vnpay.oraclejdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrTerminal {

    private Long id;

    private String terminalId;

    private String merchantId;

    private String terminalName;

    private String terminalAddress;

    public void convertQrTerminal(Map map) {
        setId(Long.parseLong(map.get("ID").toString()));
        setTerminalId(map.get("TERMINAL_ID").toString());
        setMerchantId(map.get("MERCHANT_ID").toString());
        setTerminalName(map.get("TERMINAL_NAME") == null ? " " : map.get("TERMINAL_NAME").toString());
        setTerminalAddress(map.get("TERMINAL_ADDRESS") == null ? " " : map.get("TERMINAL_ADDRESS").toString());
    }

}
