const enableGoBackBtns = () => {
    const goBackBtns = document.querySelectorAll('.go-back-btn');
    goBackBtns.forEach((btn) => {
        btn.addEventListener('click', () => {
            window.history.back();
        });
    });
};

const getHabitadispoByPiso = ({ target }) => {                //obtiene el piso seleccionado
    const selectInput = document.querySelector('#habitacion');
    if (target.value) {
        fetch(`/Habita/opciones?piso=${target.value}`, {      //value del HabitacionController,mira como pasa el parametro
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta
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


