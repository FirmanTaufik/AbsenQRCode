<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $Pertemuan = mysqli_real_escape_string($conn, $_POST['Pertemuan']);
    $Tanggal = mysqli_real_escape_string($conn, $_POST['Tanggal']);
    $Id_mk = mysqli_real_escape_string($conn, $_POST['Id_mk']);
    $id_Mhs = mysqli_real_escape_string($conn, $_POST['Jsonlist']);

    mysqli_query($conn,  "DELETE FROM tb_presensi WHERE Pertemuan = '$Pertemuan' and Tanggal ='$Tanggal' and Id_mk ='$Id_mk' ");


    $ids = explode(",", $id_Mhs);
    foreach ($ids as  $value) { 
        $query = "INSERT INTO tb_presensi
        (id_presensi, Pertemuan, Tanggal, Id_mk, Id_mhs)
        VALUES (NULL, '$Pertemuan',  '$Tanggal', '$Id_mk', '$value')";
        mysqli_query($conn, $query);
    } 

    $result["succes"] = "1";
    $result["message"] =  'success';
    echo json_encode($result);
    mysqli_close($conn);
}

 
