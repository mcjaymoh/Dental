<?php

include("config.php");
include("functions.php");
if (isset($_GET['investmentid'])){
    $investmentid = $_GET['investmentid'];
	$clsGlobalObj = new clsGlobal;

	$response = array();
	$query_search = "select * from yia_ideas where investmentid = $investmentid;";
	$query_exec = mysqli_query($db_handle, $query_search);
	$count = 0;
    if (!empty($query_exec )) {
        // check for empty result
        if (mysqli_num_rows($query_exec ) > 0) {
            // user node
			$response["ideas"] = array();
			while ($row = mysqli_fetch_array($query_exec)) {
				$ideas = array();
				$ideas["ideaid"] = $row ["ideaid"];
				$ideas["title"] = $row ["title"];
				$ideas["details"] = $row ["details"];
				$ideas["status"] = $row ["status"];
				if($count%2 == 0){
					$ideas["details"] = substr($row ["details"], 0, 150) . '...';
				}
				else{
					$ideas["details"] = substr($row ["details"], 0, 300) . '...';
				}
				
				array_push($response["ideas"], $ideas);
				
				$count ++;
			}
			$response["success"] = 1;
			$response["message"] = "Found ideas";
            // echoing JSON response
            echo json_enCode($response);
        } else {
            // no ideas found
            $response["success"] = 0;
            $response["message"] = "No ideas found";

            // echo no users JSON
            echo json_enCode($response);
        }
    } else {
        // no ideas found
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