package View;

import Model.Author;
import Model.Book;
import Model.Review;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserView {

    public UserView() {
    }

    public void showUserProfile(List<Book> books, List<Author> authors, List<Review> reviews) {
        JFrame userFrame = new JFrame("User Menu");
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setSize(300, 150);

        JPanel currentUserPanel = new JPanel(new GridLayout(2, 3));
        JButton viewBooksButton = new JButton("View Books");
        JButton viewAuthorsButton = new JButton("View Authors");
        JButton viewReviewsButton = new JButton("View Reviews");
        JButton inputBookButton = new JButton("Insert Book");
        JButton inputAuthorButton = new JButton("Insert Author");
        JButton inputReviewButton = new JButton("Write a review");
        currentUserPanel.add(viewBooksButton);
        currentUserPanel.add(viewAuthorsButton);
        currentUserPanel.add(viewReviewsButton);
        currentUserPanel.add(inputBookButton);
        currentUserPanel.add(inputAuthorButton);
        currentUserPanel.add(inputReviewButton);

        userFrame.add(currentUserPanel);
        userFrame.setVisible(true);

        viewBooksButton.addActionListener(e -> displayBooks(books));
        viewAuthorsButton.addActionListener(e -> displayAuthors(authors));
        viewReviewsButton.addActionListener(e -> displayReviews(reviews));
        inputBookButton.addActionListener(e -> inputBook(books));
        inputAuthorButton.addActionListener(e -> inputAuthor(authors));
        inputReviewButton.addActionListener(e -> inputReview(reviews));
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
                reviewCard.setBorder(BorderFactory.createTitledBorder("Book: " + review.getBook().getTitle()));

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

    private void inputBook(List<Book> books) {
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

        }
    }

    private void inputAuthor(List<Author> authors) {
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
        inputAuthorData.add(new JLabel("Death Date"));
        JTextField deathDate = new JTextField();
        inputAuthorData.add(deathDate);

        int authorData = JOptionPane.showConfirmDialog(null, inputAuthorData, "Insert Author", JOptionPane.OK_CANCEL_OPTION);
        if (authorData == JOptionPane.OK_OPTION) {

        }
    }

    private void inputReview(List<Review> reviews) {
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

        }
    }
}