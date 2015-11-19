package logIn;

public class UserManager implements UserDAO {

    @Override
    public User getUser(User u) {
        User user = new User("usename", "password");          
        
        if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean usernameExists(String player) {
        return false;
    }

	@Override
	public boolean userPasswordCorrect(String password) {

		return false;
	}
    
}
