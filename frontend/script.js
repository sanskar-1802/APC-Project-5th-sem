const API_BASE = "http://localhost:8085/api";

// 🔹 Load all polls
async function loadPolls() {
  try {
    let res = await fetch(`${API_BASE}/polls`);
    let polls = await res.json();
    let container = document.getElementById("polls");
    container.innerHTML = "";
    polls.forEach(p => {
      let div = document.createElement("div");
      div.className = "card";
      div.innerHTML = `
        <h3>${p.title}</h3>
        <p>${p.description}</p>
        <a class="btn" href="poll.html?id=${p.id}">View Poll</a>
      `;
      container.appendChild(div);
    });
  } catch (err) {
    document.getElementById("polls").innerHTML = `<p class="error">❌ ${err.message}</p>`;
  }
}

// 🔹 Load single poll
async function loadPoll() {
  const pollId = new URLSearchParams(window.location.search).get("id");
  try {
    let res = await fetch(`${API_BASE}/polls/${pollId}`);
    let poll = await res.json();

    document.getElementById("title").innerText = poll.title;
    document.getElementById("description").innerText = poll.description;

    let form = document.getElementById("voteForm");
    poll.options.forEach(opt => {
      let div = document.createElement("div");
      div.innerHTML = `<label><input type="radio" name="option" value="${opt.id}"> ${opt.text}</label>`;
      form.appendChild(div);
    });

    form.onsubmit = async e => {
      e.preventDefault();
      let optionId = form.option.value;
      if (!optionId) return alert("Choose an option!");

      let vote = { pollId, optionId, userId: 1 }; // mock userId
      let res = await fetch(`${API_BASE}/vote`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vote)
      });

      document.getElementById("message").innerText = res.ok
        ? "✅ Vote submitted!"
        : "❌ Failed to vote";
    };
  } catch (err) {
    document.getElementById("message").innerText = "❌ " + err.message;
  }
}

// 🔹 Admin: create poll
function setupAdmin() {
  document.getElementById("pollForm").onsubmit = async e => {
    e.preventDefault();
    let title = document.getElementById("title").value;
    let desc = document.getElementById("description").value;
    let options = Array.from(document.querySelectorAll("#options input")).map(i => i.value);

    let res = await fetch(`${API_BASE}/polls`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ title, description: desc, options })
    });

    document.getElementById("adminMessage").innerText = res.ok
      ? "✅ Poll created!"
      : "❌ Failed to create poll";
  };
}

function addOption() {
  let optDiv = document.getElementById("options");
  let input = document.createElement("input");
  input.type = "text";
  input.placeholder = `Option ${optDiv.children.length + 1}`;
  optDiv.appendChild(input);
}

// 🔹 Auth: login
function setupLogin() {
  document.getElementById("loginForm").onsubmit = async e => {
    e.preventDefault();
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let res = await fetch(`${API_BASE}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    document.getElementById("loginMessage").innerText = res.ok
      ? "✅ Login successful!"
      : "❌ Invalid credentials";
  };
}

// 🔹 Auth: register
function setupRegister() {
  document.getElementById("registerForm").onsubmit = async e => {
    e.preventDefault();
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let res = await fetch(`${API_BASE}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    document.getElementById("registerMessage").innerText = res.ok
      ? "✅ Registration successful!"
      : "❌ Failed to register";
  };
}
