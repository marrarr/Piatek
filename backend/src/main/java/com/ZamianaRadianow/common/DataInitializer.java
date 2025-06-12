package com.ZamianaRadianow.common;


import com.ZamianaRadianow.gra.model.*;
import com.ZamianaRadianow.gra.repository.*;
import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.rola.RoleRepository;
import com.ZamianaRadianow.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, GenreRepository genreRepository, PlatformRepository platformRepository, GameRepository gameRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
        this.gameRepository = gameRepository;
        this.reviewRepository = reviewRepository;
    }
//    @Autowired
//    private ImageRepository imageRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Tworzenie ról
            DBRole adminDBRole = new DBRole();
            adminDBRole.setName("ADMIN");
            roleRepository.save(adminDBRole);

            DBRole userDBRole = new DBRole();
            userDBRole.setName("USER");
            roleRepository.save(userDBRole);

            DBRole blocked = new DBRole();
            userDBRole.setName("USER_BLOCKED");
            roleRepository.save(blocked);

            // Tworzenie użytkowników
            DBUser admin = new DBUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.getRoles().add(adminDBRole);
            userRepository.save(admin);

            DBUser user = new DBUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("123"));
            user.getRoles().add(userDBRole);
            userRepository.save(user);

            // Tworzenie gatunków
            Genre action = new Genre();
            action.setName("Action");
            genreRepository.save(action);

            Genre rpg = new Genre();
            rpg.setName("RPG");
            genreRepository.save(rpg);

            Genre adventure = new Genre();
            adventure.setName("Adventure");
            genreRepository.save(adventure);

            Genre strategy = new Genre();
            strategy.setName("Strategy");
            genreRepository.save(strategy);

            // Tworzenie platform
            Platform pc = new Platform();
            pc.setName("PC");
            platformRepository.save(pc);

            Platform ps5 = new Platform();
            ps5.setName("PlayStation 5");
            platformRepository.save(ps5);

            Platform xbox = new Platform();
            xbox.setName("Xbox Series X");
            platformRepository.save(xbox);

            Platform switchPlatform = new Platform();
            switchPlatform.setName("Nintendo Switch");
            platformRepository.save(switchPlatform);

            // Tworzenie gier
            Game eldenRing = new Game();
            eldenRing.setTitle("Elden Ring");
            eldenRing.setReleaseDate(Timestamp.valueOf("2022-02-25 00:00:00"));
            eldenRing.setDeveloper("FromSoftware");
            eldenRing.setPublisher("Bandai Namco");
            eldenRing.setDescription("Action RPG developed by FromSoftware and published by Bandai Namco. The game was made in collaboration with fantasy novelist George R. R. Martin.");
            eldenRing.setGenres(Set.of(action, rpg));
            eldenRing.setPlatforms(Set.of(pc, ps5, xbox));
            gameRepository.save(eldenRing);
//            addImageToGame(eldenRing, "static/images/zdjecie.jpg", "image/jpeg");


            Game cyberpunk = new Game();
            cyberpunk.setTitle("Cyberpunk 2077");
            cyberpunk.setReleaseDate(Timestamp.valueOf("2020-12-10 00:00:00"));
            cyberpunk.setDeveloper("CD Projekt Red");
            cyberpunk.setPublisher("CD Projekt");
            cyberpunk.setDescription("An open-world, action-adventure RPG set in Night City, a megalopolis obsessed with power, glamour, and body modification.");
            cyberpunk.setGenres(Set.of(action, rpg));
            cyberpunk.setPlatforms(Set.of(pc, ps5, xbox));
            gameRepository.save(cyberpunk);
//            addImageToGame(cyberpunk, "static/images/zdjecie.jpg", "image/jpeg");

            Game zelda = new Game();
            zelda.setTitle("The Legend of Zelda: Breath of the Wild");
            zelda.setReleaseDate(Timestamp.valueOf("2017-03-03 00:00:00"));
            zelda.setDeveloper("Nintendo");
            zelda.setPublisher("Nintendo");
            zelda.setDescription("An action-adventure game set in an open world where players control Link, who awakens from a hundred-year slumber to defeat Calamity Ganon.");
            zelda.setGenres(Set.of(adventure, action));
            zelda.setPlatforms(Set.of(switchPlatform));
            gameRepository.save(zelda);
//            addImageToGame(zelda, "static/images/zdjecie.jpg", "image/jpeg");

            // Tworzenie recenzji
            Review review1 = new Review();
            review1.setUser(admin);
            review1.setGame(eldenRing);
            review1.setRating(10);
            review1.setReviewText("Absolutely masterpiece! The open world is breathtaking and combat is satisfying.");
            review1.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            reviewRepository.save(review1);

            Review review2 = new Review();
            review2.setUser(user);
            review2.setGame(eldenRing);
            review2.setRating(9);
            review2.setReviewText("Amazing game but quite difficult for beginners.");
            review2.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            reviewRepository.save(review2);

            Review review3 = new Review();
            review3.setUser(user);
            review3.setGame(cyberpunk);
            review3.setRating(7);
            review3.setReviewText("Good game after all the patches. Story is great but still some bugs.");
            review3.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            reviewRepository.save(review3);

            Review review4 = new Review();
            review4.setUser(admin);
            review4.setGame(zelda);
            review4.setRating(10);
            review4.setReviewText("One of the best games ever made. Perfect open world design.");
            review4.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            reviewRepository.save(review4);

            System.out.println("=========================");
            System.out.println("Initial data loaded successfully!");
            System.out.println("=========================");
        }


    }

//    private void addImageToGame(Game game, String path, String contentType) {
//        try {
//            ClassPathResource imgFile = new ClassPathResource(path);
//            byte[] imageData = Files.readAllBytes(imgFile.getFile().toPath());
//
//            Image image = new Image();
//            image.setData(imageData);
//            image.setContentType(contentType);
//            image.setGame(game);
//
//            imageRepository.save(image);
//        } catch (IOException e) {
//            System.err.println("Nie udało się załadować zdjęcia: " + path);
//            e.printStackTrace();
//        }
//    }

}