function alertMsg(String Msg){
    const alertMessage = document.getElementById("alertMsg")?.value || "";
    if (alertMessage) {
        alert(alertMessage);
    }
}
