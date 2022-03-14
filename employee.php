<?php
include 'account.php';
if (strlen($_SESSION['userAdmin']) == 0) {
    header('location:logger');
} else {
    if (isset($_POST['approveBtn'])) {
        $customerid = $_POST['identity'];

        $sql = mysqli_query($db, "UPDATE employee set status =1,remarks='' where serial_no = '$customerid '");
        if (!$sql) {
            die("Connection failed: " . $db->connect_error);
        } else {
            echo "<script>alert('Account Activated Successfully')</script>";
            echo "<script>location.href='employee' </script>";
        }
    }

    if (isset($_POST['rejectBtn'])) {
        $customerid = $_POST['identity'];
        $remarks = $_POST['remarks'];

        $sql = mysqli_query($db, "UPDATE employee set status =2 ,remarks='$remarks' where serial_no = '$customerid '");
        if (!$sql) {
            die("Connection failed: " . $db->connect_error);
        } else {
            echo "<script>alert('Account Rejected Successfully')</script>";
            echo "<script>location.href='employee' </script>";
        }
    }
?>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>KenBro</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="vendors/datatables.net-bs4/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="vendors/datatables.net-buttons-bs4/css/buttons.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
</head>

<body>
    <aside id="left-panel" class="left-panel">
        <nav class="navbar navbar-expand-sm navbar-default">
            <div class="navbar-header">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu"
                    aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
            </div>
            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <?php include('side.php'); ?>
            </div>
        </nav>
    </aside>
    <div id="right-panel" class="right-panel">
        <header id="header" class="header">
            <div class="header-menu">
                <div class="col-sm-7">
                    <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
                    <div class="header-left">
                        <div class="dropdown for-notification">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="notification"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa fa-user"></i>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="notification">
                                <a class="dropdown-item media bg-flat-color-1" href="#">
                                    <i class="fa fa-user"></i>
                                    <p>My Profile</p>
                                </a>
                                <a class="dropdown-item media bg-flat-color-4" href="logout">
                                    <i class="fa fa-sign-out"></i>
                                    <p style="color: red;">LogOut</p>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <div class="content mt-3">
            <div class="animated fadeIn">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <strong class="card-title">Manage Employees</strong>
                            </div>
                            <div class="card-body">
                                <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>.</th>
                                            <th>serial_no</th>
                                            <th>fname</th>
                                            <th>lname</th>
                                            <th>email</th>
                                            <th>phone</th>
                                            <th>username</th>
                                            <th>remarks</th>
                                            <th>date</th>
                                            <th>status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php
                                            $query_run = mysqli_query($db, "SELECT * FROM employee");
                                            $i = 1;
                                            if (mysqli_num_rows($query_run) > 0) {

                                                while ($row = mysqli_fetch_array($query_run)) {
                                            ?>
                                        <tr>
                                            <td><?php echo $i; ?></td>
                                            <td><?php echo $row['serial_no']; ?></td>
                                            <td><?php echo $row['fname']; ?></td>
                                            <td><?php echo $row['lname']; ?></td>
                                            <td> <?php echo $row['email']; ?></td>
                                            <td><?php echo $row['phone']; ?></td>
                                            <td><?php echo $row['username']; ?></td>
                                            <td><?php echo $row['remarks']; ?></td>
                                            <td><?php echo $row['date']; ?></td>
                                            <td><?php
                                                            if ($row['status'] == 0) {
                                                                echo 'Pending';
                                                            } elseif ($row['status'] == 1) {
                                                                echo 'Approved';
                                                            } elseif ($row['status'] == 2) {
                                                                echo 'Rejected';
                                                            } ?></td>
                                            <td><?php
                                                            if ($row['status'] == 0) {
                                                                echo '<div class="dropdown" style="float: right;">
                                                                <button class="btn btn-primary btn-xs" type="button"
                                                                    data-toggle="dropdown">Approve</button>
                                                                <ul class="dropdown-menu alert panel-footer">
                                                                    <li>
                                                                        <form method="post">
                                                                            <input type="hidden" name="identity"
                                                                                value="' . $row['serial_no'] . '"/><br>
                                                <input type="submit" name="approveBtn" value="Confirm"
                                                    class="btn btn-primary btn-xs" />
                                                </form>
                                                </li>
                                                </ul>
                            </div>
                            <div class="dropdown" style="float: right;">
                                <button class="btn btn-danger btn-xs" type="button"
                                    data-toggle="dropdown">Reject</button>
                                <ul class="dropdown-menu alert panel-footer">
                                    <li>
                                        <form method="post">
                                            <input type="hidden" name="identity" value="' . $row['serial_no'] . '"/>
                                            <textarea class="form-control" rows="5" name="remarks"
                                                placeholder="Reason why you Reject" required></textarea>
                                            <input type="submit" name="rejectBtn" value="Reject"
                                                class="btn btn-danger btn-xs" />
                                        </form>
                                    </li>
                                </ul>
                            </div>';
                                                            } elseif ($row['status'] == 1) {
                                                                echo ' <div class="dropdown" style="float: right;">
                                <button class="btn btn-danger btn-xs" type="button"
                                    data-toggle="dropdown">Reject</button>
                                <ul class="dropdown-menu alert panel-footer">
                                    <li>
                                        <form method="post">
                                            <input type="hidden" name="identity"
                                                value="' . $row['serial_no'] . '"/>
                                                <textarea class="form-control" rows="5" name="remarks"
                                                    placeholder="Reason why you Reject" required></textarea>
                                                <input type="submit" name="rejectBtn" value="Reject"
                                                    class="btn btn-danger btn-xs" />
                                                </form>
                                                </li>
                                                </ul>
                            </div>';
                                                            } elseif ($row['status'] == 2) {
                                                                echo '<div class="dropdown" style="float: right;">
                                <button class="btn btn-primary btn-xs" type="button"
                                    data-toggle="dropdown">Approve</button>
                                <ul class="dropdown-menu alert panel-footer">
                                    <li>
                                        <form method="post">
                                            <input type="hidden" name="identity"
                                                value="' . $row['serial_no'] . '"/><br>
                                            <input type="submit" name="approveBtn" value="Confirm"
                                                class="btn btn-primary btn-xs" />
                                        </form>
                                    </li>
                                </ul>
                            </div>';
                                                            } ?>


                                            </td>
                                        </tr>
                                        <?php $i = $i + 1;
                                                }
                                            } ?>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <?php include('footer.php'); ?>
    </div>
    <script src="vendors/jquery/dist/jquery.min.js"></script>
    <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>
    <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="vendors/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="vendors/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js"></script>
    <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.colVis.min.js"></script>
    <script src="assets/js/init-scripts/data-table/datatables-init.js"></script>
</body>

</html>
<?php } ?>