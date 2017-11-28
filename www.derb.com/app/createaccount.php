<?php
include("config.php");

$response = array();

// check for required fields
if (isset($_POST['surname']) && isset($_POST['middlename']) && isset($_POST['lastname']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['idpassport']) && isset($_POST['mobile']) && isset($_POST['gender'])) {
		
	$surname = $_POST['surname'];
    $middlename = $_POST['middlename'];
    $lastname= $_POST['lastname'];
	$gender = $_POST['gender'];
	$idpassport = $_POST['idpassport'];
	$mobile = $_POST['mobile'];
	$email = $_POST['email'];
	$password = $_POST['password'];

	$query_search = "insert into yia_clients (firstname, middlename, lastname, gender, nationalid, mobile, email, password, status) 
	values ('$surname', '$middlename', '$lastname',  '$gender', '$idpassport', '$mobile', '$email', '$password', 'active')";

	$query_exec = mysqli_query($db_handle, $query_search);
	
    // check if row inserted or not
    if ($query_exec) {
		// successfully inserted into database
		$response["success"] = 1;
		$response["message"] = "Success";
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing Man";
    // echoing JSON response
    echo json_encode($response);
}
?>		