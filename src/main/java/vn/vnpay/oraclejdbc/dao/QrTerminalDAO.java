package vn.vnpay.oraclejdbc.dao;
import java.sql.SQLException;
import java.util.Map;

public interface QrTerminalDAO {

    Map getList(Map inParams) throws SQLException, CloneNotSupportedException ;

}
