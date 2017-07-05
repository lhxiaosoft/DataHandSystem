package edu.csu.utils;



/**
 * SQLSERVER 方言
 * @author yz 2016-1-8 20:47:07
 *
 */
// 原型地址：http://opensource.atlassian.com/projects/hibernate/browse/HHH-2655
public class SQLServerDialect {



/**
 * 
 * @param sql 原始的sql
 * @param page 当前页面
 * @param row	页面偏移量，即每页显示多少条数据
 * @return 改造过的SQL
 */
    public static String getLimitString(String sql, int page,int row) {
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(sql);
		String distinctStr = "";

		String loweredString = sql.toLowerCase();
		//String sqlPartString = sql;
		if (loweredString.contains("union")) {//如果遇到union，则再包一层
			loweredString = "select * from("+loweredString+" )as tt";
		}
		if (loweredString.trim().startsWith("select")) {//判断是否有去重语句
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			loweredString = loweredString.substring(index);
		}
		pagingBuilder.append(loweredString);
		
		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		int preNum = row*(page-1);//头数
		int afNum = page*row;//尾数
		result.append("WITH query AS (SELECT ")
				.append(distinctStr)
				.append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (")
				.append(orderby)
				.append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ >")
				.append(preNum)
				.append(" AND __row_number__ <= ")
				.append(afNum)
				.append(" ORDER BY __row_number__");
		return result.toString();
	}
    /**
     * 将原来的sql改造成查询总条数的sql
     * @param sql
     * @return
     */
    public static String getCount(String sql)
    {
    	String loweredString = sql.toLowerCase();//转为小写
    	int orderByIndex = loweredString.indexOf("order by");//拿到order by
    	loweredString =orderByIndex==-1?loweredString:loweredString.substring(0, orderByIndex);//如果有order by则去掉order by

    	if (loweredString.contains("union")) {//如果是union则，直接在原来的sql上加
    		loweredString = "select count(1) from("+loweredString+" )as tt";
    		return loweredString;
    	}
    	if (loweredString.trim().startsWith("select")) { //将select到from之间的列名替换
			int index = 6;
			int FirstFrom = loweredString.indexOf("from");
			 String columName = loweredString.substring(index, FirstFrom);//拿到所有列名
			 return loweredString.replace(columName, " COUNT(1) ");//替换成count(1)
		}
    	return "";
    }
    
    /**
     * 拿到order by后面的语句
     * @param sql 原始sql
     * @return
     */
	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}
}
