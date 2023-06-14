<?php
 
$host ="localhost";
$user = "root";
$pass = "";
$database ="db_absen_qrcode"; 

$conn = mysqli_connect($host, $user, $pass, $database);

if(!$conn) {
    echo "tidak connect";
}

?>