document.addEventListener("DOMContentLoaded", function () {

    const user = JSON.parse(sessionStorage.getItem("user"));
    const container = document.getElementById("container");

    document.getElementById("userName").innerHTML = `Hi ${user.name}! &#128526;`

    const categoryList = document.getElementById("categoryList");
    const lis = categoryList.querySelectorAll("li");

    lis.forEach(li => {
        li.addEventListener("click", function () {
            // Remove "active" class from all lis
            lis.forEach(item => item.classList.remove("active"));

            // Add "active" class to the clicked li
            li.classList.add("active");
        });

        li.addEventListener("mouseover", function () {
            // Show tooltip on mouseover
            li.querySelector(".tooltip").style.visibility = "visible";
            li.querySelector(".tooltip").style.opacity = 1;
        });

        li.addEventListener("mouseout", function () {
            // Hide tooltip on mouseout
            li.querySelector(".tooltip").style.visibility = "hidden";
            li.querySelector(".tooltip").style.opacity = 0;
        });
    });

    //---------------------all list fetch call----------------------//

    async function alltask() {
        try {
            const token = user.entryPass;
            const response = await fetch(`http://localhost:8080/home/tasks/${user.name}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();

            createTable(data);
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error.message);
        }
    }


    function createTable(data) {
        const heading = document.getElementById("heading");
        heading.innerHTML = "Your tasks";
        // Assuming 'container' is a reference to the container element
        const container = document.querySelector('.container');

        // Clear the container
        container.innerHTML = "";

        const table = document.createElement('ul');
        table.classList.add('responsive-table');

        // Create table header
        const headerRow = document.createElement('li');
        headerRow.classList.add('table-header');
        headerRow.innerHTML = `
            <div class="col col-1">Tasks</div>
            <div class="col col-2">Due Date</div>
            <div class="col col-3">Priority</div>
            <div class="col col-4">Status</div>
        `;
        table.appendChild(headerRow);

        // Create table rows
        data.forEach(task => {
            const row = document.createElement('li');
            row.classList.add('table-row');
            row.classList.add(task.status.toLowerCase().replace(/\s/g, '-')); // Add status class

            row.innerHTML = `
                <div class="col col-1" data-label="Task Name">${task.taskName}</div>
                <div class="col col-2" data-label="Due Date">${task.date}</div>
                <div class="col col-3" data-label="Priority"><span class="Pr-${getPriorityClass(task.priority)}">${task.priority}</span></div>
                <div class="col col-4" data-label="Status"><span class="status-${task.status}">${mapStatusToDisplayText(task.status)}</span></div>
            `;
            table.appendChild(row);
        });

        // Append the table to the container
        container.appendChild(table);
    }

    // Function to get priority class
    function getPriorityClass(priority) {
        if (priority.toLowerCase() === 'urgent') return '1';
        if (priority.toLowerCase() === 'medium') return '2';
        if (priority.toLowerCase() === 'low') return '3';
        return '';
    }

    function mapStatusToDisplayText(status) {
        switch (status) {
            case 'Ongoing':
                return 'On going';
            case 'Completed':
                return 'Completed';
            case 'Overdue':
                return 'Overdue';
            // Add more cases as needed
            default:
                return status;
        }
    }

    alltask();

    // //------------------------down arrow-------------------------//
    // var scrollDown = document.getElementById("scrollDown");

    // function updateScrollDownVisibility() {
    //     if (container.scrollHeight > container.clientHeight) {
    //         scrollDown.style.display = "block";
    //     } else {
    //         scrollDown.style.display = "none";
    //     }
    // }

    // function scrollToContent() {
    //     container.scrollIntoView({ behavior: "smooth" });
    // }

    // window.addEventListener("scroll", updateScrollDownVisibility);
    // window.addEventListener("resize", updateScrollDownVisibility);
    // scrollDown.addEventListener("click", scrollToContent);

    // // Initial check on page load
    // updateScrollDownVisibility();

    //--------------------category fetch call-----------------------//

    categoryList.addEventListener("click", function (event) {
        const clickedElement = event.target.closest("li");
        const heading = document.getElementById("heading");
        if (clickedElement) {
            const categoryId = clickedElement.id;
            switch (categoryId) {
                case "allist":
                    alltask();
                    break;
                case "completed":
                    heading.innerHTML = "Your completed tasks"
                    categorized(2);
                    break;
                case "ongoing":
                    heading.innerHTML = "Your on going tasks"
                    categorized(1);
                    break;
                case "overdue":
                    heading.innerHTML = "Your over due tasks"
                    categorized(3);
                    break;
                default:
                    break;
            }
        }
    });

    async function categorized(num) {
        try {
            const token = user.entryPass;
            const response = await fetch(`http://localhost:8080/home/tasks/${user.name}/${num}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            createCateTable(data);

        } catch (error) {
            console.error('There was a problem with the fetch operation:', error.message);
        }
    }
    function mapStatusToDisplayText(status) {
        switch (status) {
            case 'Ongoing':
                return 'On going';
            case 'Complete':
                return 'Completed';
            case 'Overdue':
                return 'Over due';
            default:
                return status;
        }
    }

    function createCateTable(data) {

        container.innerHTML = "";

        const table = document.createElement('ul');
        table.classList.add('responsive-table');

        // Create table header
        const headerRow = document.createElement('li');
        headerRow.classList.add('table-header');
        headerRow.innerHTML = `
            <div class="col col-1">Tasks</div>
            <div class="col col-2">Due Date</div>
            <div class="col col-3">Priority</div>
            <div class="col col-5"></div>
            <div class="col col-5"></div>
            <div class="col col-5"></div>
        `;
        table.appendChild(headerRow);

        // Create table rows
        data.forEach(task => {
            const row = document.createElement('li');
            row.classList.add('table-row');
            row.classList.add(task.status.toLowerCase().replace(/\s/g, '-')); // Add status class

            if(task.status === 'Ongoing'){
                row.innerHTML = `
                <div class="col col-1" data-label="Task Name">${task.taskName}</div>
                <div class="col col-2" data-label="Due Date">${task.date}</div>
                <div class="col col-3" data-label="Priority"><span class="Pr-${getPriorityClass(task.priority)}">${task.priority}</span></div>
                <div class="col col-5" data-label="Priority"><span class="material-symbols-outlined" data-task-id='${task.id}'>check</span></div>
                <div class="col col-5" data-label="Priority"><span class="material-symbols-outlined" data-task-id='${task.id}'>delete</span></div>
                <div class="col col-5" data-label="Priority"><span class="material-symbols-outlined" data-task-id='${task.id}'>edit</span></div>
            `;
            }else{
            row.innerHTML = `
                <div class="col col-1" data-label="Task Name">${task.taskName}</div>
                <div class="col col-2" data-label="Due Date">${task.date}</div>
                <div class="col col-3" data-label="Priority"><span class="Pr-${getPriorityClass(task.priority)}">${task.priority}</span></div>
            `;
            }
            table.appendChild(row);
        });

        container.appendChild(table);
    }
});

