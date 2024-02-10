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
        fetch(`/Habita/opciones?piso=${target.value}`, {
            method: 'get'
        }).then((res) => res.text())
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


