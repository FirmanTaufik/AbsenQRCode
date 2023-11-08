<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php"; 

$query = "SELECT tb_mhs.* ,tb_kelas.Nama_Kelas, tb_kelas.Tahun_ajaran
from tb_mhs 
LEFT JOIN tb_kelas 
ON tb_kelas.id_kelas = tb_mhs.id_kelas
GROUP BY tb_mhs.id_mhs";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w  )) {



    $data[] = $row;
}
 
echo json_encode($data);
mysqli_close($conn);
