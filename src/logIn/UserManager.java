package logIn;

public class UserManager implements UserDAO {

    @Override
    public User getUser(User u) {
        User onlyUser = new User("player", "password");          
        
        if(u.getPlayer().equals(onlyUser.getPlayer()) && u.getPassword().equals(onlyUser.getPassword())) {
            return onlyUser;
        }
        return null;
    }

    @Override
    public boolean userExists(String player) {
        return false;
    }
    
}
