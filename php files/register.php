<?php

require "init.php"
$Name = $_GET["Name"];
$Email = $_GET["Email"];
$Phone = $_GET["Phone"];
$Password = $_GET["Password"];

$sql = "select * from users where Email = '$Email'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result)>0)
{
$status = "exists";
}

else
{
$sql = "insert into users(Name,Email,Phone,Password) values('$Name','$Email','$Phone','$Password');";

	if(mysqli_query($con,$sql))
	{
	   $status = "ok";
	}
	else
	{
	$status = "error";
	}
}

echo json_encode(array("response"=>$status));

mysqli_close($con);

?>