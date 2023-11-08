<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";
  

$query = "SELECT  tb_mhs_kelas.*, tb_mhs.*
FROM tb_mhs_kelas
LEFT JOIN tb_mhs ON tb_mhs.id_mhs = tb_mhs_kelas.id_mhs    ";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w)) {



    $data[] = $row;
}

echo json_encode($data);
mysqli_close($conn);
