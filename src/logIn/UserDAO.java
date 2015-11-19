package logIn;

public interface UserDAO {
    public User getUser(User u);
    public boolean usernameExists(String player);
    public boolean userPasswordCorrect(String password);
}
