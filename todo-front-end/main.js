const alllist = [
    {"task": "running", "duedate": "16/07/2024", "status": 1},
    {"task": "reading", "duedate": "22/09/2023", "status": 2},
    {"task": "writing", "duedate": "05/12/2023", "status": 3},
    {"task": "coding", "duedate": "18/04/2024", "status": 1},
    {"task": "cooking", "duedate": "30/08/2023", "status": 2},
    {"task": "learning", "duedate": "10/11/2023", "status": 3},
    {"task": "playing", "duedate": "14/02/2024", "status": 1},
    {"task": "traveling", "duedate": "25/06/2023", "status": 2},
    {"task": "sleeping", "duedate": "08/09/2023", "status": 3},
    {"task": "studying", "duedate": "12/01/2024", "status": 1}
  ];

  const yetto = [
    {"task": "running", "duedate": "16/07/2024"},
    {"task": "coding", "duedate": "18/04/2024"},
    {"task": "playing", "duedate": "14/02/2024"},
    {"task": "studying", "duedate": "12/01/2024"}
  ];

  const completed = [
    {"task": "reading", "duedate": "22/09/2023"},
    {"task": "cooking", "duedate": "30/08/2023"},
    {"task": "traveling", "duedate": "25/06/2023"},
  ];

  const over = [
    {"task": "writing", "duedate": "05/12/2023", "status": 3},
    {"task": "learning", "duedate": "10/11/2023", "status": 3},
    {"task": "sleeping", "duedate": "08/09/2023", "status": 3},
  ];

localStorage.setItem('alllist', JSON.stringify(alllist));
localStorage.setItem('yetto', JSON.stringify(yetto));
localStorage.setItem('completed', JSON.stringify(completed));
localStorage.setItem('over', JSON.stringify(over));