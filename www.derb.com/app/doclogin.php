<?php
include("config.php");

if (isset($_GET['email']) && isset($_GET['password'])){
    $email = $_GET['email'];
	$password = $_GET['password'];
	
	$query_search = "select * from doctor where email like '$email' and password like '$password';";
	$query_exec = mysqli_query($db_handle, $query_search);

    if (!empty($query_exec )) {
        // check for empty result
		if ($row = mysqli_fetch_array($query_exec)) {
				$response["success"] = 1;
				$response["firstname"] = $row ["firstname"];
				$response["middlename"] = $row ["middlename"];
				$response["lastname"] = $row ["lastname"];
				$response["idpassport"] = $row ["nationalid"];
				$response["mobile"] = $row ["mobile"];
			}else{
				$response["success"] = 0;
				$response["message"] = "No Account";
			}
            // echoing JSON response
        echo json_enCode($response);
   
    } else {
        // no details found
        $response["success"] = 0;
        $response["message"] = "Error in Querry";
        // echo no users JSON
        echo json_enCode($response);
    }
}else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Error!!! You cannot do that!! Some data is needed";
    // echoing JSON response
    echo json_enCode($response);
}
mysqli_close($db_handle);
?>
    