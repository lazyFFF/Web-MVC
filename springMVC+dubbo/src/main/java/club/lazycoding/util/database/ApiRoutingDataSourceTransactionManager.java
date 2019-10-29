package club.lazycoding.util.database;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;


public class ApiRoutingDataSourceTransactionManager
  extends DataSourceTransactionManager
{
  private static final long serialVersionUID = 1L;
  
  public ApiRoutingDataSourceTransactionManager() {}
  
  protected void doBegin(Object transaction, TransactionDefinition definition)
  {
    Boolean isReadOnly = Boolean.valueOf(definition.isReadOnly());
    if (isReadOnly.booleanValue()) {
      ApiRoutingDataSource.lookupKey.set("read");
    } else {
      ApiRoutingDataSource.lookupKey.set("write");
    }
    
    super.doBegin(transaction, definition);
  }
}
