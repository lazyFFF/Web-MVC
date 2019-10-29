package DemoApi.model;

import club.lazycoding.Impl.pojo.DemoPojo;
import DemoApi.dao.DemoDao;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoModel
{
  @Resource
  private DemoDao demoDao;
  
  public DemoModel() {}
  
  public List<DemoPojo> demoAjax()
  {
    return demoDao.demoAjax();
  }
}
