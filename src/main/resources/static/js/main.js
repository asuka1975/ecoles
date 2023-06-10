const get_books_request_button = document.querySelector('.get-books-request-button');
const get_books_resopnse_field = document.querySelector('.get-books-response-field');

get_books_request_button.addEventListener('click', async () => {
    const text = await fetch("/books")
        .then(r => r.text());
    get_books_resopnse_field.textContent = text;
});