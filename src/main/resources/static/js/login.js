document.getElementById("login").addEventListener("submit", async function(e) {
    e.preventDefault();
    let login = e.target.elements.login.value;
    let password = e.target.elements.password.value;
    let div = document.getElementById("errors_log");
    let user = {
        login,
        password
    };
    let response = await fetch("/login", {method: "POST",  headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)});
    let result = await response.text();
    console.log(result);
    switch (result) {
        case "Wrong login":
            div.style.display = "";
            div.textContent = "Пользователя с таким логином не сушествует";
            break;
        case "Wrong password":
            div.style.display = "";
            div.textContent = "Неправильный пароль";
            break;
        case "Login successful":
            window.location = "/converter.html";
            break;
    }
});
