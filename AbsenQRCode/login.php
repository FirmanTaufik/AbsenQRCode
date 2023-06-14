<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
    header('Content-Type: application/json; charset=utf-8');
    include "+connection.php"; 
    $Username = mysqli_real_escape_string($conn, $_POST['Username']);
    $Password = md5( mysqli_real_escape_string($conn, $_POST['Password'])) ;

    $r = mysqli_num_rows(mysqli_query($conn, "SELECT*FROM tb_dosen WHERE Username = '$Username' AND Password = '$Password' "));

    if ($r==1) {
        $id = mysqli_fetch_assoc(mysqli_query($conn, "SELECT*FROM tb_dosen WHERE Username = '$Username' AND Password = '$Password'  "));        
        $result["succes"] = "1";
        $result["message"] =  $id['id_dosen'];     
        echo json_encode($result);
        mysqli_close($conn);

    } else {
        $result["succes"] = "0";
        $result["message"] = "Username atau Password Salah" ;

        echo json_encode($result);
        mysqli_close($conn);
    }

}


?>