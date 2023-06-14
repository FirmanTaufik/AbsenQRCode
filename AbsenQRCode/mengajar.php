<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
$id_dosen = mysqli_real_escape_string($conn, $_GET['id_dosen']);

$query = "SELECT tb_mk.id_mk, tb_mk.Nama_mk, tb_mk.Kode_mk, 
tb_mk.id_dosen, tb_dosen.Nama_dosen, tb_mk.id_kelas, tb_kelas.nama_kelas
	FROM tb_mk
	LEFT JOIN tb_dosen on
	tb_dosen.id_dosen = tb_mk.id_dosen
	LEFT JOIN tb_kelas on
	tb_kelas.id_kelas = tb_mk.id_kelas
	WHERE tb_mk.id_dosen = '$id_dosen'
	GROUP BY tb_mk.id_mk";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w  )) {



    $data[] = $row;
}
 
echo json_encode($data);
mysqli_close($conn);
