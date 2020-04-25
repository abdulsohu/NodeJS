var mongoose = require('mongoose');

var itemSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    name: {type: String, required: true, unique: true},
    quantity: {type: Number, min: 0, required: true},
    lastUpdated: {type: Date, default: Date.now},
});

// export itemSchema as a class called Item
module.exports = mongoose.model('Item', itemSchema);