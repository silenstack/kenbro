<?php
session_start();
error_reporting(0);
include 'database/conn.php';
if (strlen($_SESSION['userAdmin']) == 0) {
    header('location:logger');
} else {
?>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>KenBro</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="vendors/jqvmap/dist/jqvmap.min.css">
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
        <div class="breadcrumbs">
            <div class="col-sm-4">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1>Dashboard</h1>
                    </div>
                </div>
            </div>
        </div>

        <div class="content mt-3">
            <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-flat-color-1">
                    <div class="card-body pb-0">
                        <div class="dropdown float-right">
                            <i class="fa fa-bell"></i>
                        </div>
                        <h4 class="mb-0">
                            <span
                                class="count"><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from customer where status=0")); ?></span>
                        </h4>
                        <p class="text-light">Customers</p>

                        <div class="chart-wrapper px-0" style="height:70px;" height="70">

                        </div>

                    </div>

                </div>
            </div>
            <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-flat-color-2">
                    <div class="card-body pb-0">
                        <div class="dropdown float-right">
                            <i class="fa fa-car"></i>
                        </div>
                        <h4 class="mb-0">
                            <span
                                class="count"><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from driver where status=0")); ?></span>
                        </h4>
                        <p class="text-light">Driver</p>

                        <div class="chart-wrapper px-0" style="height:70px;" height="70">

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-flat-color-3">
                    <div class="card-body pb-0">
                        <div class="dropdown float-right">
                            <i class="fa fa-truck"></i>
                        </div>
                        <h4 class="mb-0">
                            <span
                                class="count"><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from supplier where status=0")); ?></span>
                        </h4>
                        <p class="text-light">Supplier</p>

                    </div>

                    <div class="chart-wrapper px-0" style="height:70px;" height="70">

                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-lg-3">
                <div class="card text-white bg-flat-color-4">
                    <div class="card-body pb-0">
                        <div class="dropdown float-right">
                            <i class="fa fa-group"></i>
                        </div>
                        <h4 class="mb-0">
                            <span
                                class="count"><?php echo mysqli_num_rows(mysqli_query($db, "SELECT * from employee where status=0")); ?></span>
                        </h4>
                        <p class="text-light">Employees</p>

                        <div class="chart-wrapper px-3" style="height:70px;" height="70">

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


    <script src="vendors/chart.js/dist/Chart.bundle.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script src="assets/js/widgets.js"></script>
    <script src="vendors/jqvmap/dist/jquery.vmap.min.js"></script>
    <script src="vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
    <script src="vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
    <script>
    (function($) {
        "use strict";

        jQuery('#vmap').vectorMap({
            map: 'world_en',
            backgroundColor: null,
            color: '#ffffff',
            hoverOpacity: 0.7,
            selectedColor: '#1de9b6',
            enableZoom: true,
            showTooltip: true,
            values: sample_data,
            scaleColors: ['#1de9b6', '#03a9f5'],
            normalizeFunction: 'polynomial'
        });
    })(jQuery);
    </script>

</body>

</html>
<?php } ?>