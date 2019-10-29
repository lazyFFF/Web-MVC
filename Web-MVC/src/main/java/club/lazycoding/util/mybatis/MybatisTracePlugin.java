package club.lazycoding.util.mybatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.cloudera.htrace.Span;
import org.cloudera.htrace.Trace;
import org.cloudera.htrace.TraceScope;

@Intercepts(value={@Signature(type=Executor.class, method="update", args={MappedStatement.class, Object.class}), @Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}), @Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisTracePlugin
        implements Interceptor {
  private static final byte[] SQL_PARAM_BYTES = "sql".getBytes();
  private static final byte[] PARAMS_PARAM_BYTES = "sql_params".getBytes();
  private static final byte[] QUERY_RESULT_PARAM_BYTES = "result_size".getBytes();
  private static final byte[] UPDATE_COUNT_PARAM_BYTES = "update_count".getBytes();
  protected boolean traceEnabled;

  public MybatisTracePlugin() {
    try {
      ResourceBundle resource = ResourceBundle.getBundle("conf.fw-trace");
      this.traceEnabled = Boolean.parseBoolean(resource.getObject("trace.sql.enabled").toString());
    }
    catch (Exception var2) {
      this.traceEnabled = false;
    }
  }


  public Object intercept(Invocation invocation) throws Throwable {
    Span parentSpan = Trace.currentSpan();
    if (parentSpan != null) {
      Object var9;
      BoundSql boundSql;
      Span currentSpan;
      MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
      Object parameter = invocation.getArgs()[1];
      TraceScope traceScope = Trace.startSpan((String)mappedStatement.getId(), (Span)parentSpan);
      byte[] resultType = null;
      int resultSize = -1;
      boolean var16 = false;
      try {
        var16 = true;
        Object result = invocation.proceed();
        if ("query".equals(invocation.getMethod().getName())) {
          resultType = QUERY_RESULT_PARAM_BYTES;
          resultSize = result == null ? 0 : ((List)result).size();
        } else {
          resultType = UPDATE_COUNT_PARAM_BYTES;
          resultSize = (Integer)result;
        }
        var9 = result;
        var16 = false;
      }
      finally {
        if (var16) {
          currentSpan = Trace.currentSpan();
          boundSql = mappedStatement.getBoundSql(parameter);
          currentSpan.addKVAnnotation(SQL_PARAM_BYTES, boundSql.getSql().getBytes());
          currentSpan.addKVAnnotation(PARAMS_PARAM_BYTES, this.getParameters(mappedStatement, boundSql, parameter).getBytes());
          if (resultType != null) {
            currentSpan.addKVAnnotation(resultType, String.valueOf(resultSize).getBytes());
          }
          traceScope.close();
        }
      }
      currentSpan = Trace.currentSpan();
      boundSql = mappedStatement.getBoundSql(parameter);
      currentSpan.addKVAnnotation(SQL_PARAM_BYTES, boundSql.getSql().getBytes());
      currentSpan.addKVAnnotation(PARAMS_PARAM_BYTES, this.getParameters(mappedStatement, boundSql, parameter).getBytes());
      if (resultType != null) {
        currentSpan.addKVAnnotation(resultType, String.valueOf(resultSize).getBytes());
      }
      traceScope.close();
      return var9;
    }
    return invocation.proceed();
  }

  public String getSqlId(MappedStatement mappedStatement) {
    return mappedStatement.getId();
  }

  public Object plugin(Object target) {
    return this.traceEnabled ? Plugin.wrap((Object)target, (Interceptor)this) : target;
  }

  public void setProperties(Properties properties) {
  }

  protected BoundSql getSql(MappedStatement mappedStatement, Object parameters) {
    return mappedStatement.getBoundSql(parameters);
  }

  public String getParameters(MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
    try {
      TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
      Configuration configuration = mappedStatement.getConfiguration();
      List parameterMappings = boundSql.getParameterMappings();
      StringBuilder builder = new StringBuilder();
      if (parameterMappings != null) {
        for (int i = 0; i < parameterMappings.size(); ++i) {
          Object value;
          ParameterMapping parameterMapping = (ParameterMapping)parameterMappings.get(i);
          if (parameterMapping.getMode() == ParameterMode.OUT) continue;
          String propertyName = parameterMapping.getProperty();
          if (boundSql.hasAdditionalParameter(propertyName)) {
            value = boundSql.getAdditionalParameter(propertyName);
          } else if (parameterObject == null) {
            value = null;
          } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            value = parameterObject;
          } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            value = metaObject.getValue(propertyName);
          }
          if (i != 0) {
            builder.append(", ");
          }
          if (value instanceof Enum) {
            value = ((Enum)value).ordinal();
          }
          builder.append(value);
        }
      }
      return builder.toString();
    }
    catch (Exception var13) {
      return "Exception occured when getting parameters!";
    }
  }
}
