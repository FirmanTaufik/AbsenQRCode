<?php

header('Content-Type: application/json; charset=utf-8');
include "+connection.php";

$idMk = mysqli_real_escape_string($conn, $_GET['idMk']);
$idKelas = mysqli_real_escape_string($conn, $_GET['idKelas']);

$query = "SELECT  tb_mhs_kelas. *, tb_mhs.* 
     FROM tb_mhs_kelas 
     left join tb_mhs on tb_mhs_kelas.id_mhs = tb_mhs.id_mhs
	WHERE tb_mhs_kelas.id_kelas = '$idKelas' ";
$w = mysqli_query($conn, $query);


$data = array();
while ($row = mysqli_fetch_object($w)) {
    $idMhs = $row->id_mhs;
    $arrayName = array();

    for ($i = 1; $i < 17; $i++) {

        $query = "SELECT  *from tb_presensi where Pertemuan ='$i' and  
        Id_mk ='$idMk' and Id_mhs =' $idMhs' and Id_kelas   ='$idKelas' ";

        $fafa = mysqli_num_rows(mysqli_query($conn, $query));
        if ($fafa != 0) {
            array_push($arrayName, true);
        } else   {
            array_push($arrayName, false);
        }
    }
    $row->Pertemuan =  $arrayName;

    $data[] = $row;
}

echo json_encode($data);
mysqli_close($conn);

// $query = "SELECT  tb_presensi.*, tb_mhs.*
// FROM tb_presensi
// LEFT JOIN tb_mhs ON tb_mhs.id_mhs = tb_presensi.Id_mhs
//   where Id_mk = '$idMk' and tb_presensi.Id_kelas = '$idKelas'  
//  ORDER BY Tanggal desc  ";

// $w = mysqli_query($conn, $query);
// $data = array();
// while ($row = mysqli_fetch_object($w)) { 
//     $data[] = $row;
// }

// echo json_encode($data);
// mysqli_close($conn);
