const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

const Item = require('/Users/abdulmoizmunir/Desktop/CIS350/FinalProject/node-item/models/item.js');


// Handle incoming GET requests to /items/
router.get('/', (req, res, next) => {
    Item.find()
        .select('_id name quantity lastUpdated')
        .exec()
        .then(
            docs => {
                const response = {
                    count: docs.length,
                    items: docs
                };

                console.log(docs);
                res.status(200).json(response);
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({ error: err })
            });
});


// Handle incoming POST requests to /items/
router.post('/', (req, res, next) => {
    const item = new Item({
        _id: new mongoose.Types.ObjectId(),
        name: req.body.name,
        quantity: req.body.quantity,
        lastUpdated: req.body.lastUpdated || Date.now()
    });

    item.save()
        .then(
            result => {
                console.log(result);
                res.status(200).json({
                    message: 'Handling POST requests to /products',
                    createdItem: result
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


// Handle GET requests to items/:itemId
router.get('/:itemId', (req, res, next) => {
    const id = req.params.itemId;

    Item.findById(id)
        .exec()
        .then(
            doc => {
                console.log("From database", doc);
                if (doc) {
                    res.status(200).json({ doc });
                } else {
                    res.status(404).json({
                        message: "No valid item for provided ID found!"
                    })
                }
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({ error: err });
            });
});


// Handle PATCH requests to /items/:itemId
router.patch('/:itemId', (req, res, next) => {
    const id = req.params.itemId;

    Item.update({ _id: id },
        {
            $set: {
                name: req.body.name,
                quantity: req.body.quantity,
                lastUpdated: Date.now()
            }
        })
        .exec()
        .then(
            result => {
                console.log(result);
                res.status(200).json(result);
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({
                    error: err
                });
            });

});


// Handle DELETE requests to /items/:itemId
router.delete('/:itemId', (req, res, next) => {
    const id = req.params.itemId;

    Item.remove({ _id: id })
        .exec()
        .then(
            result => {
                res.status(200).json(result);
            })
        .catch(
            err => {
                console.log(err);
                res.status(500).json({
                    error: err
                });
            });
});

module.exports = router;