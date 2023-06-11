const get_books_request_button = document.querySelector('.get-books-request-button');
const get_books_response_field = document.querySelector('.get-books-response-field');

get_books_request_button.addEventListener('click', async () => {
    const text = await fetch("/books")
        .then(r => r.text());
    get_books_response_field.textContent = text;
});

const post_books_request_button = document.querySelector('.post-books-request-button');
const post_books_request_field = document.querySelector('.post-books-request-field');
const post_books_response_field = document.querySelector('.post-books-response-field');

post_books_request_button.addEventListener('click', async () => {
    const text = await fetch("/books", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: post_books_request_field.textContent
    })
        .then(r => r.text());
    post_books_response_field.textContent = text;
});