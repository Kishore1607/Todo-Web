// validation.js

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
        errorMessages.push('Name should be within 50 characters.');
    }

    if (countAlphabets(name) < 3) {
        errorMessages.push('Alphabets must contain at least 3 letters.');
    }

    if (countSpaces(name) > 3) {
        errorMessages.push('Only 3 spaces allowed.');
    }

    return errorMessages;
}

function validatePassword(password) {
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,10}$/.test(password);
}

// Export the functions
export { validateName, validatePassword };
