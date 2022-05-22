const books = [
    new Book(1, "Book1", "Alex"),
    new Book(2, "Book2", "Sarah"),
    new Book(3, "Book3", "Tom")
];


function Book(id, title, author){
    this.id = id;
    this.title = title;
    this.author = author;
}

function addBook(){
    var newBook = document.getElementById("book_title").value;
    var newAuthor = document.getElementById("book_author").value;
    books.push(new Book(books.length + 1, newBook, newAuthor));
    elementsDebug();
}

function getBook(){
    elementsDebug();
    for(let i = 0; i < books.length; i++){
        let x = document.createElement("p");
        x.innerHTML = books[i].id + ". " + books[i].title + " - by " + books[i].author;
        document.getElementById("list").appendChild(x);
    }
    elementsDebug();
}

function elementsDebug(){

    console.log(books);
}

function toLibrary(){

}