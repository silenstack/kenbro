<?php
include 'conn.php';

$sql = "CREATE TABLE customer(
    entry_no varchar(50) PRIMARY KEY,
    fname TEXT NOT NULL,
    lname text not null,
    email varchar(50),
    phone varchar(50),
    residence varchar(50),
    username varchar(50),
    password VARCHAR(250),
    status int default 0,
    remarks varchar(200),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  )"; //entry_no,fname,lname,email,phone,residence,username,status,remarks,date
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "Customer table created successfully";
}
mysqli_close($db);