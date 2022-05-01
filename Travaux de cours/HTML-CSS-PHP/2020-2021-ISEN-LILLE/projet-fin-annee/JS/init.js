document.addEventListener("DOMContentLoaded", function () {
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl)
    })

    initFormValidation();
});


//fonction pour initialiser la validation des formulaires
function initFormValidation() {

    // Récupérez tous les formulaires auxquels nous voulons appliquer des styles de validation
    let forms = document.querySelectorAll('.needs-validation');

    // Bouclez sur eux et empêchez l'envoi du formulaire
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
}

function cleanInput(input) {
    input.value = input.value.replaceAll(" ", "");
}