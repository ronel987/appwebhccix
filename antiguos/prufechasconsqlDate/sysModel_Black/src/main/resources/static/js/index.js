const enableGoBackBtns = () => {
    const goBackBtns = document.querySelectorAll('.go-back-btn');
    goBackBtns.forEach((btn) => {
        btn.addEventListener('click', () => {
            window.history.back();
        });
    });
};

const getHabitadispoByFecha = ({ target }) => {                //obtiene fecha de ingreso seleccionada
    const selectInput = document.querySelector('#habitacion');
    if (target.value) {
        fetch(`/Habita/opciones?fecha=${target.value}`, {      //value del HabitacionController,mira como pasa el parametro
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del controller
            .then((data) => {
                selectInput.innerHTML = data;
                selectInput.disabled = false;
            });
    } else {
        selectInput.disabled = false;
    }
};

const getHabitadispoByFechayPiso = ({ target }) => {                //obtiene piso  seleccionado
    const selectInput = document.querySelector('#habitacion');    
    if (target.value) {
        fetch(`/Habita/fechaypiso?piso=${target.value}`, {      //value del HabitacionController,mira como pasa el parametro
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del controller
            .then((data) => {
                selectInput.innerHTML = data;
                selectInput.disabled = false;
            });
    } else {
        selectInput.disabled = true;
    }
};



window.addEventListener('load', () => {    
    enableGoBackBtns();
});


