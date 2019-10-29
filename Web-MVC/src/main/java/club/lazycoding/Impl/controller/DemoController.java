package club.lazycoding.Impl.controller;

import club.lazycoding.Impl.pojo.DemoPojo;
import club.lazycoding.Impl.service.DemoService;
import club.lazycoding.util.json.JsonMap;
import club.lazycoding.util.json.util.JsonUtils;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class DemoController
{
  @Resource
  private DemoService demoService;
  
  public DemoController() {}

  @RequestMapping(value={"/demo1.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  public String demoPage()
  {
    return "demo/demo1";
  }
  

  @ResponseBody
  @RequestMapping(value={"/demoAjax.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
  public JsonMap demoAjax()
  {
    JsonMap jsonMap = JsonUtils.getJsonMap(0, 1, 1);
    List<DemoPojo> demoList = demoService.demoAjax();
    JsonUtils.setListToJson(jsonMap, demoList);
    
    return jsonMap;
  }

}
