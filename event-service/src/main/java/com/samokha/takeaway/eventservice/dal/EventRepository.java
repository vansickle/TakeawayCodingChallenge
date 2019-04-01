package com.samokha.takeaway.eventservice.dal;

import com.samokha.takeaway.eventservice.model.Event;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends MapIdCassandraRepository<Event> {

	List<Event> findByEntityId(UUID id);
}
