class Student
{
constructor()
{
this.rollNumber=0;
this.name="";
this.gender="";
}
}
class StudentService
{
AddStudent(student)
{
if(student.getName().length==0)
{
reject("Title section is empty");
return;
}
if(student.getRollNumber().length==0)
{
reject("RollNumber section is empty");
return;
}
if(student.getGender().length==0)
{
reject("Title section is empty");
return;
}
$.ajax({
url:"/tmwebrock/AddStudent",
type:"GET",
async:false,
data: student, 
success: function(res)
{
//data - response from server
var responseData=JSON.stringify(res['isSuccessfull']);
if(responseData==="true")
{
resolve("Data Saved");
}
else
{
alert("Data Not Saved");
}
},
error: function ()
{
resolve("Some Problem");
}
}); 
});
return p;
}
getStudent(rn)
{
var p=new Promise(function(resolve,reject){
$.ajax({url:'/tmwebrock/getStudent',async:'false',
data: rn,type:'GET',success:function(result){
var responseData=result['result'];
resolve(responseData);
}, 
error: function()
{
reject("Some Problem ");
}
});
});
return p;
}

UpdateStudent(student)
{
var p=new Promise(function(resolve,reject){
$.ajax({
url:"/tmwebrock/UpdateStudent",
type:"GET",
async:false,
data: student, 
success: function(res)
{
//data - response from server
var responseData=JSON.stringify(res['isSuccessfull']);
if(responseData==="true")
{
resolve("Data Updated");
}
else
{
alert("Data Not Updated");
}
},
error: function ()
{
resolve("Some Problem");
}
}); 
});
return p;
}



getAllStudent()
{
var p=new Promise(function(resolve,reject){
$.ajax({
url:"/tmwebrock/getAllStudent",
type:"GET",
async:false,
success: function(res)
{
//data - response from server
var responseData=JSON.stringify(res['isSuccessfull']);
if(responseData==="true")
{
resolve("Data Saved");
}
else
{
alert("Data Not Present");
}
},
error: function ()
{
resolve("Some Problem");
}
}); 
});
return p;
}
}