var mongoose = require('mongoose');

var todoSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    todo: {type: String, required: true, unique: true}
});

// export itemSchema as a class called Item
module.exports = mongoose.model('Todo', todoSchema);