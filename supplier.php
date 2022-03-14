<?php
include 'conn.php';

$sql = "CREATE TABLE supplier(
    entry_no varchar(50) PRIMARY KEY,
    business_no varchar(20),
    fname TEXT NOT NULL,
    lname text not null,
    email varchar(50),
    phone varchar(50),
    address varchar(250),
    username varchar(50),
    password VARCHAR(250),
    status int default 0,
    remarks varchar(200),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  )"; //entry_no,business_no,fname,lname,email,phone,address,username,status,remarks,date
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "Supplier table created successfully";
}
mysqli_close($db);