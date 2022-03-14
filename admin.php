<?php
include 'conn.php';

$sql = "CREATE TABLE admin(
  adm_id varchar(50) PRIMARY KEY,
  username TEXT NOT NULL,
  password VARCHAR(250),
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)";

$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "Admin table created successfully";
}
mysqli_close($db);