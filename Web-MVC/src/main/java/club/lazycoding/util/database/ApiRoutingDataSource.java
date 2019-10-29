package club.lazycoding.util.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ApiRoutingDataSource
  extends AbstractRoutingDataSource
{
  public static final ThreadLocal<String> lookupKey = new ThreadLocal();
  
  public ApiRoutingDataSource() {}
  
  protected Object determineCurrentLookupKey()
  {
    String connType = (String)lookupKey.get();
    lookupKey.remove();
    return connType;
  }
}
