import { validPriority, validDate, validTaskName } from './validation.js';

document.addEventListener("DOMContentLoaded", function () {

  function displayTaskForm(title, submitButtonText, formId) {
    const container = document.getElementById("addForm");
    container.innerHTML = "";
    const taskForm = `
        <div class="form-container">
            <div class="form">
                <form id="${formId}">
                    <div>
                        <span class="close" id="closeButton">&times;</span>
                    </div>
                    <div class="title">${title}</div>
                    <label for="taskName" class="label">Task Name:</label>
                    <input type="text" id="taskName" name="taskName" required>

                    <label for="dueDate">Due Date:</label>
                    <input type="date" id="dueDate" name="dueDate" required>

                    <label for="priority">Priority:</label>
                    <select id="priority" name="priority" class="choose" required>
                        <option value="Low">Low</option>
                        <option value="Medium">Medium</option>
                        <option value="Urgent">Urgent</option>
                    </select>

                    <div class="button">
                        <button type="submit">${submitButtonText}</button>
                    </div>
                </form>
            </div>
        </div>`;

    container.innerHTML += taskForm;

    const closeBtn = document.getElementById('closeButton');
    closeBtn.addEventListener('click', function (event) {
      event.preventDefault();
      container.innerHTML = "";
    });


    if (formId === "taskForm") {
      const form = document.getElementById("taskForm");
      form.addEventListener("submit", async function (event) {
        event.preventDefault();
        await handleTaskSubmission('http://localhost:8081/home/addtask', 'POST');
      });
    } else {
      const form = document.getElementById("editTask");
      form.addEventListener("submit", async function (event) {
        event.preventDefault();
        await handleTaskSubmission('http://localhost:8081/home/edittask', 'PUT');
      });
    }
  }

  // Event listener for Create Task button
  const formButton = document.getElementById("formButton");
  formButton.addEventListener("click", function () {
    displayTaskForm("Add Task", "Submit", "taskForm");
  });

  // Event listener for Edit Task
  document.addEventListener("click", function (event) {
    if (
      event.target &&
      event.target.tagName === "SPAN" &&
      event.target.classList.contains("material-symbols-outlined") &&
      event.target.textContent === "edit"
    ) {
      const taskId = event.target.getAttribute("data-task-id");
      taskFetch(taskId);
    }
  });

  //-------------------------------------------------------//
  // Fetch and Edit Task
  async function taskFetch(id) {
    try {
      const user = JSON.parse(sessionStorage.getItem("user"));
      const token = user.entryPass + "$" + user.name;
      const response = await fetch(`http://localhost:8081/home/task/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();

      // Call the editForm function instead of displayTaskForm
      displayTaskForm("Edit Form", "Update", "editTask");

      // Set values in the form
      const task = document.getElementById("taskName");
      task.value = data.taskName;
      task.setAttribute("task-id", data.id);
      document.getElementById("dueDate").value = data.date;
      document.getElementById("priority").value = data.priority;

    } catch (error) {
      console.error('There was a problem with the fetch operation:', error.message);
    }
  }

  async function performFetch(url, method, obj) {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const token = user.entryPass + "$" + user.name;

    const response = await fetch(url, {
      method: method,
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(obj),
    });

    if (response.ok) {
      window.location.reload();
    } else {
      console.error(`HTTP error! Status: ${response.status}`);
    }
  }


  async function handleTaskSubmission(url, method) {
    const taskName = document.getElementById("taskName");
    const dueDate = document.getElementById("dueDate").value;
    const priority = document.getElementById("priority").value;

    const errors = validateTaskForm(taskName.value, dueDate, priority);
  
    if (errors.length !== 0) {
      alert(errors.join('\n'));
    } else {
      const taskObj = {
        taskName: taskName.value,
        date: dueDate,
        priority: priority,
        id: method === 'POST' ? undefined : taskName.getAttribute('task-id')
      };
  
      await performFetch(url, method, taskObj);
    }
  }
  

  // Validation function for task form
  function validateTaskForm(taskName, dueDate, priority) {
    const errors = [];

    if (!validTaskName(taskName)) {
      errors.push('* Name can not be empty');
    }

    if (!validDate(dueDate)) {
      errors.push('* Due date can not be in the past');
    }

    if (!validPriority(priority)) {
      errors.push('* Priority can not be empty');
    }

    return errors;
  }

  const tasksContainer = document.getElementById("container");
  tasksContainer.addEventListener("click", function (event) {
    if (
      event.target &&
      event.target.tagName === "SPAN" &&
      event.target.classList.contains("material-symbols-outlined") &&
      event.target.textContent === "edit"
    ) {
      const taskId = event.target.getAttribute("data-task-id");
      taskFetch(taskId);
    }
  });

  //--------------------------------completed and delete-------------------------------//

document.getElementById('container').addEventListener('click', function (event) {
  const clickedElement = event.target.closest('.material-symbols-outlined');
  
  if (clickedElement) {
    const taskId = clickedElement.getAttribute('data-task-id');
    
    if (clickedElement.textContent === 'check') {
      performTaskAction(`http://localhost:8081/home/taskComplete/${taskId}`, 'PATCH');
    } else if (clickedElement.textContent === 'delete') {
      performTaskAction(`http://localhost:8081/home/deletetask/${taskId}`, 'DELETE');
    }
  }
});

async function performTaskAction(url, method) {
  const user = JSON.parse(sessionStorage.getItem("user"));
    const token = user.entryPass + "$" + user.name;
  try {
    const response = await fetch(url , {
      method: method,
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    console.log(data);
    window.location.reload();
  } catch (error) {
    console.error('Error:', error.message);
  }
}

});

