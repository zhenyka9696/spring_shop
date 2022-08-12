let selectColor =  $("#modalAdd #acolor");
for (let i=0; i<colors.length; i++){
    let option = $('<option>');
    option.html(colors[i]);
    selectColor.append(option);
}

//При сохранении новой книги (нажатии на кнопку OK)
$(document).on("click", "#buttonAdd", async function () {
    let name = $("#modalAdd #aname").val();
    let color = $("#modalAdd #acolor").val();
    let price = $("#modalAdd #aprice").val();
    let priceNew = $("#modalAdd #apriceNew").val();

    const res = await
        fetch(`http://localhost:8080/jewelries`,
            {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({name, color, price, priceNew})
            });

    $("#modalAdd").modal().hide();
    location.reload();
    //$("#modalUpdate #cancel").click()
})