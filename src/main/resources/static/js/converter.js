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
    /*
    let response = await fetch("/exchange", {method: "POST", headers: {
            'Content-Type': 'application/json;charset=utf-8'},
        body: JSON.stringify();
    )
     */
});