
let form = document.getElementById("formButton");
form.addEventListener("click", function () {
    let container = document.getElementById("addForm");

    console.log(container);
    const add_form = `
    <div class="form-container">
      <div class="form">
        <div>
            <span class="close" onclick="closeButton()">&times;</span>
        </div>
        <div class="modal-content">
          <form>
            <div class="title">Add Task</div>
            <label for="taskName" class="label">Task Name:</label>
            <input type="text" id="taskName" name="taskName" required>

            <label for="dueDate">Due Date:</label>
            <input type="date" id="dueDate" name="dueDate" required>

            <label for="priority">Priority:</label>
            <select id="priority" name="priority" class="choose" required>
              <option value="low">Low</option>
              <option value="medium">Medium</option>
              <option value="high">High</option>
            </select>

            <div class="button"><button type="submit">Submit</button></div>
          </form>
        </div>
      </div>
    </div>
    `;

    container.innerHTML += add_form;
});

function closeButton() {
    let container = document.getElementById("addForm");
    setTimeout(() => {
        container.innerHTML = "";
    }, 300);
}
