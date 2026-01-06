/**
 * 
 */
//Contraseña oculta
document.addEventListener("DOMContentLoaded", function () {
    const contraseñaOculta = document.getElementById("contraseñaOculta");
    const contraseña = document.getElementById("clave");

    if (contraseñaOculta && contraseña) {
        contraseñaOculta.addEventListener("click", function () {
            const type = contraseña.type === "password" ? "text" : "password";
            contraseña.type = type;

            this.classList.toggle("bi-eye");
            this.classList.toggle("bi-eye-slash");
        });
    }
});

//Para ingresar valores numericos
function formatearNumeroEuropeo(input) {
    let valor = input.value.replace(/\D/g, "");

    if (valor === "") {
        input.value = "0,00";
        return;
    }

    valor = valor.replace(/^0+/, "");

    if (valor === "") {
        input.value = "0,00";
        return;
    }

    if (valor.length <= 2) {
        input.value = "0," + valor.padStart(2, "0");
        return;
    }

    let parteDecimal = valor.slice(-2);
    let parteEntera = valor.slice(0, -2);
    parteEntera = parteEntera.replace(/\B(?=(\d{3})+(?!\d))/g, ".");

    input.value = parteEntera + "," + parteDecimal;
}


function obtenerValorNumerico(input) {
    let valor = input.value;
    valor = valor.replace(/\./g, '').replace(',', '.');
    return parseFloat(valor);
}

//Para el balance
window.onload = function () {
    const ctx = document.getElementById('balanceChart').getContext('2d');
    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Transferencias Enviadas', 'Transferencias Recibidas'],
        datasets: [{
          data: [40, 30],
          backgroundColor: ['#3fa9f5', '#6c8dbf'],
          borderWidth: 2
        }]
      },
      options: {
        cutout: '70%',
        plugins: {
          legend: { display: false }
        }
      }
    });
  };
  
  
  function cargarLocalidades() {
      const idProvincia = document.getElementById("ddlProvincia").value;
      const ddlLocalidad = document.getElementById("ddlLocalidad");

      // Evita enviar si no eligió provincia válida
      if (idProvincia === "default") {
          ddlLocalidad.innerHTML = '<option value="default">-- Seleccione su Localidad --</option>';
          return;
      }

      fetch('ServletLocalidades?idProvincia=' + idProvincia)
          .then(response => response.text())
          .then(data => {
              ddlLocalidad.innerHTML = data;
          })
          .catch(error => {
              console.error('Error al cargar localidades:', error);
          });
  }
  
  function validarNombreApellido(formSelector) {
      const form = document.querySelector(formSelector);
      if (!form) return;

      form.addEventListener("submit", function(e) {
          const nombre = form.querySelector("input[name='txtNombre']").value.trim();
          const apellido = form.querySelector("input[name='txtApellido']").value.trim();
          const nombreValido = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/;

          if (!nombreValido.test(nombre) || !nombreValido.test(apellido)) {
              e.preventDefault();
              alert("Nombre y Apellido solo deben contener letras.");
          }
      });
  }

  document.addEventListener("DOMContentLoaded", function () {
      validarNombreApellido("form"); // o un selector más específico como "#formAltaCliente"
  });
  
  document.addEventListener("DOMContentLoaded", function () {
      if (typeof clienteAgregado !== "undefined" && clienteAgregado) {
          Swal.fire({
              icon: 'success',
              title: 'Cliente agregado correctamente',
              showConfirmButton: false,
              timer: 2000
          });
      }
  });
  
  document.addEventListener("DOMContentLoaded", function () {
      const cuotaMensualInput = document.getElementById("cuotaMensual");
      const totalDevolverInput = document.getElementById("totalDevolver");

	  function parseMonto(valor) {
	      return parseFloat(
	          valor
	              .replace(/\s/g, '')
	              .replace('$', '')
	              .replace(/\./g, '')
	              .replace(',', '.')
	      );
	  }

      function formatMoneda(valor) {
          return valor.toLocaleString("es-AR", { style: "currency", currency: "ARS" });
      }

	  function actualizarValores() {
	      const montoStr = montoSeleccionado.value;
	      const cuotas = parseInt(cuotasSeleccionado.value);

	      if (!montoStr || isNaN(cuotas)) return;

	      const monto = parseMonto(montoStr);

	      const montoConInteres = monto * 1.10;
	      const cuotaMensual = montoConInteres / cuotas;

	      cuotaMensualInput.value = formatMoneda(cuotaMensual);
	      totalDevolverInput.value = formatMoneda(montoConInteres);
	  }

      montoSeleccionado.addEventListener("change", actualizarValores);
      cuotasSeleccionado.addEventListener("change", actualizarValores);

      actualizarValores();
  });
  
  
  document.addEventListener("DOMContentLoaded", function () {
        const montoSeleccionado = document.getElementById("montoSeleccionado");
        const cuotasSeleccionado = document.getElementById("cuotasSeleccionado");
        const cuotaMensualInput = document.getElementById("cuotaMensual");
        const totalDevolverInput = document.getElementById("totalDevolver");

        function formatMoneda(valor) {
            return valor.toLocaleString("es-AR", { style: "currency", currency: "ARS" });
        }

        function actualizarValores() {
            const monto = parseFloat(montoSeleccionado.value); // AHORA viene limpio
            const cuotas = parseInt(cuotasSeleccionado.value);

            if (isNaN(monto) || isNaN(cuotas)) return;

            const montoConInteres = monto * 1.10;
            const cuotaMensual = montoConInteres / cuotas;

            cuotaMensualInput.value = formatMoneda(cuotaMensual);
            totalDevolverInput.value = formatMoneda(montoConInteres);
        }

        montoSeleccionado.addEventListener("change", actualizarValores);
        cuotasSeleccionado.addEventListener("change", actualizarValores);

        actualizarValores();
    });