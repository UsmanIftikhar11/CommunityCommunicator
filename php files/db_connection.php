<?php
    /**
    *Database config variables,
    */
    define("DB_HOST","local host"); 
    define("DB_USER","root");
    define("DB_PASSWORD","");
    define("DB_DATABASE","community");
 
    $connection = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
 
    if(mysqli_connect_errno()){
        die("Database connection failed " . "(" .
            mysqli_connect_error() . " - " . mysqli_connect_errno() . ")"
                );
    }
?>