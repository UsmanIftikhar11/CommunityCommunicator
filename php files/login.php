<?php

require "init.php";

$Email = $_GET["Email"];
$Password = $_GET["Password"];

$sql = "select Name from users where Email = '$Email' and Password = '$Password'";

$result = mysqli_query($con,$sql);

if(!mysqli_num_rows($result)>0)
{
$status = "failed";
echo json_encode(array("response"=>$status));
}

else
{
$row = mysqli_fetch_assoc($result);
$Name = $row['Name'];
$status = "ok";
echo json_encode(array("response"=>$status,"Name"=>$Name));
}

mysqli_close($con);

?>