package club.lazycoding.util.json;

import java.io.Serializable;
import java.util.List;


public class JsonPojo<T extends Serializable>
  implements Serializable
{
  private Integer start;
  private Integer length;
  private Integer total;
  private List<T> jsonData;
  
  public JsonPojo() {}
  
  public Integer getStart()
  {
    return start;
  }
  
  public void setStart(Integer start) {
    this.start = start;
  }
  
  public Integer getLength() {
    return length;
  }
  
  public void setLength(Integer length) {
    this.length = length;
  }
  
  public Integer getTotal() {
    return total;
  }
  
  public void setTotal(Integer total) {
    this.total = total;
  }
  
  public List<T> getJsonData()
  {
    return jsonData;
  }
  
  public void setJsonData(List<T> jsonData)
  {
    this.jsonData = jsonData;
  }
}
