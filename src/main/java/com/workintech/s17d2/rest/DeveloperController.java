package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Taxable taxable;
    public Map<Integer, Developer> developers;


    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostConstruct
    public void init() {
        this.developers = new HashMap<>();
    }

    @GetMapping
    public List<Developer> getAll() {
        return new ArrayList<>(developers.values());
    }

    @GetMapping("/{id}")
    public Developer getById(@PathVariable int id) {
        return developers.get(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Developer dev) {
        developers.put(id, dev);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        developers.remove(id);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Developer dev) {
        double salary = dev.getSalary();
        Experience exp = dev.getExperience();

        if (exp == Experience.JUNIOR) {
            salary -= salary * (taxable.getSimpleTaxRate() / 100);
            developers.put(dev.getId(), new JuniorDeveloper(dev.getId(), dev.getName(), salary));
        } else if (exp == Experience.MID) {
            salary -= salary * (taxable.getMiddleTaxRate() / 100);
            developers.put(dev.getId(), new MidDeveloper(dev.getId(), dev.getName(), salary));
        } else if (exp == Experience.SENIOR) {
            salary -= salary * (taxable.getUpperTaxRate() / 100);
            developers.put(dev.getId(), new SeniorDeveloper(dev.getId(), dev.getName(), salary));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build(); 
    }


}
