package club.lazycoding.util.json.util;

import club.lazycoding.util.json.JsonMap;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeanUtils;



public final class JsonUtils
{
  private static final String[] jsonProperties;
  
  static
  {
    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(JsonRequestUtil.class);
    jsonProperties = new String[pds.length];
    for (int i = 0; i < pds.length; i++) {
      jsonProperties[i] = pds[i].getName();
    }
  }
  
  public static JsonMap getJsonMap(Integer start, Integer length, Integer total)
  {
    JsonMap jsonMap = new JsonMap();
    jsonMap.setStart(start);
    jsonMap.setLength(length);
    jsonMap.setTotal(total);
    jsonMap.setJsonData(new ArrayList());
    return jsonMap;
  }
  
  public static <T extends Serializable> void setListToJson(JsonMap jsonMap, List<T> pojoList)
  {
    writeJsonMap(jsonMap.getJsonData(), pojoList);
  }
  
  private static <T> void writeJsonMap(List<HashMap<String, Object>> objectMapList, List<T> pojoList)
  {
    PropertyDescriptor[] sourcePds;
    if (pojoList != null) {
      sourcePds = BeanUtils.getPropertyDescriptors(pojoList.get(0).getClass());
      for (T t : pojoList) {
        HashMap<String, Object> entityMap = new HashMap();
        for (PropertyDescriptor sourcePd : sourcePds) {
          if (!"class".equals(sourcePd.getName()))
          {

            Method readMethod = sourcePd.getReadMethod();
            if (readMethod != null)
              try {
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                  readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(t, new Object[0]);
                entityMap.put(sourcePd.getName(), value);
              }
              catch (Throwable ex) {
                throw new RuntimeException(ex.getMessage(), ex);
              }
          }
        }
        objectMapList.add(entityMap);
      }
    }
  }
  
  private JsonUtils() {}
}
