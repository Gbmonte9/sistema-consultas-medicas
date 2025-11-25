async function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const errorMsg = document.getElementById("errorMsg");

    // Reseta a mensagem de erro
    errorMsg.style.display = "none";

    if (!email || !password) {
        errorMsg.textContent = "Preencha todos os campos.";
        errorMsg.style.display = "block";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            errorMsg.textContent = "E-mail ou senha incorretos.";
            errorMsg.style.display = "block";
            return;
        }

        const data = await response.json();

        // Salva o token JWT
        localStorage.setItem("token", data.token);

        // Redireciona por tipo de usuário
        if (data.tipo === "ADMIN") {
            window.location.href = "/admin/dashboard";
        } else if (data.tipo === "MEDICO") {
            window.location.href = "/doctor/dashboard";
        } else {
            window.location.href = "/patient/dashboard";
        }

    } catch (error) {
        console.error("Erro:", error);
        errorMsg.textContent = "Erro ao conectar com o servidor.";
        errorMsg.style.display = "block";
    }

}
