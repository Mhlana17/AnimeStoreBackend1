//Author: Phihlello Junaid Maroga 219354359
package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password;

    protected   User() {
    }

    public  User (Builder builder){
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.email = builder.email;
        this.role = builder.role;
        this.password = builder.password;
    }



    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public static class Builder {
        private String userId;
        private String userName;
        private String email;
        private String role ;
        private String password;

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder copy(User user) {
            this.userId = user.userId;
            this.userName = user.userName;
            this.email = user.email;
            this.role = user.role;
            this.password = user.password;
            return this;
        }

        public User build(){return  new User(this);
        }
    }
}
