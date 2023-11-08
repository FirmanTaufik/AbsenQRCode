<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $Pertemuan = mysqli_real_escape_string($conn, $_POST['Pertemuan']);
    $Tanggal = mysqli_real_escape_string($conn, $_POST['Tanggal']);
    $Id_mk = mysqli_real_escape_string($conn, $_POST['Id_mk']);
    $id_Mhs = mysqli_real_escape_string($conn, $_POST['Jsonlist']);
    $Id_kelas = mysqli_real_escape_string($conn, $_POST['Id_kelas']);
    $StatusJson = mysqli_real_escape_string($conn, $_POST['StatusJson']);

    mysqli_query($conn,  "DELETE FROM tb_presensi WHERE Pertemuan = '$Pertemuan' and Tanggal ='$Tanggal' and Id_mk ='$Id_mk' 
    and Id_kelas ='$Id_kelas' ");


    $ids = explode(",", $id_Mhs);
    $listStatus = explode(",", $StatusJson);

    for ($i = 0; $i < count($ids); $i++) {
        $value = $ids[$i];
        $status = $listStatus[$i];
        $query = "INSERT INTO tb_presensi
            (id_presensi, Pertemuan, Tanggal, Id_mk, Id_mhs, Id_kelas, Status)
            VALUES (NULL, '$Pertemuan',  '$Tanggal', '$Id_mk', '$value', '$Id_kelas', $status )";
            mysqli_query($conn, $query);
    }


    $result["succes"] = "1";
    $result["message"] =  'success';
    echo json_encode($result);
    mysqli_close($conn);
}
