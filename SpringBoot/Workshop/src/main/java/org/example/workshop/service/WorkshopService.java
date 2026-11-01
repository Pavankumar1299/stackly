package org.example.workshop.service;

import lombok.Getter;
import lombok.Setter;
import org.example.workshop.model.Workshop;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WorkshopService {

    public Map<Long, Workshop> workshops = new HashMap<>();

//    @Getter
//    @Setter
    private long nextId = 1006L;

    public WorkshopService() {
        workshops.put(1001L, new Workshop(1001L, "Advance Python", "Programming", BigDecimal.valueOf(199), 25, "pk@gamil.com"));
        workshops.put(1002L, new Workshop(1002L, "AI in SpringBoot", "Programming", BigDecimal.valueOf(299), 13, "jk@gmail.com"));
        workshops.put(1003L, new Workshop(1003L, "Improve your communication", "Communication", BigDecimal.valueOf(99), 21, "jk@gmail.com"));
        workshops.put(1004L, new Workshop(1004L, "Have Attitude", "Personality", BigDecimal.valueOf(159), 9, "jk@gmail.com"));
        workshops.put(1005L, new Workshop(1005L, "How to Speak", "Communication", BigDecimal.valueOf(299), 31, "jk@gmail.com"));

    }

    public Workshop addWorkshop(Workshop workshop) {

        long id = nextId++;

        Workshop newWorkshop = new Workshop(
                id,
                workshop.getTitle(),
                workshop.getCategory(),
                workshop.getFee(),
                workshop.getAvailableSeats(),
                workshop.getOrganizerEmail()
        );

        workshops.put(id, newWorkshop);

        return workshop;
    }

    public List<Workshop> getAllWorkshops() {
        return new ArrayList<>(workshops.values());
    }

    public Workshop getWorkshopById(int id) {
        return workshops.entrySet().stream()
                .filter(entry -> entry.getKey() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Workshop with id " + id + " does not exist")).getValue();
    }


    public List<Workshop> getByTitle(String title) {
        checkNotBlank(title, "Search title must not be blank");

        return workshops.values().stream()
                .filter(workshop -> workshop.getTitle().toLowerCase().contains(title.trim().toLowerCase()))
                .toList();
    }

    public List<Workshop> getByCategory(String category) {
        checkNotBlank(category, "Search category must not be blank");

        return workshops.values().stream()
                .filter(workshop -> workshop.getCategory().toLowerCase().contains(category.trim().toLowerCase()))
                .toList();
    }

    public List<Workshop> getAvailableWithinBudget(BigDecimal maxFee) {
        if (maxFee.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Max fee must be greater than 0");
        }

        return workshops.values().stream()
                .filter(workshop -> workshop.getAvailableSeats() > 0)
                .filter(workshop -> workshop.getFee().compareTo(maxFee) < 0)
                .toList();
    }

    private void checkNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
