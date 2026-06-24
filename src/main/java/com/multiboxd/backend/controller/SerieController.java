package com.multiboxd.backend.controller;

import com.multiboxd.backend.entity.Serie;
import com.multiboxd.backend.repository.SerieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/series") // Ce controller sera lancer lorsque ce genre de requêtes sera envoyés
public class SerieController {

    // Pas de new() car c'est une interface, sinon on devrait implémenter toutes les méthodes des interfaces
    final SerieRepository serieRepository;


    public SerieController(SerieRepository serieRepository){
        this.serieRepository = serieRepository;
    }

    /*
    @GetMapping
    public List<Serie> getAllSeries(){
        return serieRepository.findAll();
    }
    */

    @GetMapping
    public ResponseEntity<List<Serie>> getAllSeries(){
        return new ResponseEntity<>(serieRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Serie> createSerie(@RequestBody Serie serie){
        Serie serieCreated = serieRepository.save(serie);
        return new ResponseEntity<Serie>(serieCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSerieById(@PathVariable Long id){
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()){
            return new ResponseEntity<>(serie.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Serie> updateSerie(@PathVariable Long id, @RequestBody Serie serieDetails){
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie existingSerie = serie.get();
            existingSerie.setRating(serieDetails.getRating());
            existingSerie.setDescription(serieDetails.getDescription());
            existingSerie.setTitle(serieDetails.getTitle());

            Serie updatedSerie = serieRepository.save(existingSerie);
            return new ResponseEntity<>(updatedSerie, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable Long id){
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            serieRepository.delete(serie.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
