const express = require('express');
const app = express();
const morgan = require('morgan');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

// Routes which are going to handle requests
const itemRoutes = require('./api/routes/items.js');
const mail = require('./api/routes/send-mail.js');
const todo = require('./api/routes/todos.js');

mongoose.connect(
    'mongodb+srv://nabeel:nabeel@fanyhealth-mek3v.mongodb.net/test?retryWrites=true&w=majority',
    {
        useNewUrlParser: true,
        useUnifiedTopology: true,
        useCreateIndex: true
    }
);

// Log everything
app.use(morgan('dev'));

// Parse incoming requests' bodies
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers',
        'Origin, X-Requested-With, Content-Type, Accept, Authorization');

    if (req.method == 'OPTIONS') {
        res.header('Access-Control-Allow-Methods',
            'PUT, POST, PATCH, DELETE, GET');
        return res.status(200).json({});
    }

    next();
});

// Send /items requests to file that handles them:
app.use('/items', itemRoutes);
// Send /mail requests to file that handles them:
app.use('/mail', mail);
// Send /todo requests to file that handles them:
app.use('/todo', todo);

// For any other request, if this line is reached, then nothing was able to
// handle the request:
app.use((req, res, next) => {
    const error = new Error('Not found');
    error.status = 404;
    next(error);
});

app.use((error, req, res, next) => {
    res.status(error.status || 500);
    res.json({
        error: {
            message: error.message
        }
    });
});

module.exports = app;