window.onload = async () => {
    let response = await fetch("/getAllCurrencies", {method: "GET"});
    let result = await response.json();
    for(let i = 0; i < result.length; i++){
        result[i].update_date = result[i].update_date.substring(0, 10);
    }
    let select1 = document.getElementById("from");
    let select2 = document.getElementById("to");

    for(let i = 0; i < result.length; i++){
        let option = document.createElement("option");
        option.textContent = result[i].name;
        let option2 = document.createElement("option");
        option2.textContent = result[i].name;
        select1.appendChild(option);
        select2.appendChild(option2)
    }
};

document.getElementById("exchange").addEventListener("submit", async function (e) {
    e.preventDefault();
    let select1 = document.getElementById("from");
    let select2 = document.getElementById("to");
    let currencyFrom = select1.options[select1.selectedIndex].textContent;
    let currencyTo = select2.options[select2.selectedIndex].textContent;
    let data = {currencyFrom, currencyTo};

    let response = await fetch("/converter.html/exchange", {method: "POST", headers: {
            'Content-Type': 'application/json;charset=utf-8'},
        body: JSON.stringify(data)}
    );

    let answer = await response.json();
    let rate1 = answer[0];
    let rate2 = answer[1];
    rate1 = Math.round(rate1 * 10000) / 10000;
    rate2 = Math.round(rate2 * 10000) / 10000;
    let input = document.getElementById("input1").value;
    document.getElementById("input2").value = Math.round(input * rate1 / rate2 * 10000) / 10000;
});