<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
$id_kelas = mysqli_real_escape_string($conn, $_GET['id']);
$idMk = mysqli_real_escape_string($conn, $_GET['idMk']);

$id = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_kelas WHERE id_kelas = '$id_kelas'  ")); 

$mengajar = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_mk WHERE id_dosen = '$idMk' and id_kelas = '$id_kelas'  ")); 


$query = "SELECT  tb_presensi.Pertemuan ,tb_presensi.Tanggal ,tb_presensi.Id_mk
FROM tb_presensi
WHERE tb_presensi.Id_mk= '$idMk'
GROUP BY tb_presensi.Pertemuan ,tb_presensi.Tanggal 
order by tb_presensi.Pertemuan  desc";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w  )) {



    $data[] = $row;
}

$object = new stdClass();
$object ->kelas = $id;
$object ->mengajar = $mengajar;
$object ->pertemuan = $data;

echo json_encode($object);


mysqli_close($conn);
