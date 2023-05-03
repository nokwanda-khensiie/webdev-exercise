package wethinkcode.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {
        List<Genre> genreName = new ArrayList<>();
        try(final Statement genres = connection.createStatement()){
            String sql = "SELECT * FROM genres;";
            ResultSet resultSet = genres.executeQuery(sql);

            while (resultSet.next()){
                String code = resultSet.getString("code");
                String description = resultSet.getString("description");

                genreName.add(new Genre(code, description));
            }
        }
        return genreName;
    }

    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {
         List<Genre> nameOfGenresLike = new ArrayList<>();
         try(final Statement likeGenre = connection.createStatement()){
             String sql = "SELECT * FROM Genres WHERE description LIKE '" +pattern+"';";
             ResultSet setOfResult = likeGenre.executeQuery(sql);

             while (setOfResult.next()){
                 String code = setOfResult.getString("code");
                 String description = setOfResult.getString("description");

                 nameOfGenresLike.add(new Genre(code, description));
             }
         }
         return nameOfGenresLike;
    }

    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {
        List<BookGenreView> checkBookAndGenre = new ArrayList<>();
        new DataLoader(connection).insertGenres();
        try(final  Statement BandG = connection.createStatement()){
            String sql = """
                     SELECT Books.title, Genres.description
                    FROM Books, Genres
                    WHERE Books.genre_code = Genres.code;""";
            ResultSet setOfResult = BandG.executeQuery(sql);

            while (setOfResult.next()){
                String title = setOfResult.getString("title");
                String description = setOfResult.getString("description");

                checkBookAndGenre.add(new BookGenreView(title, description));
            }
        }

        return checkBookAndGenre;
    }

    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
       int size = 0;
       try(final Statement bookInG = connection.createStatement()){

           String sql = "SELECT COUNT(genre_code)\n" +
                   "FROM Books\n" +
                   "WHERE genre_code = \""+genreCode+"\";";
           ResultSet setOfResults = bookInG.executeQuery(sql);
           size = setOfResults.getInt(1);
       }
       return size;
    }
}
