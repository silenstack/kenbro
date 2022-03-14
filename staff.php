<?php
include 'conn.php';

$sql = "CREATE TABLE employee(
    serial_no varchar(50) PRIMARY KEY,
    fname TEXT NOT NULL,
    lname text not null,
    email varchar(50),
    phone varchar(50),
    role varchar(250),
    username varchar(50),
    password VARCHAR(250),
    status int default 0,
    remarks varchar(200),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  )"; //serial_no,fname,lname,email,phone,address,username,status,remarks,date
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "Staff table created successfully";
}
mysqli_close($db);