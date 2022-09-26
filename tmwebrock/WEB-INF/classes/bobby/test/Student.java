package bobby.test;
import com.thinking.machines.webrock.Annotations.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.*;
import java.math.*;
@path(name="/stud")
@InjectSessionScope
@InjectApplicationDirectory
@InjectRequestScope
@InjectApplicationScope
public class Student
{
private Student student;
private Service service;
private SessionScope sessionScope;
private RequestScope requestScope;
private ApplicationScope applicationScope;
private ApplicationDirectory applicationDirectory;
@get
@path(name="/printName")
@forward(name="/printModuleName")
//@onStartup(priority=2)
public void printName()
{
System.out.println("print Name method got called");
}
@get
@path(name="/printModuleName")
public void printModuleName()
{
System.out.println("Name of the class is student Module");
}
@get
@path(name="/ModuleName")
public String getModuleName()
{
return "Student Module Name";
}
public void setSessionScope(SessionScope sessionScope)
{
this.sessionScope=sessionScope;
}
public SessionScope getSessionScope()
{
return this.sessionScope;
}
public void setRequestScope(RequestScope requestScope)
{
this.requestScope=requestScope;
}
public RequestScope getRequestScope()
{
return this.requestScope;
}
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
}
public ApplicationScope getApplicationScope()
{
return this.applicationScope;
}
public void setApplicationDirectory(ApplicationDirectory applicationDirectory)
{
this.applicationDirectory=applicationDirectory;
}
public ApplicationDirectory getApplicationDirectory()
{
return this.applicationDirectory;
}








@post
@path(name="/printAge")
//@onStartup(priority=1)
public String printAge(@RequestParameter("xyz") int x,@RequestParameter("pqr") String name)
{
System.out.println("Student name is "+name);
System.out.println("Student rollNumber is "+x);
return "Data recieved";
}



@post
@path(name="/printData")
public String printData(@RequestParameter("xyz") float x,@RequestParameter("pqr") BigDecimal name)
{
System.out.println("Student name is "+name);
System.out.println("Student rollNumber is "+x);
return "Data recieved";
}



@get
@path(name="/printChar")
public String printChar(@RequestParameter("xyz") int x,@RequestParameter("pqr") char name)
{
System.out.println("Student name is "+name);
System.out.println("Student rollNumber is "+x);
return "Data recieved";
}






@path(name="/printing")
//@onStartup(priority=1)
public void printing(@RequestParameter("xyz") int x,ApplicationDirectory applicationDirectory,SessionScope sessionScope,RequestScope requestScope)
{
System.out.println("Printing method got called");
}
@path(name="/jsonCall")
public String jsonCall(Object obj,ApplicationDirectory applicationDirectory,SessionScope sessionScope,RequestScope requestScope)
{
System.out.println(obj);
System.out.println("JSON method got called");
return "Data recieved";
}
}