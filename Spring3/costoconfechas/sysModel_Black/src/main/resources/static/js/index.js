const enableGoBackBtns = () => {
    const goBackBtns = document.querySelectorAll('.go-back-btn');
    goBackBtns.forEach((btn) => {
        btn.addEventListener('click', () => {
            window.history.back();
        });
    });
};

const getHabitadispoByFecha = ({ target }) => {                //obtiene fechadeingreso seleccionada
    const selectInput = document.querySelector('#habitacion');
    if (target.value) {
        fetch(`/Habita/opciones?fecha=${target.value}`, {      //value del HabitacionController,mira como pasa el parametro fecha
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del controller
            .then((data) => {
                selectInput.innerHTML = data;
                selectInput.disabled = false;
            });
    } else {
        selectInput.disabled = false;
    }
    //
    if (target.value) {
        fetch(`/Clients/cost?fechaingreso=${target.value}`, {      //envio la fechaingreso
            method: 'get'
        });       
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

const getCostodeAlojamiento = ({ target }) => {                               //obtiene fechasalida  seleccionada
    const selectInputx = document.querySelector('#costo_alojamiento');    
    if (target.value) {
        fetch(`/Clients/costo?fechasalida=${target.value}`, {      //value del ClientsController
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del controller
            .then((data) => {
                selectInputx.innerHTML = data;
                selectInputx.disabled = false;
            });
    } else {
        selectInputx.disabled = true;
    }
};

const generarpdf = ({ target }) => {             //no uso    
    
    if (target.value) {
        fetch(`/Reserva/${aidi}/reserva_detalle/?format=pdf`, {      
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del controller
            .then((data) => {
                
            });
    } else {
        
    }
};

//En Registrar alquiler, pa definir costo total,deudaaloja y deuda total
const definecostototal = ({ target }) => {                //en Registrar Alquiler: obtiene costo de alojamiento
    const selectInput1 = document.querySelector('#costototal');
    const selectInput2 = document.querySelector('#deuda');
    const selectInput3 = document.querySelector('#deudaaloja');
    if (target.value) {
        fetch(`/Alquiler/opciones?costo=${target.value}`, {      
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del Alquilercontroller
            .then((data) => {
                selectInput1.innerHTML = data;
                selectInput1.disabled = false;
                selectInput2.innerHTML = data;
                selectInput2.disabled = false;
                selectInput3.innerHTML = data;
                selectInput3.disabled = false;
            });
    } else {
        selectInput1.disabled = false;
        selectInput2.disabled = false;
        selectInput3.disabled = false;
    }   
};

//En Editar alquiler, pa actualizar costototal y la deudaloja y deuda total al cambiar costo_aloja
const actualizacostototal = ({ target }) => {                // obtiene el nuevo costo_alojamiento
    const selectInput4 = document.querySelector('#costototal');    
    const selectInput5 = document.querySelector('#deuda'); 
    const selectInput6 = document.querySelector('#deudaaloja'); 
    if (target.value) {
        fetch(`/Alquiler/acostototal?costoaloja=${target.value}`, {      
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del Alquilercontroller
            .then((data) => {
                selectInput4.innerHTML = data;
                selectInput4.disabled = false;                
            });
    } else {
        selectInput3.disabled = false;       
    }  
    //
    if (target.value) {
        fetch(`/Alquiler/adeudatotal?costoaloja=${target.value}`, {      
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del Alquilercontroller
            .then((data) => {
                selectInput5.innerHTML = data;
                selectInput5.disabled = false;                
            });
    } else {
        selectInput4.disabled = false;       
    }   
    //
    if (target.value) {
        fetch(`/Alquiler/adeudaloja?costoaloja=${target.value}`, {      
            method: 'get'
        }).then((res) => res.text())                   //obtiene una respuesta del Alquilercontroller
            .then((data) => {
                selectInput6.innerHTML = data;
                selectInput6.disabled = false;                
            });
    } else {
        selectInput6.disabled = false;       
    }   
     
};
//

window.addEventListener('load', () => {    
    enableGoBackBtns();
});


