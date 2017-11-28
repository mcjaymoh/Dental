<?php

include("config.php");
include("functions.php");

	$clsGlobalObj = new clsGlobal;

	$response = array();
	$query_search = "select * from yia_investments where status like 'active';";
	$query_exec = mysqli_query($db_handle, $query_search);
	$count = 0;
    if (!empty($query_exec )) {
        // check for empty result
        if (mysqli_num_rows($query_exec ) > 0) {
            // user node
			$response["investments"] = array();
			while ($row = mysqli_fetch_array($query_exec)) {
				$investments = array();
				$investments["id"] = $row ["investmentid"];
				$investments["title"] = $row ["title"];
				$investments["status"] = $row ["status"];
				$investments["department"] = $row ["department"];
				$investments["category"] = $row ["category"];
				if($count%2 == 0){
					$investments["description"] = substr($row ["description"], 0, 150) . '...';
				}
				else{
					$investments["description"] = substr($row ["description"], 0, 300) . '...';
				}
				
				array_push($response["investments"], $investments);
				
				$count ++;
			}
			$response["success"] = 1;
			$response["message"] = "Found investments";
            // echoing JSON response
            echo json_enCode($response);
        } else {
            // no investments found
            $response["success"] = 0;
            $response["message"] = "No investments found";

            // echo no users JSON
            echo json_enCode($response);
        }
    } else {
        // no investments found
        $response["success"] = 0;
        $response["message"] = "Error in Querry";

        // echo no users JSON
        echo json_enCode($response);
    }

mysqli_close($db_handle);
?>