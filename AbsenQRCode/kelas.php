<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
$id_kelas = mysqli_real_escape_string($conn, $_GET['id']);
$idMk = mysqli_real_escape_string($conn, $_GET['idMk']);

$id = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_kelas WHERE id_kelas = '$id_kelas'  "));

$mengajar = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_mk WHERE id_mk = '$idMk'  "));


$query = "SELECT  tb_presensi.Pertemuan ,tb_presensi.Tanggal ,tb_presensi.Id_mk
FROM tb_presensi
WHERE tb_presensi.Id_mk= '$idMk' and tb_presensi.Id_kelas= '$id_kelas'
GROUP BY tb_presensi.Pertemuan ,tb_presensi.Tanggal 
order by tb_presensi.Pertemuan  desc";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w)) {

    $m = mysqli_query($conn, "SELECT  tb_presensi.*, tb_mhs.id_mhs, tb_mhs.NIM,tb_mhs.Nama
    FROM tb_presensi
    LEFT JOIN tb_mhs ON tb_mhs.id_mhs = tb_presensi.Id_mhs
    where pertemuan = '$row->Pertemuan' and Id_mk = '$row->Id_mk' 
    and tb_presensi.Id_kelas= '$id_kelas'
     ORDER BY tb_presensi.id_presensi desc    ");
    $ids = array();
    while ($result = mysqli_fetch_object($m)) {
        $ids[] = $result;
    }

    $object = new stdClass();
    $object->Pertemuan = $row->Pertemuan;
    $object->Tanggal = $row->Tanggal;
    $object->Id_mk = $row->Id_mk;
    $object->Absen =  $ids;

    array_push($data, $object);
}

$object = new stdClass();
$object->kelas = $id;
$object->mengajar = $mengajar;
$object->pertemuan = $data;

echo json_encode($object);


// mysqli_close($conn);
