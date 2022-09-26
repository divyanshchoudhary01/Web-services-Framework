package com.thinking.machines.webrock.model;
import java.util.*;
import com.thinking.machines.webrock.pojo.*;
public class webRockModel  
{
private static HashMap<String, Service> hash;
public HashMap<String, Service> getMap()
{
if(hash!=null) return hash;
return new HashMap<String,Service>();
}
}