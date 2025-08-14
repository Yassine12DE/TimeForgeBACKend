package tn.esprit.tic.timeforge.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tic.timeforge.Entity.User;
import tn.esprit.tic.timeforge.Repository.UserRepository;
import tn.esprit.tic.timeforge.dto.DashboardStats;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User updateUser(Long userId, User userDetails) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setCin(userDetails.getCin());
            user.setRoles(userDetails.getRoles());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    @Transactional
    public void deleteUser(Long userId) {
         userRepository.deleteById(userId);

    }

    public User getbyid(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
           return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public List<User> listUsers() {
        return  userRepository.findAll();
    }

}
