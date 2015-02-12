function checkForm() {
        var name=$("#name").val();
        if (name.length == 0) {
            $("#nameError").html("Пожалуйста, укажите имя");
            return false;
        }
		$("#nameError").html("");
        return true;
}