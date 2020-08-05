window.onload = async () => {
    let response = await fetch("/getAllCurrencies", {method: "GET"});
    let result = await response.json();

};