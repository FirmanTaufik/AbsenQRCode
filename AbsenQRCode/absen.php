<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";

$pertemuan = mysqli_real_escape_string($conn, $_GET['pertemuan']);
$idMk = mysqli_real_escape_string($conn, $_GET['idMk']);

$query = "SELECT  tb_presensi.*, tb_mhs.id_mhs, tb_mhs.NIM,tb_mhs.Nama
FROM tb_presensi
LEFT JOIN tb_mhs ON tb_mhs.id_mhs = tb_presensi.Id_mhs
where pertemuan = '$pertemuan' and Id_mk = '$idMk'
 ORDER BY tb_presensi.id_presensi desc    ";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w)) {



    $data[] = $row;
}

echo json_encode($data);
mysqli_close($conn);
