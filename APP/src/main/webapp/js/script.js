(function () {
	'use strict';
	
	var forms = document.querySelectorAll('.needs-validation');
		
	Array.prototype.slice.call(forms)
		.forEach(function (form) {
			form.addEventListener('submit', function (event) {
				if (!form.checkValidity()) {
				event.preventDefault();
				event.stopPropagation();
			}	
	
			form.classList.add('was-validated');
		}, false);
	});
})();

$(document).ready(function() {
    $('#form').attr('novalidate', '');

    $('#email, #password, #firstName, #lastName').attr('required', '');
});

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("button[data-book-id]").forEach(button => {
        button.addEventListener("click", function () {
            const bookId = this.getAttribute("data-book-id");
            reserveBook(bookId);
        });
    });
});

function extendLoan(loanId) {
    const url = `/loan/${loanId}/extend`;

	fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(data => window.location.href = '/profile');
}

function reserveBook(bookId) {
    const url = `/reservation/create/${bookId}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(data => window.location.href = '/profile');
}

function cancelReservation(reservationId) {
    const url = `/reservations/${reservationId}`;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(data => window.location.href = '/profile');
}