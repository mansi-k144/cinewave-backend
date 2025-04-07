package reactjavaproject.cinewave.services;

import java.util.List;
import java.util.Optional;

import reactjavaproject.cinewave.models.User;

public interface UserService {
    // Check if email exists (for registration validation)
    boolean emailExists(String email);
    
    // Register new user with password encryption
    Optional<User> registerUser(User user);
    
    // Find user by email (for login)
    Optional<User> getUserByEmail(String email);
    
    // Find user by ID (for profile viewing)
    Optional<User> getUserById(String id);
    
    // Authenticate user (login validation)
    boolean authenticateUser(String email, String password);
    
    // Get all users (needed for /api/auth/users endpoint)
    List<User> getAllUsers();
   
    Optional<User> updateUser(String id, User user);
    boolean deleteUser(String id);
    
}