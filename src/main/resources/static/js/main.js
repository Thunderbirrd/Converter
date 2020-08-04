const app = new Vue({
    el: "#register",
    data: {
        errors: [],
        login: null,
        password: null,
        password2: null
    },
    methods: {
        checkForm: function (e) {
            this.errors = [];
            if(this.password.length < 8 || this.password.length > 32){
                this.errors.push("Пароль должен быть от 8 до 32 символов");
            }
            if(this.password !== this.password2){
                this.errors.push("Пароли не совпадают")
            }

            e.preventDefault();

            if(this.errors.length === 0){
                let data = JSON.stringify({login: this.login, password: this.password});
                let response = fetch("/login", {method: "Post", body: data, headers: {'Content-type' : 'application/json;charset=utf-8'}});
                let result = response.json()
            }else{
                console.log(this.errors[0])
            }
        }
    }
});