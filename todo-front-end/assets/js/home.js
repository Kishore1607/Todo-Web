function generateTaskList(tasks) {
    const ulElement = document.createElement('ul');
    ulElement.classList.add('responsive-table');

    tasks.forEach((task, index) => {
        const liElement = document.createElement('li');
        liElement.classList.add('table-row');

        const taskName = task.task;
        const dueDate = task.duedate;
        const priorityClass = `Pr-${task.status}`;
        const statusClass = `status${task.status}`;

        const innerHTML = `
            <div class="col col-1" data-label="Task Name">${taskName}</div>
            <div class="col col-2" data-label="Due Date">${dueDate}</div>
            <div class="col col-3" data-label="Priority"><span class="${priorityClass}">${taskName}</span></div>
            <div class="col col-4" data-label="Status"><span class="${statusClass}">Completed</span></div>
        `;

        liElement.innerHTML = innerHTML;
        ulElement.appendChild(liElement);
    });

    document.body.appendChild(ulElement);
}


generateTaskList(tasksArray);
