function isNumeric() {
        var sum=parseFloat($("#sum").val());
		
        if ( sum != null && sum < 0.0) {
            $("#sumError").html("Сумма должна быть положительной");
            return false;
        }
		if (isNaN(sum)) {
            $("#sumError").html("Пожалуйста, введите сумму");
            return false;
        }
		$("#sumError").html("");
        return true;
}