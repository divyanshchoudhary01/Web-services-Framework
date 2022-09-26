package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.FilenameFilter;
import java.lang.reflect.*;
import java.lang.annotation.*;
import com.thinking.machines.webrock.Annotations.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.model.*;
import java.util.*;
import com.google.gson.*;
import java.math.*;
public class TMWebRock extends HttpServlet
{
public void init() throws ServletException
{
try
{
ArrayList<Service> l1=new ArrayList<Service>();
ServletConfig config=getServletConfig();  
String c=config.getInitParameter("SERVICE_PACKAGE_PREFIX");  
c=c.replace('.','/');
String rFile="c:/tomcat9/webapps/tmwebrock/WEB-INF/classes"+"/"+c;
File f=new File(rFile);
if(f==null)
{
//response.sendError(404,"Given file is not present in the mentioned package in the WEB-INF");
System.out.println("there is no file ");
return;
}
FilenameFilter filenameFilter = (file, s) ->true;
File[] list = f.listFiles(filenameFilter);
File ds=null;
String cc=null;
HashMap<String, Service> hash=null;
for(File file :list) 
{
//1
if(file.getName().endsWith(".java")) 
{
//2
//analyze the class
RandomAccessFile r=new RandomAccessFile(file,"r");
//here we are reading the package name of the file
String a=r.readLine();
a=a.replace("package","");
a=a.replace(";","");
a=a.replace(" ","");
String cFile=file.getName();
cFile=cFile.replace(".java"," ");
cFile=cFile.replace(" ","");
System.out.println(a+"."+cFile);
Class clazz=Class.forName(a+"."+cFile);
//here Class type object is formed
String url=null;
path t=null;
if(clazz.isAnnotationPresent(path.class)) 
{
t=(path)clazz.getAnnotation(path.class);
url=t.name();
}
//get gt=(get)clazz.getAnnotation(get.class);  
//post pos=(post)clazz.getAnnotation(post.class);
Service service=null;
webRockModel WebRockModel=new webRockModel();
hash=WebRockModel.getMap();
get g=null;
post p=null;
for(Method method : clazz.getDeclaredMethods()) 
{
//3
Class returnParam=method.getReturnType();
Class parameters[]=method.getParameterTypes();
path pt=null;
service=new Service();
if(method.isAnnotationPresent(path.class)) //name of the annotation will come here
{ 
pt=method.getAnnotation(path.class);  
cc=url+pt.name();
service.setPath(cc);
}
else
{
cc=url;
service.setPath(null);
}
forward fw=method.getAnnotation(forward.class);
//method.setAccessible(true);
service.setMethod(method);
service.setClass(clazz);
Annotation[][] Arrayannotations=method.getParameterAnnotations();
for(Annotation[] annotationRow :Arrayannotations) 
{
for(Annotation annotation:annotationRow) 
{
service.setRequestParameter(true);
}
}
if(service.getRequestParameter())
{
service.setcheckJSON(false);
}
else
{
service.setcheckJSON(true);
}
if(method.isAnnotationPresent(post.class))
{
service.setRequest("POST");
service.setGetAllowed(false);
service.setPostAllowed(true);
}
else if(method.isAnnotationPresent(get.class))
{
service.setRequest("GET");
service.setGetAllowed(true);
service.setPostAllowed(false);
}
if(fw!=null)
{
service.setForward(fw.name());
}
if(parameters.length!=0)
{
if(clazz.isAnnotationPresent(InjectSessionScope.class)) 
{
service.setInjectSessionScope(true);
}
if(clazz.isAnnotationPresent(InjectApplicationDirectory.class)) 
{
service.setInjectApplicationDirectory(true);
}
if(clazz.isAnnotationPresent(InjectApplicationScope.class)) 
{
service.setInjectApplicationScope(true);
}
if(clazz.isAnnotationPresent(InjectRequestScope.class)) 
{
service.setInjectRequestScope(true);
}
}
if((returnParam.getName().equals("void") && parameters.length==0))
{
l1.add(service);
}
hash.put(cc,service);
} //for loop of method

} 
}

ServletContext context = getServletContext();
Collections.sort(l1);
context.setAttribute("Map", hash);
//calling the following method in a sequence
for(Service s:l1)
{
Class c1=s.getServiceClass();
Method m=s.getMethod();
m.invoke(c1.newInstance());
}
}catch(Exception e)
{
System.out.println("Class not found   "+e.getMessage());
}
System.out.println("Servlet initialized succesfully");
}
public void doGet(HttpServletRequest request ,HttpServletResponse response)
{
try
{
ServletContext context = getServletContext();
String path=request.getServletPath();
Integer a=0;
float b=0;
double dc=0;
HashMap<String, Service> hash=(HashMap)context.getAttribute("Map");
Service service=null;
Method m=null;
if(!(hash.containsKey(path)))
{
//send request for error page
response.sendError(404,"The requested resource is not available");
return;
}
for(Map.Entry<String,Service> entry : hash.entrySet())
{ 
if(entry.getKey().equalsIgnoreCase(path))
{
service=entry.getValue();
break;
}
}
String requestType=service.getRequest();
boolean getType=service.getGetAllowed();
boolean postType=service.getPostAllowed();
if(requestType!=null)
{
if(postType)
{
response.sendError(404,"The requested resource is  available in POST type request");
return;
}
}
Class cc=service.getServiceClass();
Class c=service.getServiceClass();
SessionScope sessionScope=null;
if(service.getInjectSessionScope())
{
sessionScope=new SessionScope();
HttpSession session=request.getSession();  
sessionScope.setAttribute(session);
Method seScope=cc.getDeclaredMethod("setSessionScope",SessionScope.class);
seScope.invoke(cc.newInstance(), sessionScope); 
}
RequestScope requestScope=null;
if(service.getInjectRequestScope())
{
requestScope=new RequestScope();
requestScope.setHttpServletRequest(request);
Method seScope=cc.getDeclaredMethod("setRequestScope",RequestScope.class);
seScope.invoke(cc.newInstance(), requestScope); 
}
ApplicationScope applicationScope=null;
if(service.getInjectApplicationScope())
{
ServletContext servletContext=getServletContext();
applicationScope=new ApplicationScope();
applicationScope.setAttribute(servletContext);
Method seScope=cc.getDeclaredMethod("setApplicationScope",ApplicationScope.class);
seScope.invoke(cc.newInstance(), applicationScope); 
}
ApplicationDirectory applicationDirectory=null;
if(service.getInjectApplicationDirectory())
{
applicationDirectory=new ApplicationDirectory();
Method seScope=cc.getDeclaredMethod("setApplicationDirectory",ApplicationDirectory.class);
seScope.invoke(cc.newInstance(),applicationDirectory); 
}
m=service.getMethod();
Object k[]=null;
Class Parameters[]=m.getParameterTypes();
if(Parameters.length==0)
{
Object value=m.invoke(c.newInstance(),k);
if(value!=null)
{
PrintWriter pw=response.getWriter();
pw.println(value.toString());        
pw.flush();  
pw.close();  
String forward=service.getForward();
if(forward!=null)
{
path pt=(path)c.getAnnotation(path.class);
RequestDispatcher r =request.getRequestDispatcher("/"+pt.name()+forward);
r.forward(request, response);
} //if forward !=null
} //if value!=null
}// if parameters.length
else
{
k=new Object[Parameters.length];
int y=0;
HashMap<Integer,String> paramAnnotationList=new HashMap<>();
Annotation[][] Arrayannotations=m.getParameterAnnotations();
int u=0;
for(Annotation[] annotationRow :Arrayannotations) 
{
for(Annotation annotation:annotationRow) 
{
RequestParameter  rq=(RequestParameter)annotation;
paramAnnotationList.put(u,rq.value());
u++;
}
}
for(y=0;y<Parameters.length;y++)
{
String pName=Parameters[y].getName();
if(pName.equalsIgnoreCase("int"))
{
k[y]=Integer.valueOf(request.getParameter(paramAnnotationList.get(y)));
}
else if(pName.equalsIgnoreCase("float"))
{
k[y]=Float.valueOf(request.getParameter(paramAnnotationList.get(y)));
}
else if(pName.equalsIgnoreCase("java.math.BigDecimal"))
{
k[y]=BigDecimal.valueOf(Double.parseDouble(request.getParameter(paramAnnotationList.get(y))));
}
else if(pName.equalsIgnoreCase("java.lang.String"))
{
k[y]=request.getParameter(paramAnnotationList.get(y));
}
else if(pName.equalsIgnoreCase("char"))
{
String s=request.getParameter(paramAnnotationList.get(y)); 
char ch=s.charAt(0);  
k[y]=ch;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.RequestScope"))
{
k[y]=requestScope;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.ApplicationDirectory"))
{
k[y]=applicationDirectory;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.SessionScope"))
{
k[y]=sessionScope;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.ApplicationScope"))
{
k[y]=applicationScope;
}
else
{
if(!(service.getcheckJSON()))
{
response.sendError(404,"As request parameter available in the written functionality ,you can't send JSON with it");
return;
}
//means we are having the json object
BufferedReader br=request.getReader();
StringBuffer stringBuffer=new StringBuffer();
String d;
while(true)
{
d=br.readLine();
if(d==null) 
{
break;
}
stringBuffer.append(d);
}
String rawdata=stringBuffer.toString();
Object c3=new Gson().fromJson(rawdata,Object.class);
k[y]=c3;
} //else condition ends here
} //for loop of parameters end
Object value=m.invoke(c.newInstance(),k);
if(value!=null)
{
PrintWriter pw=response.getWriter();
Gson gson = new Gson();
String jsonString=gson.toJson(value);
pw.print(jsonString);
pw.flush();  
String forward=service.getForward();
if(forward!=null)
{
path pt=(path)c.getAnnotation(path.class);
RequestDispatcher r =request.getRequestDispatcher("/"+pt.name()+forward);
r.forward(request, response);
}

}

}
}catch(Exception e)
{
System.out.println("exception getting raised");
System.out.println(e.getMessage());
}
}
public void doPost(HttpServletRequest request ,HttpServletResponse response)
{
try
{
ServletContext context = getServletContext();
String path=request.getServletPath();
HashMap<String, Service> hash=(HashMap)context.getAttribute("Map");
Service service=null;
Method m=null;
if(!(hash.containsKey(path)))
{
//send request for error page
response.sendError(404,"The requested resource is not available");
return;
}
for(Map.Entry<String,Service> entry : hash.entrySet())
{ 
if(entry.getKey().equalsIgnoreCase(path))
{
service=entry.getValue();
break;
}
}

String requestType=service.getRequest();
boolean getType=service.getGetAllowed();
boolean postType=service.getPostAllowed();
if(requestType!=null)
{
if(getType)
{
response.sendError(404,"The requested resource is  available in GET type request");
return;
}
}





Class cc=service.getServiceClass();
Class c=service.getServiceClass();
SessionScope sessionScope=null;
if(service.getInjectSessionScope())
{
sessionScope=new SessionScope();
HttpSession session=request.getSession();  
sessionScope.setAttribute(session);
Method seScope=cc.getDeclaredMethod("setSessionScope",SessionScope.class);
seScope.invoke(cc.newInstance(), sessionScope); 
}
RequestScope requestScope=null;
if(service.getInjectRequestScope())
{
requestScope=new RequestScope();
requestScope.setHttpServletRequest(request);
Method seScope=cc.getDeclaredMethod("setRequestScope",RequestScope.class);
seScope.invoke(cc.newInstance(), requestScope); 
}
ApplicationScope applicationScope=null;
if(service.getInjectApplicationScope())
{
ServletContext servletContext=getServletContext();
applicationScope=new ApplicationScope();
applicationScope.setAttribute(servletContext);
Method seScope=cc.getDeclaredMethod("setApplicationScope",ApplicationScope.class);
seScope.invoke(cc.newInstance(), applicationScope); 
}
ApplicationDirectory applicationDirectory=null;
if(service.getInjectApplicationDirectory())
{
applicationDirectory=new ApplicationDirectory();
Method seScope=cc.getDeclaredMethod("setApplicationDirectory",ApplicationDirectory.class);
seScope.invoke(cc.newInstance(),applicationDirectory); 
}
m=service.getMethod();
Object k[]=null;
Class Parameters[]=m.getParameterTypes();
if(Parameters.length==0)
{
Object value=m.invoke(c.newInstance(),k);
if(value!=null)
{
PrintWriter pw=response.getWriter();
pw.println(value.toString());        
pw.flush();  
pw.close();  
String forward=service.getForward();
if(forward!=null)
{
path pt=(path)c.getAnnotation(path.class);
RequestDispatcher r =request.getRequestDispatcher("/"+pt.name()+forward);
r.forward(request, response);
} //if forward !=null
} //if value!=null
}// if parameters.length
else
{
k=new Object[Parameters.length];
int y=0;
HashMap<Integer,String> paramAnnotationList=new HashMap<>();
Annotation[][] Arrayannotations=m.getParameterAnnotations();
int u=0;
for(Annotation[] annotationRow :Arrayannotations) 
{
for(Annotation annotation:annotationRow) 
{
RequestParameter  rq=(RequestParameter)annotation;
paramAnnotationList.put(u,rq.value());
u++;
}
}
for(y=0;y<Parameters.length;y++)
{
//there is bug here
String pName=Parameters[y].getName();
if(pName.equalsIgnoreCase("int"))
{
k[y]=Integer.valueOf(request.getParameter(paramAnnotationList.get(y)));
}
else if(pName.equalsIgnoreCase("float"))
{
k[y]=Float.valueOf(request.getParameter(paramAnnotationList.get(y)));
}
else if(pName.equalsIgnoreCase("java.math.BigDecimal"))
{
k[y]=BigDecimal.valueOf(Double.parseDouble(request.getParameter(paramAnnotationList.get(y))));
}
else if(pName.equalsIgnoreCase("java.lang.String"))
{
k[y]=request.getParameter(paramAnnotationList.get(y));
}
else if(pName.equalsIgnoreCase("char"))
{
String s=request.getParameter(paramAnnotationList.get(y)); 
char ch=s.charAt(0);  
k[y]=ch;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.RequestScope"))
{
k[y]=requestScope;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.ApplicationDirectory"))
{
k[y]=applicationDirectory;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.SessionScope"))
{
k[y]=sessionScope;
}
else if(pName.equalsIgnoreCase("com.thinking.machines.webrock.ApplicationScope"))
{
k[y]=applicationScope;
}
else
{
if(!(service.getcheckJSON()))
{
response.sendError(404,"As request parameter available in the written functionality ,you can't send JSON with it");
return;
}
//means we are having the json object
BufferedReader br=request.getReader();
StringBuffer stringBuffer=new StringBuffer();
String d;
while(true)
{
d=br.readLine();
if(d==null) 
{
break;
}
stringBuffer.append(d);
}
String rawdata=stringBuffer.toString();
Object c3=new Gson().fromJson(rawdata,Object.class);
k[y]=c3;
} //else condition ends here
} //for loop of parameters end
Object value=m.invoke(c.newInstance(),k);
if(value!=null)
{
PrintWriter pw=response.getWriter();
Gson gson = new Gson();
String jsonString=gson.toJson(value);
pw.print(jsonString);
pw.flush();  
String forward=service.getForward();
if(forward!=null)
{
path pt=(path)c.getAnnotation(path.class);
RequestDispatcher r =request.getRequestDispatcher("/"+pt.name()+forward);
r.forward(request, response);
}

}

}










}catch(Exception e)
{
System.out.println("exception getting raised");
System.out.println(e.getMessage());
}
}
}