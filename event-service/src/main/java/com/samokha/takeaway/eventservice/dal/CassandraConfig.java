package com.samokha.takeaway.eventservice.dal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableCassandraRepositories(
		basePackages = "com.samokha.takeaway.eventservice.dal")
public class CassandraConfig
//extends AbstractCassandraConfiguration
{

	/*
	 * Factory bean that creates the com.datastax.driver.core.Session instance
	 */
	@Bean
	public CassandraClusterFactoryBean cluster() {

		CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace("event_key_space")
				.ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true);

		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setJmxReportingEnabled(false);
		cluster.setKeyspaceCreations(Arrays.asList(specification));
		cluster.setContactPoints("event-service-store"); //TODO to env var


		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {

		CassandraMappingContext mappingContext =  new CassandraMappingContext();

		mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), "event_key_space"));
		try {
			mappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan(new String[]{"com.samokha.takeaway.eventservice.model"}));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return mappingContext;
	}


	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());
	}

	/*
	 * Factory bean that creates the com.datastax.driver.core.Session instance
	 */
	@Bean
	public CassandraSessionFactoryBean session() {

		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setConverter(converter());
		session.setCluster(cluster().getObject());
		session.setKeyspaceName("event_key_space");
		session.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
//		session.setStartupScripts(Arrays.asList("CREATE KEYSPACE IF NOT EXISTS event_key_space;"));

		return session;

	}
}