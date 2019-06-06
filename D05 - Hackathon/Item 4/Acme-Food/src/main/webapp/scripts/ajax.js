function watchMore(restaurantId) {
	$.ajax({
		type : 'GET',
		url : 'finder/customer/watch-more?restaurantId=' + restaurantId,
		success : function(res) {
			alert(res);
		}
	});
}
