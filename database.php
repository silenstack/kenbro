<?php
$host = "localhost";
$username = "root";
$password = "";

$con = new mysqli($host, $username, $password);
if ($con->connect_error) {
    die("Connection Error");
}

$sql = 'CREATE Database kenbro';
if ($con->query($sql) == TRUE) {
    echo "Kenbro database created successfully";
} else {
    echo "Failed to create database:" . $con->error;
}

$con->close();