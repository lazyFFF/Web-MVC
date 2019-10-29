package club.lazycoding.Impl.service;

import club.lazycoding.Impl.pojo.DemoPojo;
import DemoApi.api.DemoApi;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoService
{
  @Resource
  private DemoApi demoApi;
  
  public DemoService() {}
  
  public List<DemoPojo> demoAjax()
  {
    return demoApi.demoAjax();
  }
}
