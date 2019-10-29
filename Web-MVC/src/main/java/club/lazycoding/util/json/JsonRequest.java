package club.lazycoding.util.json;

import java.io.Serializable;
import java.util.Map;


public class JsonRequest
  implements Serializable
{
  private int start;
  private int length;
  private Map<String, Serializable> parameters;
  
  public JsonRequest() {}
  
  public int getStart()
  {
    return start;
  }
  





  public void setStart(int start)
  {
    this.start = start;
  }
  




  public int getLength()
  {
    return length;
  }
  





  public void setLength(int length)
  {
    this.length = length;
  }
  




  public Map<String, Serializable> getParameters()
  {
    return parameters;
  }
  





  public void setParameters(Map<String, Serializable> parameters)
  {
    this.parameters = parameters;
  }
}
