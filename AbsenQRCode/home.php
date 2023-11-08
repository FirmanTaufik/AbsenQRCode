<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
$id_dosen = mysqli_real_escape_string($conn, $_GET['id_dosen']);

$query = "SELECT  *FROM tb_mk 
	WHERE tb_mk.id_dosen = '$id_dosen'
	GROUP BY tb_mk.id_mk";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w  )) { 

    $data[] = $row;
}

$query = "SELECT  *FROM tb_kelas   ";

$wkelas = mysqli_query($conn, $query);
$kelas = array();
while ($row = mysqli_fetch_object($wkelas  )) { 

    $kelas[] = $row;
}

$id = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_dosen WHERE id_dosen = '$id_dosen'  "));
 $object = new stdClass();
 $object ->profile = $id;
 $object ->mengajar = $data;
 $object ->kelas = $kelas;
echo json_encode($object);
mysqli_close($conn);
