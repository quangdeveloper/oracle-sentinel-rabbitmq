package vn.vnpay.oraclejdbc.dao.impl;

import org.springframework.stereotype.Component;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;
import java.sql.SQLException;
import java.util.Map;

@Component
public class QrTerminalDAOImpl extends BaseDAO implements QrTerminalDAO {

    @Override
    public Map getList(Map inParams) throws SQLException, CloneNotSupportedException {

        Map<String, Object> map = callProcedure(PK_TEST, "GET_LIST2", inParams);
        return map;
    }
}


//    Map<String, Object> out = simpleJdbcCall.execute(
//            new MapSqlParameterSource("order_by", by)
//                    .addValue("direction", direction)
//                    .addValue("limit_", size)
//                    .addValue("offset_", offset));