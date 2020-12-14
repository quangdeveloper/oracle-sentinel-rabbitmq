package vn.vnpay.oraclejdbc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.Map;

public class BaseDAO{

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected DataSource dataSource;

    protected final String PK_TEST = "PKG_QUANGNV";


    protected Map<String, Object> callFunction(String pack, String func, Map inPamram){
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(pack);
        Map<String, Object> map = simpleJdbcCall.withFunctionName(func).execute(inPamram);
        return map;
    }

    protected Map<String, Object> callProcedure(String pack, String func, Map inPamram){
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName(pack);
        Map<String, Object> map = simpleJdbcCall.withProcedureName(func).execute(inPamram);
        return map;
    }



}
