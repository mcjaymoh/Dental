<?php

include("config.php");

	$response = array();
	$query_search = "select distinct(department) from yia_investments order by department asc";
	$query_exec = mysqli_query($db_handle, $query_search);
	$count = 0;
    if (!empty($query_exec )) {
        // check for empty result
        if (mysqli_num_rows($query_exec ) > 0) {
            // user node
			$response["departmentdetails"] = array();
			
			while ($row = mysqli_fetch_array($query_exec)) {
				$departmentdetails = array();
				if($count==0){
					$departmentdetails["department"] = 'All';
				}else{
					$departmentdetails["department"] = $row ["department"];
				}
				array_push($response["departmentdetails"], $departmentdetails);
				$count ++;
			}
			$response["success"] = 1;
			$response["message"] = "Found " . $count . " Investment departments";
            // echoing JSON response
            echo json_enCode($response);
        } else {
            // no departmentdetails found
            $response["success"] = 0;
            $response["message"] = "No Investment departments Found";

            // echo no users JSON
            echo json_enCode($response);
        }
    } else {
        // no departmentdetails found
        $response["success"] = 0;
        $response["message"] = "Error in MySQL query";

        // echo no users JSON
        echo json_enCode($response);
    }
mysqli_close($db_handle);
?>