window.onload = async () => {
    if(document.cookie === ""){
        window.location = "/"
    }
    let response = await fetch("/getAllCurrencies", {method: "GET"});
    let result = await response.json();
    for(let i = 0; i < result.length; i++){
        result[i].update_date = result[i].update_date.substring(0, 10);
    }
    let select1 = document.getElementById("from");
    let select2 = document.getElementById("to");
    let select3 = document.getElementById("currency1");
    let select4 = document.getElementById("currency2");
    let option = document.createElement("option");
    let option2 = document.createElement("option");
    option.textContent = ""; option2.textContent = "";
    select3.appendChild(option); select4.appendChild(option2);

    for(let i = 0; i < result.length; i++){
        let option = document.createElement("option");
        option.textContent = result[i].name;
        let option2 = document.createElement("option");
        option2.textContent = result[i].name;
        let option3 = document.createElement("option");
        option3.textContent = result[i].name;
        let option4 = document.createElement("option");
        option4.textContent = result[i].name;
        select1.appendChild(option);
        select2.appendChild(option2);
        select3.appendChild(option3);
        select4.appendChild(option4);
    }
};

document.getElementById("exchange").addEventListener("submit", async function (e) {
    e.preventDefault();
    let select1 = document.getElementById("from");
    let select2 = document.getElementById("to");
    let input = document.getElementById("input1").value;
    let currencyFrom = select1.options[select1.selectedIndex].textContent;
    let currencyTo = select2.options[select2.selectedIndex].textContent;
    let userId = document.cookie;
    let data = {currencyFrom, currencyTo, userId, input};

    let response = await fetch("/converter.html/exchange", {method: "POST", headers: {
            'Content-Type': 'application/json;charset=utf-8'},
        body: JSON.stringify(data)}
    );

    let answer = await response.json();
    let rate1 = answer[0];
    let rate2 = answer[1];
    rate1 = Math.round(rate1 * 10000) / 10000;
    rate2 = Math.round(rate2 * 10000) / 10000;
    document.getElementById("input2").value = Math.round(input * rate1 / rate2 * 10000) / 10000;
});


document.getElementById("search").addEventListener("submit", async function (e) {
    e.preventDefault();
    let date = document.getElementById("date").value;
    console.log(date);
    let currency1 = document.getElementById("currency1").value;
    let currency2 = document.getElementById("currency2").value;
    let userId = document.cookie;
    let data = {date, currency1, currency2, userId};

    let response = await fetch("/converter.html/search", {method: "POST", headers: {
            'Content-Type': 'application/json;charset=utf-8'},
        body: JSON.stringify(data)});

    let answer = await response.json();
    let tbody = document.getElementById("history");
    tbody.innerHTML = "";
    for(let i = 0; i < answer.length; i++){
        let tr = document.createElement("tr");
        let td = document.createElement("td");
        td.textContent = answer[i].currency1;
        tr.appendChild(td);
        td = document.createElement("td");
        td.textContent = answer[i].currency2;
        tr.appendChild(td);
        td = document.createElement("td");
        td.textContent = String(answer[i].value1);
        tr.appendChild(td);
        td = document.createElement("td");
        td.textContent = String(answer[i].value2);
        tr.appendChild(td);
        td = document.createElement("td");
        td.textContent = answer[i].date.substring(0, 10);
        tr.appendChild(td);
        tbody.appendChild(tr)
    }

});