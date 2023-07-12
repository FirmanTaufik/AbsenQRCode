<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";

$pertemuan = mysqli_real_escape_string($conn, $_POST['Pertemuan']);
$idMk = mysqli_real_escape_string($conn, $_POST['idMk']);

$query = "DELETE FROM tb_presensi WHERE Pertemuan =  '$pertemuan' and Id_mk = '$idMk'   ";

$id = mysqli_query($conn,$query);        

$result["succes"] = "1";
$result["message"] =  "berhasil menghapus";     
echo json_encode($result);
mysqli_close($conn);