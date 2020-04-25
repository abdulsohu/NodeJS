const express = require('express');
app = express();
const mongoose = require('mongoose');
const path = require('path');

const Todo = require('/Users/abdulmoizmunir/Desktop/CIS350/FinalProject/node-item/models/todo.js');

// Handle incoming GET requests to /todo/ (to show static homepage)
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});


// Handle incoming GET requests to /todo/getTodos (to find all todos)
app.get('/getTodos', (req, res) => {
    Todo.find()
        .select('_id todo')
        .exec()
        .then(
            docs => {
                const response = {
                    count: docs.length,
                    todos: docs
                };

                console.log(docs);
                res.status(200).json(response.todos);
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({ error: err })
            });
});


// Handle incoming PUT requests to /todo/ (for adding a todo)
app.post('/', (req, res) => {
    const todo = new Todo({
        _id: new mongoose.Types.ObjectId(),
        todo: req.body.todo
    });

    todo.save()
        .then(
            result => {
                console.log(result);
                res.status(200).json({
                    ok: true,
                    message: 'Handling PUT requests to /todo/ for creation',
                    createdTodo: result
                });
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({
                    error: err
                });
            })
});


// Handle incoming PATCH requests to /todo/todoId (for updating a todo)
app.put('/:id', (req, res) => {
    const id = req.params.id;
    const userInput = req.body;

    Todo.updateOne({ _id: id },
        {
            $set: {
                todo: userInput.todo
            }
        })
        .exec()
        .then(
            result => {
                console.log(result);
                res.status(200).json({ ok: true, result, todo: userInput.todo });
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({
                    error: err
                });
            });
});


// Handle incoming DELETE requests to /todo/todoId (for deleting a todo)
app.delete('/:id', (req, res) => {
    const id = req.params.id;

    Todo.deleteOne({ _id: id })
        .exec()
        .then(
            result => {
                res.status(200).json({ ok: true, result });
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({
                    error: err
                });
            });
});




module.exports = app;