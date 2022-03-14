<?php
error_reporting(0);
error_reporting(E_ERROR);
session_start();
include 'database/conn.php';
if (isset($_POST['regist'])) {
    $username = $_POST['username'];
    $password = md5($_POST['password']);
    $cpassword = md5($_POST['retype']);
    $year = date("Y");
    if ($cpassword != $password) {
        $notify = 'message';
        $show = 'Oops!! Your passwords do not match';
    } else {
        $sql = "SELECT * FROM admin WHERE username='$username'";
        $response = mysqli_query($db, $sql);
        if (mysqli_num_rows($response) === 1) {
            mysqli_query($db, "UPDATE admin set password='$password' where username='$username'");
            $row = mysqli_fetch_assoc($response);
            $notify = 'message';
            $show = 'Password reset successfully';
            header("Refresh:1.5; url=logger");
            mysqli_close($db);
        } else {
            $notify = 'message';
            $show = 'Your username is not recognized';
            mysqli_close($db);
        }
    }
}

?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kenbro||Admin</title>
    <link rel="stylesheet" href="style/style.css">
    <script src="custom-sweeralert.js"></script>
</head>

<body>

    <link href='https://fonts.googleapis.com/css?family=Ubuntu:500' rel='stylesheet' type='text/css'>
    <form method="POST">
        <div class="login">
            <?php
            if ($notify == 'message') {
                echo '<script>
    swal("","' . $show . '");
    </script>';
            }
            ?>
            <div class="login-header">
                <h4>Reset Password</h4>
            </div>
            <div class="login-form">
                <h3>Username:</h3>
                <input type="text" name="username" placeholder="Username" required /><br>
                <h3>New Password:</h3>
                <input type="password" name="password" placeholder="new Password" required />
                <br>
                <h3>Confirm:</h3>
                <input type="password" name="retype" placeholder="retype Password" required />
                <br>
                <input type="submit" name="regist" value="Reset" class="login-button" />
                <br>
                <a href="logger" class="sign-up">Back</a>
                <br>
            </div>

        </div>
    </form>
</body>

</html>