package com.anur.pagehelper.page;

/**
 * Created by Anur IjuoKaruKas on 2018/6/8
 */
public class PageSqlResolver {

    public static final String SQL_SIGN = "AS limitable";

    public static final String LIMIT_SIGN = "LIMIT ?";

    public static final String LIMIT_SIGN_EX = "LIMIT ?, ?";

    public static String resolveLimit(String pageSql) {
        if (pageSql == null) {
            return null;
        }

        if (pageSql
            .contains(SQL_SIGN)) {// 如果需要特殊分页

            String changer = "";

            if (pageSql.contains(LIMIT_SIGN_EX)) {
                changer = LIMIT_SIGN_EX;
            } else if (pageSql.contains(LIMIT_SIGN)) {
                changer = LIMIT_SIGN;
            }

            pageSql = pageSql.replace(changer, "");
            StringBuilder sqlBuilder = new StringBuilder(pageSql);

            StringBuilder mae = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.indexOf(SQL_SIGN)));// mae 截止sql语句到 limitable
            StringBuilder uShiRo = new StringBuilder(sqlBuilder.substring(sqlBuilder.indexOf(SQL_SIGN), sqlBuilder.length()));// 剩余的

            mae.insert(mae.lastIndexOf(")"), String.format(" %s", changer));

            return mae.append(uShiRo)
                      .toString();
        } else {
            return pageSql;
        }
    }
}
