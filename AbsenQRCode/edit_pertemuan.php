<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $Pertemuan = mysqli_real_escape_string($conn, $_POST['Pertemuan']);
    $Tanggal = mysqli_real_escape_string($conn, $_POST['Tanggal']); 
    $id_Mhs = mysqli_real_escape_string($conn, $_POST['Jsonlist']); 
 
    $ids = explode(",", $id_Mhs);
    foreach ($ids as  $value) { 
        $query = "UPDATE tb_presensi
        SET 
            Pertemuan='$Pertemuan',
            Tanggal='$Tanggal' 
        WHERE id_presensi='$value'";
       $isSuccess =  mysqli_query($conn, $query); 
    } 

    $result["succes"] = "1";
    $result["message"] =  'success';
    echo json_encode($result);
    mysqli_close($conn);
}

 
