import { validateName, validatePassword } from './validation';

window.signUp = function () {
    var nameInput = document.getElementById('nameInput');
    var passwordInput = document.getElementById('passwordInput');

    var errorMessages = [];

    if (!validatePassword(passwordInput.value)) {
        errorMessages.push('Invalid password. Password must contain at least one uppercase letter, one lowercase letter, one number, one symbol, and have a length between 8 and 10 characters.');
    }

    var nameValid = validateName(nameInput.value);
    if (nameValid.length > 0) {
        errorMessages = [...errorMessages, ...nameValid]
    }

    if (errorMessages.length > 0) {
        alert(errorMessages.join('\n'));
        return;
    }

    window.location.assign("pages/homePage.html");
}