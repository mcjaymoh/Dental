<?php
class clsGlobal{
	function datestring($date){
		$dayvalue = date('d', strtotime($date));

		$day = date('D', strtotime($date));

		$month = date('M', strtotime($date)); 

		$year = date('Y', strtotime($date));
			
		return ( $day . ' ' .  $dayvalue . ' ' . $month . ' ' . $year);
	}
	
	function getInvestmenttestimonialscount($investmentid){
		include("config.php");
		$query_search = "select count(ideaid) as _count from yia_ideas where investmentid = $investmentid;";
		$query_exec = mysqli_query($db_handle, $query_search);

		if (!empty($query_exec )) {
			// check for empty result
			if ($row = mysqli_fetch_array($query_exec)) {
				$countvalue = $row ["_count"];
			}else{
				$countvalue = 0;
			}
		} else {
			$countvalue = 0;
		}
		return 'There are (' . $countvalue . ' ideas) on this.' ;
	}
}
?>