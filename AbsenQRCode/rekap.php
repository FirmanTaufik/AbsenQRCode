<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
 
$idMk = mysqli_real_escape_string($conn, $_GET['idMk']);
$idKelas = mysqli_real_escape_string($conn, $_GET['idKelas']);
$date1 = mysqli_real_escape_string($conn, $_GET['date1']);
$date2 = mysqli_real_escape_string($conn, $_GET['date2']);

$query = "SELECT  tb_presensi.*, tb_mhs.*
FROM tb_presensi
LEFT JOIN tb_mhs ON tb_mhs.id_mhs = tb_presensi.Id_mhs
  where Id_mk = '$idMk' and tb_presensi.Id_kelas = '$idKelas'  and Tanggal BETWEEN '$date1' AND '$date2'  
 ORDER BY Tanggal desc  ";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w)) {



    $data[] = $row;
}

echo json_encode($data);
mysqli_close($conn);
