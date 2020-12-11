package vn.vnpay.oraclejdbc.dao.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import vn.vnpay.oraclejdbc.dao.QrTerminalDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Map;

@Component
public class QrTerminalDAOImpl extends BaseDAO implements QrTerminalDAO {

    @Override
    public Map getList(Map inParams) throws SQLException, CloneNotSupportedException {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PKG_QUANGNV");
        Map<String, Object> map = simpleJdbcCall.withProcedureName("GET_LIST").execute();
        return map;
    }

}


//    Map<String, Object> out = simpleJdbcCall.execute(
//            new MapSqlParameterSource("order_by", by)
//                    .addValue("direction", direction)
//                    .addValue("limit_", size)
//                    .addValue("offset_", offset));