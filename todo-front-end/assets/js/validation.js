
function countAlphabets(str) {
    const alphabetPattern = /[A-Za-z]/g;
    const matches = str.match(alphabetPattern);
    return matches ? matches.length : 0;
}

function countSpaces(str) {
    const spacePattern = /\s/g;
    const matches = str.match(spacePattern);
    return matches ? matches.length : 0;
}

function validateName(name) {
    name = name.trim();

    var errorMessages = [];

    if (name.length > 50) {
        errorMessages.push('* Name should be within 50 characters.');
    }

    if (countAlphabets(name) < 3) {
        errorMessages.push('* Alphabets must contain at least 3 letters.');
    }

    if (countSpaces(name) > 3) {
        errorMessages.push('* Only 3 spaces allowed.');
    }

    return errorMessages;
}

function validatePassword(password) {
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+\-]).{8,24}$/.test(password);
}

function validPriority(priority) {
    return priority.trim() !== '';
}

function validDate(dateString) {
    const currentDate = new Date();
    const inputDate = new Date(dateString);

    return inputDate > currentDate;
}

function validTaskName(name) {
    return name.trim() !== '';
}

export { validateName, validatePassword, validPriority, validDate, validTaskName };
