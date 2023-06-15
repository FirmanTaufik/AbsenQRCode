<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php"; 

$query = "SELECT *from tb_mhs ";

$w = mysqli_query($conn, $query);
$data = array();
while ($row = mysqli_fetch_object($w  )) {



    $data[] = $row;
}
 
echo json_encode($data);
mysqli_close($conn);
