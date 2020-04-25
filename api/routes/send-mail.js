const express = require('express');
const router = express.Router();
const sgMail = require('@sendgrid/mail');
sgMail.setApiKey("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");

//Sending mail via this channel
// Handle incoming GET requests to /mail/emailaddress/personName
router.post('/:address/:person', (req, res, next) => {
    const email = req.params.address;
    const person = req.params.person;

    const msg = {
        to: email,
        from: 'intel.moiz@gmail.com',
        subject: 'FANY-Health Appointment Confirmation',
        text: 'Your FANYHEALTH appointment is confirmed with ' + person + '.'
      };
    
    sgMail.send(msg)
    .then(() => {
        console.log('Message sent')
        res.status(200);
    })
    .catch((error) => {
        console.log(error.response.body);
    });
});

module.exports = router;