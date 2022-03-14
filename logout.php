<?php
                                                                                            session_start();
                                                                                            unset($_SESSION['userAdmin']);
                                                                                            echo "<script>location.href='logger' </script>";