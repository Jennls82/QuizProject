package logIn;

public class User {
    //    Fields
    private String player;
    private String password;
    
    //    Constructor
    public User() {
        setPlayer("");
        setPassword("");
    }
    
    public User(String email, String password) {
        super();
        setPlayer(email);
        setPassword(password);
    }
    
    //    Accessor Methods
    public String getPlayer()        { return player;        }
    public String getPassword()        { return password;    }
    
    public void setPlayer(String e)        { player = e;    }
    public void setPassword(String p)    { password = p;    }
}
