package  com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
public class Service implements Comparable
{
private Class serviceClass;
private String path;
private Method service;
private String forward;
private String request;
private boolean isGetAllowed;
private boolean isPostAllowed;
private boolean runOnStart;
private int priorty;
private boolean injectSessionScope;
private boolean injectRequestScope;
private boolean injectApplicationScope;
private boolean injectApplicationDirectory;
private boolean isRequestParameter;
private boolean checkJSON;
public Service()
{
this.serviceClass=null;
this.path=null;
this.service=null;
this.forward=null;
this.request=null;
this.isGetAllowed=true;
this.isPostAllowed=true;
this.runOnStart=false;
this.priorty=0;
this.injectSessionScope=false;
this.injectRequestScope=false;
this.injectApplicationScope=false;
this.injectApplicationDirectory=false;
this.isRequestParameter=false;
this.checkJSON=false;
}
public void setcheckJSON(boolean checkJSON)
{
this.checkJSON=checkJSON;
}
public boolean getcheckJSON()
{
return this.checkJSON;
}
public void setRequestParameter(boolean isRequestParameter)
{
this.isRequestParameter=isRequestParameter;
}
public boolean getRequestParameter()
{
return this.isRequestParameter;
}
public boolean getInjectSessionScope()
{
return this.injectSessionScope;
}
public boolean getInjectRequestScope()
{
return this.injectRequestScope;
}
public boolean getInjectApplicationScope()
{
return this.injectApplicationScope;
}
public boolean getInjectApplicationDirectory()
{
return this.injectApplicationDirectory;
}
public void setInjectSessionScope(boolean injectSessionScope)
{
this.injectSessionScope=injectSessionScope;
}
public void setInjectRequestScope(boolean injectRequestScope)
{
this.injectRequestScope=injectRequestScope;
}
public void setInjectApplicationScope(boolean injectApplicationScope)
{
this.injectApplicationScope=injectApplicationScope;
}
public void setInjectApplicationDirectory(boolean injectApplicationDirectory)
{
this.injectApplicationDirectory=injectApplicationDirectory;
}
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void setMethod(Method service)
{
this.service=service;
}
public Method getMethod()
{
return this.service;
}
public Class getServiceClass()
{
return this.serviceClass;
}
public void setClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public void setForward(String forward)
{
this.forward=forward;
}
public String getForward()
{
return this.forward;
}
public void setRequest(String request)
{
this.request=request;
}
public String getRequest()
{
return this.request;
}
public void setGetAllowed(boolean isGetAllowed)
{
this.isGetAllowed=isGetAllowed;
}
public void setPostAllowed(boolean isPostAllowed)
{
this.isPostAllowed=isPostAllowed;
}
public boolean getGetAllowed()
{
return this.isGetAllowed;
}
public boolean getPostAllowed()
{
return this.isPostAllowed;
}
public void setRunOnStart(boolean runOnStart)
{
this.runOnStart=runOnStart;
}
public boolean getRunOnStart()
{
return this.runOnStart;
}
public void setPriorty(int priorty)
{
this.priorty=priorty;
}
public int getPriorty()
{
return this.priorty;
}
public int compareTo(Object object) 
{
Service service=(Service)object;
int priorty=service.getPriorty();
/* For Ascending order*/
return this.priorty-priorty;
/* For Descending order do like this */
//return compareage-this.studentage;
}
}