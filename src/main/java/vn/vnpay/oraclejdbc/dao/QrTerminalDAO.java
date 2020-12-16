package vn.vnpay.oraclejdbc.dao;
import java.sql.SQLException;
import java.util.Map;

public interface QrTerminalDAO {

    Map<String, Object> searchQrTerminalByCondition(Map<String, Object> inParams);

}
