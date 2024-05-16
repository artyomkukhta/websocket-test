package com.farnell.server.service;


import com.farnell.server.exception.EntityNotFoundException;
import com.farnell.server.model.Event;
import org.springframework.stereotype.Service;
import com.farnell.server.repository.EventRepository;

import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;

    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Event.class, id));
    }

    @Override
    public List<Event> findAllByIds(Set<Long> ids) {
        return eventRepository.findAllByIdIn(ids);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> saveAll(List<Event> events) {
        return eventRepository.saveAll(events);
    }

    @Override
    public Event update(Long id, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Event.class, id));
        updatedEvent.setId(existingEvent.getId());
        updatedEvent.setCreatedAt(existingEvent.getCreatedAt());
        return eventRepository.save(updatedEvent);
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}

