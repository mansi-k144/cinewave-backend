package reactjavaproject.cinewave.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import reactjavaproject.cinewave.models.User;
import reactjavaproject.cinewave.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users (admin only - consider adding security)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Register new user - matches RegistrationPage.js
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody User user,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(Map.of("error", errors));
        }

        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already registered"));
        }

        return userService.registerUser(user)
            .map(u -> ResponseEntity.ok(Map.of(
                "message", "Registration successful",
                "user", u
            )))
            .orElse(ResponseEntity.internalServerError()
                .body(Map.of("error", "Registration failed")));
    }

    // Login user - matches LoginPage.js
    @PostMapping("/login")
public ResponseEntity<?> loginUser(
    @RequestBody Map<String, String> credentials) {
    
    // 1. Validate input
    String email = credentials.get("email");
    String password = credentials.get("password");
    
    if (email == null || password == null) {
        return ResponseEntity.badRequest()
            .body(Map.of("error", "Email and password are required"));
    }

    // 2. Authenticate
    if (!userService.authenticateUser(email, password)) {
        return ResponseEntity.status(401)
            .body(Map.of("error", "Invalid credentials"));
    }

    // 3. Fetch user data
    Optional<User> user = userService.getUserByEmail(email);
    if (user.isEmpty()) {
        return ResponseEntity.internalServerError()
            .body(Map.of("error", "User not found"));
    }

    // 4. Return success
    return ResponseEntity.ok(Map.of(
        "message", "Login successful",
        "user", user.get()  // Password is excluded via @JsonIgnore
    ));
}
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @Valid @RequestBody User user,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Validation failed"));
        }

        return userService.updateUser(id, user)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok(Map.of("message", "User deleted"));
        }
        return ResponseEntity.notFound().build();
    }
    // Get user by ID - for profile viewing
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get user by email - alternative lookup
    @GetMapping("/user/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

