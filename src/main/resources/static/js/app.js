/**
 * EsportNabor — Klientská validace formuláře a UX vylepšení
 */
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registrationForm');
    if (!form) return;

    // ─── Plynulý scroll pro kotevní odkazy ───
    document.querySelectorAll('a[href^="#"]').forEach(link => {
        link.addEventListener('click', e => {
            const target = document.querySelector(link.getAttribute('href'));
            if (target) {
                e.preventDefault();
                target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });

    // ─── Pozadí navbaru při scrollu ───
    const navbar = document.querySelector('.navbar');
    if (navbar) {
        window.addEventListener('scroll', () => {
            navbar.style.background = window.scrollY > 40
                ? 'rgba(11, 14, 23, 0.92)'
                : 'rgba(11, 14, 23, 0.75)';
        }, { passive: true });
    }

    // ─── Klientská validace ───
    form.addEventListener('submit', e => {
        clearErrors();
        let valid = true;

        // Povinná textová pole
        const requiredFields = {
            'firstName': 'Jméno je povinné.',
            'lastName': 'Příjmení je povinné.',
            'phoneNumber': 'Telefonní číslo je povinné.'
        };

        Object.entries(requiredFields).forEach(([id, msg]) => {
            const input = document.getElementById(id);
            if (!input.value.trim()) {
                showError(input, msg);
                valid = false;
            }
        });

        // E-mail
        const email = document.getElementById('email');
        if (!email.value.trim()) {
            showError(email, 'E-mail je povinný.');
            valid = false;
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value.trim())) {
            showError(email, 'Zadejte prosím platnou e-mailovou adresu.');
            valid = false;
        }

        // Datum narození
        const dob = document.getElementById('dateOfBirth');
        if (!dob.value) {
            showError(dob, 'Datum narození je povinné.');
            valid = false;
        } else {
            const age = getAge(new Date(dob.value));
            if (age < 18) {
                showError(dob, 'Musíte být starší 18 let.');
                valid = false;
            }
        }

        if (!valid) {
            e.preventDefault();
            const firstError = form.querySelector('.is-invalid');
            if (firstError) firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    });

    // ─── Živé mazání chyb při psaní ───
    form.querySelectorAll('.form-control').forEach(input => {
        input.addEventListener('input', () => {
            input.classList.remove('is-invalid');
            const errEl = input.parentElement.querySelector('.js-error');
            if (errEl) errEl.remove();
        });
    });

    // ─── Pomocné funkce ───
    function showError(input, message) {
        input.classList.add('is-invalid');
        if (!input.parentElement.querySelector('.field-error:not(.js-error)')) {
            const p = document.createElement('p');
            p.className = 'field-error js-error';
            p.textContent = message;
            input.parentElement.appendChild(p);
        }
    }

    function clearErrors() {
        form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
        form.querySelectorAll('.js-error').forEach(el => el.remove());
    }

    function getAge(birthday) {
        const today = new Date();
        let age = today.getFullYear() - birthday.getFullYear();
        const m = today.getMonth() - birthday.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthday.getDate())) age--;
        return age;
    }
});
