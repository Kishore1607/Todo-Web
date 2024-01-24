import { validateName, validatePassword } from './validation.js';

const form = document.getElementById('form');
form.addEventListener('submit', async function (event) {
    event.preventDefault(); 
    var nameInput = document.getElementById('nameInput');
    var passwordInput = document.getElementById('passwordInput');
    var confPassword = document.getElementById('confPasswordInput');

    var errorMessages = [];

    if (!validatePassword(passwordInput.value)) {
        errorMessages.push('* Invalid password. Password must contain at least one uppercase letter, one lowercase letter, one number, one symbol, and have a length between 8 and 10 characters.');
    }

    if (passwordInput.value !== confPassword.value) {
        errorMessages.push('* Password and Confirmed password should match.');
    }

    var nameValid = validateName(nameInput.value);

    if (nameValid.length > 0) {
        errorMessages = [...errorMessages, ...nameValid]
    }

    if (errorMessages.length > 0) {
        alert(errorMessages.join('\n'));
        return;
    }

    try {
        const response = await fetch("http://localhost:8081/users/register", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: nameInput.value,
                password: passwordInput.value,
            }),
        });
    
        console.log('Response:', response);
    
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
    
        const data = await response.json();
        console.log('Data:', data);
    
        sessionStorage.setItem("user", JSON.stringify(data));
        window.location.href = "../pages/homePage.html";
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    form.reset();
});