<?php
include("config.php");
include("functions.php");
if (isset($_GET['investmentid'])){
	$clsGlobalObj = new clsGlobal;
    $investmentid = $_GET['investmentid'];
	$query_search = "select description from yia_investments where investmentid = $investmentid;";
	$query_exec = mysqli_query($db_handle, $query_search);

    if (!empty($query_exec )) {
        // check for empty result
		if ($row = mysqli_fetch_array($query_exec)) {
			$response["success"] = 1;
			$response["message"] = "Found Data";

			$response["description"] = $row ["description"];
			$response["ideascount"] = $clsGlobalObj->getInvestmenttestimonialscount($investmentid);
		}else{
			$response["success"] = 0;
			$response["message"] = "No Data! Something went wrong!!!";
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
    