package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
public class RequestScope 
{
private HttpServletRequest httpServletRequest;
public void setHttpServletRequest(HttpServletRequest httpServletRequest)
{
this.httpServletRequest=httpServletRequest;
}
public HttpServletRequest getHttpServletRequest()
{
return this.httpServletRequest;
}
}