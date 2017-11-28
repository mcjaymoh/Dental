<?php
include("config.php");

$response = array();

// check for required fields
if (isset($_POST['department']) && isset($_POST['category']) && isset($_POST['title']) && isset($_POST['description'])) {
		
	$department = $_POST['department'];
    $category = $_POST['category'];
    $title= $_POST['title'];
	$description = $_POST['description'];
	
	

	$query_search = "insert into yia_investments (department, category, title, description, status) 
	values ('$department', '$category', '$title',  '$description','active')";

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