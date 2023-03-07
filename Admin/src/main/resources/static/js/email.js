const name = document.getElementById('name');
const phone = document.getElementById('phone');
const email = document.getElementById('email');
const msg = document.getElementById('msg');
//add listener on form container
const submit = document.getElementsByClassName('form-contact')[0];

submit.addEventListener('submit', (e) => {
    e.preventDefault();
    console.log("clicked")
//    write html for proper format to be sent in mail
    let ebody = `
    <b>Name: </b>${name.value}
    <br>
    <b>Email: </b>${phone.value}
    <br>
    <b>Phone: </b>${email.value}
    <br>
    <b>Msg: </b>${msg.value}
`

//    email code
    Email.send({
        SecureToken: "e8c87b45-0aa4-4b81-896f-d57536be696b",
        To: 'qiyuan.cui1989@gmail.com',
        From: "qiyuan.cui1989@gmail.com",
        Subject: "This is the test" + email.value,
        Body: ebody
    }).then(
        message => alert(message)
    );
})