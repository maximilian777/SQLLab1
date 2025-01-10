package View;

import Controller.AuthorController;
import Controller.BookController;
import Controller.ReviewController;
import Model.Author;
import Model.Book;
import Model.Review;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UserView {

    private final BookController bookController;
    private final AuthorController authorController;
    private final ReviewController reviewController;
    private final Runnable logoutAction;

    public UserView(BookController bookController, AuthorController authorController, ReviewController reviewController, Runnable logoutAction) {
        this.bookController = bookController;
        this.authorController = authorController;
        this.reviewController = reviewController;
        this.logoutAction = logoutAction;
    }

    public void showUserProfile(List<Book> books, List<Author> authors, List<Review> reviews, Supplier<String> getUser) throws SQLException {
        JFrame userFrame = new JFrame("User Menu");
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(450, 300);

        JPanel currentUserPanel = new JPanel(new GridLayout(3, 3));
        JButton viewBooksButton = new JButton("View Books");
        JButton viewAuthorsButton = new JButton("View Authors");
        JButton viewReviewsButton = new JButton("View Reviews");
        JButton search = new JButton("Search book");
        JButton inputBookButton = new JButton("Insert Book");
        JButton inputAuthorButton = new JButton("Insert Author");
        JButton inputReviewButton = new JButton("Write a Review");
        JButton inputAuthorToBookButton = new JButton("Assign Author to Book");
        JButton logOutButton = new JButton("Log out");

        currentUserPanel.add(viewBooksButton);
        currentUserPanel.add(viewAuthorsButton);
        currentUserPanel.add(viewReviewsButton);
        currentUserPanel.add(search);
        currentUserPanel.add(inputBookButton);
        currentUserPanel.add(inputAuthorButton);
        currentUserPanel.add(inputReviewButton);
        currentUserPanel.add(inputAuthorToBookButton);
        currentUserPanel.add(logOutButton);

        userFrame.add(currentUserPanel);
        userFrame.setVisible(true);

        // Add action listeners for each button
        viewBooksButton.addActionListener(e -> displayBooks(books));
        viewAuthorsButton.addActionListener(e -> displayAuthors(authors));
        viewReviewsButton.addActionListener(e -> displayReviews(reviews));
        search.addActionListener(e -> {
            try {
                search();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        inputBookButton.addActionListener(e -> {
            try {
                inputBook(books);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        inputAuthorButton.addActionListener(e -> {
            try {
                inputAuthor(authors);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        inputReviewButton.addActionListener(e -> {
            try {
                inputReview(reviews, getUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        inputAuthorToBookButton.addActionListener(e -> {
            try {
                inputAuthorToBook(books, authors);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        logOutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    userFrame,
                    "Are you sure you want to log out?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                if (logoutAction != null) {
                    logoutAction.run();
                }
                userFrame.dispose();
            }
        });
    }

    private void displayBooks(List<Book> books) {
        JFrame bookFrame = new JFrame("Books");
        bookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookFrame.setSize(600, 400);

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));

        for (Book book : books) {
            JPanel singleBookPanel = new JPanel(new GridLayout(4, 1, 5, 5));
            singleBookPanel.setBorder(BorderFactory.createTitledBorder(book.getTitle()));

            singleBookPanel.add(new JLabel("Genre: " + book.getGenre()));
            singleBookPanel.add(new JLabel("Pages: " + book.getPages()));
            singleBookPanel.add(new JLabel("ISBN: " + book.getISBN()));

            StringBuilder authors = new StringBuilder("Authors: ");
            for (Author author : book.getAuthors()) {
                authors.append(author.getFirstName()).append(" ").append(author.getLastName()).append(", ");
            }
            singleBookPanel.add(new JLabel(authors.toString()));

            bookPanel.add(singleBookPanel);
        }

        JScrollPane scrollPane = new JScrollPane(bookPanel);
        bookFrame.add(scrollPane);
        bookFrame.setVisible(true);
    }

    private void displayAuthors(List<Author> authors) {
        JFrame authorFrame = new JFrame("Authors");
        authorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        authorFrame.setSize(400, 300);

        JPanel authorPanel = new JPanel();
        authorPanel.setLayout(new BoxLayout(authorPanel, BoxLayout.Y_AXIS));

        for (Author author : authors) {
            JPanel singleAuthorPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            singleAuthorPanel.setBorder(BorderFactory.createTitledBorder(author.getFirstName() + " " + author.getLastName()));

            singleAuthorPanel.add(new JLabel("Author ID: " + author.getAuthorID()));
            singleAuthorPanel.add(new JLabel("Date of Birth: " + author.getBirthDate()));
            singleAuthorPanel.add(new JLabel("Date of Death: " + (author.getDeathDate() != null ? author.getDeathDate() : "N/A")));

            authorPanel.add(singleAuthorPanel);
        }

        JScrollPane scrollPane = new JScrollPane(authorPanel);
        authorFrame.add(scrollPane);
        authorFrame.setVisible(true);
    }

    private void displayReviews(List<Review> reviews) {
        JFrame reviewFrame = new JFrame("Reviews");
        reviewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewFrame.setSize(600, 400);

        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        if (reviews.isEmpty()) {
            JLabel noReviewsLabel = new JLabel("No reviews available!");
            noReviewsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            reviewPanel.add(noReviewsLabel);
        } else {
            for (Review review : reviews) {
                JPanel reviewCard = new JPanel(new BorderLayout());
                reviewCard.setBorder(BorderFactory.createTitledBorder("Book: " + review.getBookISBN()));

                String reviewDetails = String.format(
                        "Reviewer: %s\nRating: %d/5\nReview:\n%s\n",
                        review.getReviewer().getUsername(),
                        review.getRating(),
                        review.getReviewText()
                );

                JTextArea reviewTextArea = new JTextArea(reviewDetails);
                reviewTextArea.setEditable(false);
                reviewCard.add(new JScrollPane(reviewTextArea), BorderLayout.CENTER);

                reviewPanel.add(reviewCard);
            }
        }

        JScrollPane scrollPane = new JScrollPane(reviewPanel);
        reviewFrame.add(scrollPane);
        reviewFrame.setVisible(true);
    }

    private Book inputBook(List<Book> books) throws SQLException {
        JPanel inputBookData = new JPanel(new GridLayout(4, 2));
        inputBookData.add(new JLabel("Title: "));
        JTextField title = new JTextField();
        inputBookData.add(title);
        inputBookData.add(new JLabel("Genre: "));
        JTextField genre = new JTextField();
        inputBookData.add(genre);
        inputBookData.add(new JLabel("Pages: "));
        JTextField pages = new JTextField();
        inputBookData.add(pages);
        inputBookData.add(new JLabel("ISBN: "));
        JTextField ISBN = new JTextField();
        inputBookData.add(ISBN);

        int bookData = JOptionPane.showConfirmDialog(null, inputBookData, "Insert Book", JOptionPane.OK_CANCEL_OPTION);

        if (bookData == JOptionPane.OK_OPTION) {
            return bookController.createBook(title.getText(), null, genre.getText(), pages.getText(), ISBN.getText());
        }
        else {return null;}
    }

    private Author inputAuthor(List<Author> authors) throws SQLException {
        JPanel inputAuthorData = new JPanel(new GridLayout(4, 2));
        inputAuthorData.add(new JLabel("First Name: "));
        JTextField firstName = new JTextField();
        inputAuthorData.add(firstName);
        inputAuthorData.add(new JLabel("Last Name: "));
        JTextField lastName = new JTextField();
        inputAuthorData.add(lastName);
        inputAuthorData.add(new JLabel("Birth Date: "));
        JTextField birthDate = new JTextField();
        inputAuthorData.add(birthDate);
        inputAuthorData.add(new JLabel("Death Date:"));
        JTextField deathDate = new JTextField();
        inputAuthorData.add(deathDate);

        int authorData = JOptionPane.showConfirmDialog(null, inputAuthorData, "Insert Author", JOptionPane.OK_CANCEL_OPTION);
        if (authorData == JOptionPane.OK_OPTION) {
            try {
                java.sql.Date parsedBirthDate = java.sql.Date.valueOf(birthDate.getText());
                java.sql.Date parsedDeathDate = null;
                if (!deathDate.getText().trim().isEmpty()) {
                    parsedDeathDate = java.sql.Date.valueOf(deathDate.getText());
                }
                return authorController.createAuthor(
                        firstName.getText(),
                        lastName.getText(),
                        parsedBirthDate,
                        parsedDeathDate
                );
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Invalid date format! Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }
    }

    private Review inputReview(List<Review> reviews, Supplier<String> getUser) throws SQLException {
        JPanel inputReviewData = new JPanel(new GridLayout(3, 2));
        inputReviewData.add(new JLabel("Book ISBN: "));
        JTextField ISBN = new JTextField();
        inputReviewData.add(ISBN);
        inputReviewData.add(new JLabel("Select a rating (1-10): "));
        Integer[] ratings = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox<Integer> selectRating = new JComboBox<>(ratings);
        inputReviewData.add(selectRating);
        inputReviewData.add(new JLabel("Review: "));
        JTextField reviewText = new JTextField();
        inputReviewData.add(reviewText);

        int reviewData = JOptionPane.showConfirmDialog(null, inputReviewData, "Write a review", JOptionPane.OK_CANCEL_OPTION);
        if (reviewData == JOptionPane.OK_OPTION) {
            return reviewController.createReview(ISBN.getText(),  (String) selectRating.getSelectedItem(), getUser.get(), reviewText.getText() );
        }
        else { return null;}
    }

    private void inputAuthorToBook(List<Book> books, List<Author> authors) throws SQLException {
        JPanel inputAuthorToBook = new JPanel(new GridLayout(3, 2));

        inputAuthorToBook.add(new JLabel("Select a Book:"));
        JComboBox<Book> bookDropdown = new JComboBox<>(books.toArray(new Book[0]));
        bookDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Book) {
                    value = ((Book) value).getTitle();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        inputAuthorToBook.add(bookDropdown);

        inputAuthorToBook.add(new JLabel("Select an Author:"));
        JComboBox<Author> authorDropdown = new JComboBox<>(authors.toArray(new Author[0]));
        authorDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Author) {
                    value = ((Author) value).getFirstName() + " " + ((Author) value).getLastName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        inputAuthorToBook.add(authorDropdown);

        int result = JOptionPane.showConfirmDialog(null, inputAuthorToBook, "Assign Author to Book", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Book selectedBook = (Book) bookDropdown.getSelectedItem();
            Author selectedAuthor = (Author) authorDropdown.getSelectedItem();
            bookController.assignAuthorToBook(selectedBook.getISBN(), selectedAuthor.getAuthorID());
        }
    }

    private void search() throws SQLException {
        List<Book> searchedItems = new ArrayList<>();
        JComboBox<String> searchOptions = new JComboBox<>(new String[]{"Title", "Author", "ISBN", "Genre", "Review Score"});
        JPanel searchPanel = new JPanel();
        JTextField inputField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();

        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.add(new JLabel("Select Search Type:"));
        searchPanel.add(searchOptions);
        searchPanel.add(new JLabel("Search Input:"));
        searchPanel.add(inputField);

        // Action listener to dynamically update input fields
        searchOptions.addActionListener(e -> {
            String selected = (String) searchOptions.getSelectedItem();
            searchPanel.removeAll(); // Clear the panel
            searchPanel.add(new JLabel("Select Search Type:"));
            searchPanel.add(searchOptions);

            if ("Author".equals(selected)) {
                searchPanel.add(new JLabel("First Name:"));
                searchPanel.add(firstNameField);
                searchPanel.add(new JLabel("Last Name:"));
                searchPanel.add(lastNameField);
            } else {
                searchPanel.add(new JLabel("Search by " + selected.toLowerCase() + ":"));
                searchPanel.add(inputField);
            }

            searchPanel.revalidate();
            searchPanel.repaint();
        });
        
        JScrollPane scrollPane = new JScrollPane(searchPanel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        int result = JOptionPane.showConfirmDialog(null, scrollPane, "Search", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String searchType = (String) searchOptions.getSelectedItem();
            switch (searchType) {
                case "Author":
                    searchedItems = bookController.searchBookByAuthor(firstNameField.getText(), lastNameField.getText());
                    break;
                case "Title":
                    searchedItems = bookController.searchBookByTitle(inputField.getText());
                    break;
                case "ISBN":
                    searchedItems = bookController.searchBookByISBN(inputField.getText());
                    break;
                case "Genre":
                    searchedItems = bookController.searchBookByGenre(inputField.getText());
                    break;
                case "Review Score":
                    searchedItems = bookController.searchBookByRating(inputField.getText());
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid search type selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (searchedItems.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No results found.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                displayBooks(searchedItems);
            }
        }
    }
}