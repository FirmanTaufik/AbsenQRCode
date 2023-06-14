<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
$id_kelas = mysqli_real_escape_string($conn, $_GET['id']);

$id = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_kelas WHERE id_kelas = '$id_kelas'  "));
$result["succes"] = "1";
$result["message"] =  'success';
$result["data"]   = $id;
echo json_encode($result);
mysqli_close($conn);
