package org.example.workshop.controller;

import org.example.workshop.model.Workshop;
import org.example.workshop.service.WorkshopService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @PostMapping
    public Workshop addWorkshop(@RequestBody Workshop workshop) {
        return workshopService.addWorkshop(workshop);
    }

    @GetMapping
    public List<Workshop> getAllWorkshops() {
        return workshopService.getAllWorkshops();
    }

    @GetMapping("/{id}")
    public Workshop getWorkshopById(@PathVariable int id) {
        return workshopService.getWorkshopById(id);
    }

    @GetMapping("/search")
    public List<Workshop> searchByTitle(@RequestParam String title) {
        return workshopService.getByTitle(title);
    }

    @GetMapping("/category")
    public List<Workshop> getWorkshopsByCategory(@RequestParam String category) {
        return workshopService.getByCategory(category);
    }

    @GetMapping("/available")
    public List<Workshop> getAvailableWorkshops(@RequestParam BigDecimal maxFee) {
        return workshopService.getAvailableWithinBudget(maxFee);
    }

}
