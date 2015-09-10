package logIn;

public interface UserDAO {
    public User getUser(User u);
    public boolean userExists(String player);
}
