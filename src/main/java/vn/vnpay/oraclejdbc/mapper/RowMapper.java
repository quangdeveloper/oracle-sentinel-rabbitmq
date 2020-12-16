package vn.vnpay.oraclejdbc.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T mapRow(ResultSet resultSet);
}
