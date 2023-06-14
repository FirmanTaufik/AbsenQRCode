<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php";
    $Pertemuan = mysqli_real_escape_string($conn, $_POST['Pertemuan']);
    $Tanggal = mysqli_real_escape_string($conn, $_POST['Tanggal']);
    $Id_mk = mysqli_real_escape_string($conn, $_POST['Id_mk']);
    $Id_mhs = mysqli_real_escape_string($conn, $_POST['Id_mhs']);

    $query = "INSERT INTO tb_presensi
     (id_presensi, Pertemuan, Tanggal, Id_mk, Id_mhs)
     VALUES (NULL, '$Pertemuan',  '$Tanggal', '$Id_mk', '$Id_mhs')";

    $success =  mysqli_query($conn, $query);

    if ($success) {
        $result["succes"] = "1";
        $result["message"] =  'success';
        echo json_encode($result);
        mysqli_close($conn);
    } else {
        $result["succes"] = "0";
        $result["message"] = "Username atau Password Salah";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
