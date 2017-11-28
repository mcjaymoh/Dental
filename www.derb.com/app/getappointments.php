<?php

include("config.php");
include("functions.php");

	$clsGlobalObj = new clsGlobal;

	$response = array();
	$query_search = "select * from appointments where status like 'active';";
	$query_exec = mysqli_query($db_handle, $query_search);
	$count = 0;
    if (!empty($query_exec )) {
        // check for empty result
        if (mysqli_num_rows($query_exec ) > 0) {
            // user node
			$response["appointments"] = array();
			while ($row = mysqli_fetch_array($query_exec)) {
				$appointments = array();
				$appointments["id"] = $row ["appid"];
				$appointments["names"] = $row ["names"];
				$appointments["number"] = $row ["mobilenumber"];
				$appointments["date"] = $row ["date"];
				$appointments["status"] = $row ["status"];
				if($count%2 == 0){
					$appointments["reason"] = substr($row ["reason"], 0, 150) . '...';
				}
				else{
					$appointments["reason"] = substr($row ["reason"], 0, 300) . '...';
				}
				
				array_push($response["appointments"], $appointments);
				
				$count ++;
			}
			$response["success"] = 1;
			$response["message"] = "Found appointments";
            // echoing JSON response
            echo json_enCode($response);
        } else {
            // no investments found
            $response["success"] = 0;
            $response["message"] = "No appointments found";

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