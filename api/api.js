// server.js
const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const app = express();
const PORT = 5000;

// Middleware
app.use(cors({ origin: "http://localhost:5173" }));
app.use(bodyParser.json());

// ---------------- In-memory storage ----------------
let users = [];
let complaints = [];
let communityPosts = [];

let userIdCounter = 1;
let complaintIdCounter = 1;
let communityIdCounter = 1;

// ---------------- USER ROUTES ----------------

// Register user
app.post("/register", (req, res) => {
  const { username, password } = req.body;
  if (!username || !password) return res.status(400).json({ message: "Username and password required" });

  const exists = users.find(u => u.username === username);
  if (exists) return res.status(400).json({ message: "User already exists" });

  const user = { id: userIdCounter++, username, password };
  users.push(user);
  res.json({ message: "User registered successfully", user });
});

// Login user
app.post("/login", (req, res) => {
  const { username, password } = req.body;
  const user = users.find(u => u.username === username && u.password === password);
  if (!user) return res.status(401).json({ message: "Invalid credentials" });

  res.json({ message: "Login successful", userId: user.id, username: user.username });
});

// ---------------- COMPLAINT ROUTES ----------------

// Submit a complaint
app.post("/complaints", (req, res) => {
  const { userId, title, description } = req.body;
  const user = users.find(u => u.id === userId);
  if (!user) return res.status(400).json({ message: "Invalid user" });

  const complaint = {
    id: complaintIdCounter++,
    userId: user.id,
    username: user.username,
    title,
    description,
    status: "Pending",
    timestamp: new Date(),
  };
  complaints.push(complaint);
  res.json(complaint);
});

// Get complaints for a user
app.get("/complaints/:userId", (req, res) => {
  const userId = parseInt(req.params.userId);
  const userComplaints = complaints.filter(c => c.userId === userId);
  res.json(userComplaints);
});

// ---------------- COMMUNITY ROUTES ----------------

// Create a community post
app.post("/community", (req, res) => {
  const { userId, title, content } = req.body;
  const user = users.find(u => u.id === userId);
  if (!user) return res.status(400).json({ message: "Invalid user" });

  const post = {
    id: communityIdCounter++,
    userId: user.id,
    username: user.username,
    title,
    content,
    timestamp: new Date(),
  };
  communityPosts.push(post);
  res.json(post);
});

// Get all community posts
app.get("/community", (req, res) => {
  res.json(communityPosts);
});

// ---------------- START SERVER ----------------
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
