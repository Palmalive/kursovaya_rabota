package com.example.kursovaya_rabota.menu.segment;

import com.example.kursovaya_rabota.menu.dish.Dish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SegmentService {
    private final SegmentRepository segmentRepository;


    public void saveSegment(Segment segment) {
        if (segment != null){
            segmentRepository.save(segment);
            log.info("Segment with id {} was saved", segment.getId());
        }
    }

    public List<Segment> getAllSegments(){
        return segmentRepository.findAll();
    }


    private Segment getSegmentById(Long segmentId) {
        return segmentRepository.findById(segmentId).orElse(null);
    }

    public void deleteSegment(Long id) {
        segmentRepository.deleteById(id);
        log.info("Segment with id {} was deleted", id);
    }

    public void addDishToSegment(Dish dish, Long segmentId) {
        Segment segment = getSegmentById(segmentId);
        segment.addDish(dish);
        dish.setSegment(segment);
        segmentRepository.save(segment);
        log.info("Dish with id {} was added to segment with id {}", dish.getId(), segmentId);
    }

    public void deleteDishFromSegment(Dish dish) {
        Segment segment = dish.getSegment();
        segment.deleteDish(dish);
        dish.setSegment(null);
        dish.setDeletedFromMenu(true);
        saveSegment(segment);
    }
}
