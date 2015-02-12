function checkForm() {
        var name=$("#name").val();		
		var email = $("#email").val();
		var city = $("#city").val();
		var balance = parseFloat($("#balance").val());
		
        if (name.length == 0) {
            $("#nameError").html("Пожалуйста, укажите имя");
            return false;
        }
		
		var nameArr = name.split(" ");
		if (nameArr.length != 2) {
			$("#nameError").html("Пожалуйста, укажите имя");
			return false;
		}
		
		for(var i=0;i<nameArr.length;i++) {
        var n = nameArr[i];
            if (n.length == 0) {
                $("#nameError").html("Пожалуйста, укажите Имя и Фамилию");
                return false;
            }
        }  
		
		if (city.length == 0) {
            $("#cityError").html("Пожалуйста, укажите город");
            return false;
        }
		
        if (!email.match(".+@.+\.[a-zA-Z]{2,3}")) {
            $("#emailError").html("неверный формат e-mail");
            return false;
        }
		
		if ( balance != null && balance < 0.0) {
            $("#balanceError").html("Сумма должна быть положительной");
            return false;
        }
		if (isNaN(balance)) {
            $("#balanceError").html("Пожалуйста, введите сумму");
            return false;
        }
			
		
		$("#nameError").html("");
		$("#emailError").html("");
		$("#cityError").html("");
        return true;
}