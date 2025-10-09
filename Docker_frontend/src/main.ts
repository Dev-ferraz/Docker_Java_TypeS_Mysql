console.log("Frontend carregado!");

document.addEventListener("DOMContentLoaded", () => {
  console.log("Documento pronto");

  const titulo = document.querySelector("title");
  if (titulo) {
    titulo.textContent += " - Vite Ativo";
  }
});
