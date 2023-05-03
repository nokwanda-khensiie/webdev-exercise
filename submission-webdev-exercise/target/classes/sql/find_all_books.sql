SELECT Books.title, Genres.description
FROM Books, Genres
WHERE Books.genre_code = Genres.code;