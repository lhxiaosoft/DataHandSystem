package edu.csu.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;

import edu.csu.model.BaseModel;
import edu.csu.utils.SQLServerDialect;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("已经进入拦截器...");
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler);
		MappedStatement mappedStatement = (MappedStatement) metaObject
				.getValue("delegate.mappedStatement");

		// 配置文件中SQL语句的ID
		String id = mappedStatement.getId();
		
		if (id.contains("ByList")) {// 只有以ByList结尾的才可以排序
			System.out.println("拦截函数:" + id);
			BoundSql boundSql = statementHandler.getBoundSql();// SQL包装类
			// 原始的sql
			String sql = boundSql.getSql();
			System.out.println("原始的SQL:"+sql);
			String countSql = SQLServerDialect.getCount(sql);
			System.out.println("原始SQL总条数:" + countSql);
			Connection connection = (Connection) invocation.getArgs()[0];// 拿到连接类
			PreparedStatement countsStatement = connection
					.prepareStatement(countSql);
			ParameterHandler parameterHandler = (ParameterHandler) metaObject
					.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(countsStatement);
			ResultSet rs = countsStatement.executeQuery();
			BaseModel parameter = (BaseModel) boundSql.getParameterObject();// 拿到所有的积累
			if (rs.next()) {
				parameter.setCount(rs.getInt(1));// 将总条数设置到count中
				System.out.println("所以查出来的结果是：" + parameter.getCount());
			}
			Integer rows = parameter.getRows();
			Integer page = parameter.getPage();
			// 修改SQL
			String modifiedSql = SQLServerDialect.getLimitString(sql, page,
					rows);
			System.out.println("修改过的SQL为：" + modifiedSql);
			metaObject.setValue("delegate.boundSql.sql", modifiedSql);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);// 设置过滤点
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
