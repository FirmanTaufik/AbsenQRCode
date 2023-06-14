<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
$id_dosen = mysqli_real_escape_string($conn, $_GET['id']);

$id = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_dosen WHERE id_dosen = '$id_dosen'  "));
$result["succes"] = "1";
$result["message"] =  'success';
$result["data"]   = $id;
echo json_encode($result);
mysqli_close($conn);
