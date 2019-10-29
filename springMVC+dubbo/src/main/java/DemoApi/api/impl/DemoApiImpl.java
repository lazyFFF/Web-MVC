package DemoApi.api.impl;

import club.lazycoding.Impl.pojo.DemoPojo;
import DemoApi.api.DemoApi;
import DemoApi.model.DemoModel;
import java.util.List;
import javax.annotation.Resource;

public class DemoApiImpl
  implements DemoApi
{
  @Resource
  private DemoModel demoModel;
  
  public DemoApiImpl() {}
  
  public List<DemoPojo> demoAjax()
  {
    return demoModel.demoAjax();
  }
}
