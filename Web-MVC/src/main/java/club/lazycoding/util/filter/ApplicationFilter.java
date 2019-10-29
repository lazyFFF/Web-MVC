package club.lazycoding.util.filter;

import club.lazycoding.util.filter.support.ParameterRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ApplicationFilter
  extends HttpServlet
  implements Filter
{
  private String errorUrl = "";
  private String illegalUrl = "";
  private static String illegalCharacter = "";

  public void init(FilterConfig fConfig) throws ServletException
  {
    errorUrl = fConfig.getInitParameter("errorUrl");
    illegalUrl = fConfig.getInitParameter("illegalUrl");
    illegalCharacter = fConfig.getInitParameter("illegalCharacter");
  }
  




  public ApplicationFilter() {}
  



  public void destroy() {}
  



  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest req = (HttpServletRequest)request;

    HttpSession session = req.getSession(true);

    Map requestParams = req.getParameterMap();

    Map<String, String[]> illegalRequestParams = getIllegalRequestParams(requestParams);
    req = new ParameterRequestWrapper(req, illegalRequestParams);

    chain.doFilter(req, response);
  }
  


  private Map<String, String[]> getIllegalRequestParams(Map requestParams)
  {
    Map<String, String[]> illegalRequestParams = new HashMap();
    String[] illegalCharArray; Iterator<?> iter; if (requestParams.size() > 0) {
      illegalCharArray = illegalCharacter.toUpperCase().split(",");
      for (iter = requestParams.keySet().iterator(); iter.hasNext();)
      {
        String name = (String)iter.next();
        String[] values = (String[])requestParams.get(name);
        String string = values[0];
        for (int i = 0; i < illegalCharArray.length; i++) {
          String illegalChar = illegalCharArray[i];
          int indexOf = string.toUpperCase().indexOf(illegalChar);
          if (indexOf > -1) {
            String subtring = string.substring(indexOf, indexOf + illegalChar.length());
            string = string.replaceAll(subtring, "");
          }
        }


        values[0] = string;
        illegalRequestParams.put(name, values);
      }
    }
    return illegalRequestParams;
  }


}
